package homeoffice.support;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static homeoffice.config.PostcodesApiConfiguration.stubMappingsFile;
import static homeoffice.config.PostcodesApiConfiguration.stubResponsesDirectory;

public final class PostcodesApiStub {

    private static final String JSON_CONTENT_TYPE = "application/json";

    private static WireMockServer server;

    private PostcodesApiStub() {}

    public static void start() {
        if (server != null && server.isRunning()) {
            return;
        }
        server = new WireMockServer(options().dynamicPort());
        server.start();
        configureStubs();
        System.setProperty("postcodes.api.base-url", server.baseUrl());
    }

    public static void stop() {
        if (server != null) {
            server.stop();
            server = null;
        }
        System.clearProperty("postcodes.api.base-url");
    }

    public static boolean isRunning() {
        return server != null && server.isRunning();
    }

    private static void configureStubs() {
        stubMappings().stream().skip(1).map(PostcodesApiStub::stubMapping).forEach(server::stubFor);
    }

    private static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder jsonResponse(
            int status, String body) {
        return aResponse()
                .withStatus(status)
                .withHeader("Content-Type", JSON_CONTENT_TYPE)
                .withBody(body);
    }

    private static MappingBuilder stubMapping(String mappingLine) {
        List<String> columns = parseCsvLine(mappingLine);
        if (columns.size() != 5) {
            throw new IllegalArgumentException("Invalid stub mapping: " + mappingLine);
        }

        String method = columns.get(0);
        String pathPattern = columns.get(1);
        String queryParams = columns.get(2);
        int status = Integer.parseInt(columns.get(3));
        String responseBody = resourceText(stubResponsesDirectory() + "/" + columns.get(4));

        MappingBuilder mapping = requestMapping(method, pathPattern);
        if (!queryParams.isBlank()) {
            Arrays.stream(queryParams.split(";"))
                    .map(parameter -> parameter.split("=", 2))
                    .forEach(
                            parameter ->
                                    mapping.withQueryParam(parameter[0], equalTo(parameter[1])));
        }

        return mapping.willReturn(jsonResponse(status, responseBody));
    }

    private static MappingBuilder requestMapping(String method, String pathPattern) {
        if ("GET".equalsIgnoreCase(method)) {
            return get(urlPathMatching(pathPattern));
        }
        if ("POST".equalsIgnoreCase(method)) {
            return post(urlPathMatching(pathPattern));
        }

        throw new IllegalArgumentException("Unsupported stub method: " + method);
    }

    private static List<String> stubMappings() {
        return resourceText(stubMappingsFile()).lines().filter(line -> !line.isBlank()).toList();
    }

    private static String resourceText(String resourcePath) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (var inputStream = classLoader.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not read resource: " + resourcePath, exception);
        }
    }

    private static List<String> parseCsvLine(String line) {
        return Arrays.stream(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1))
                .map(PostcodesApiStub::unquote)
                .toList();
    }

    private static String unquote(String value) {
        String trimmedValue = value.trim();
        if (trimmedValue.length() >= 2
                && trimmedValue.startsWith("\"")
                && trimmedValue.endsWith("\"")) {
            return trimmedValue.substring(1, trimmedValue.length() - 1).replace("\"\"", "\"");
        }
        return trimmedValue;
    }
}
