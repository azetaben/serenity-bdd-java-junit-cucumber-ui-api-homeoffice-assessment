package homeoffice.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import homeoffice.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ProductDataManager handles all product-related data from external configuration.
 * This eliminates hardcoding of product names, prices, and other attributes.
 */
public class ProductDataManager {
    private static final Logger log = LoggerFactory.getLogger(ProductDataManager.class);
    private static final String PRODUCTS_JSON_FILE = "data/jsonfiles/products.json";
    private static final ProductDataManager INSTANCE = new ProductDataManager();
    private List<Product> products;
    private Map<String, Product> productsByName;
    private Map<String, Product> productsByAlias;

    private ProductDataManager() {
        loadProducts();
    }

    public static ProductDataManager getInstance() {
        return INSTANCE;
    }

    private void loadProducts() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(PRODUCTS_JSON_FILE);

            if (inputStream == null) {
                throw new IllegalStateException("Products JSON file not found: " + PRODUCTS_JSON_FILE);
            }

            Map<String, Object> data = mapper.readValue(inputStream, Map.class);
            List<Map<String, Object>> productsData = (List<Map<String, Object>>) data.get("products");

            if (productsData == null) {
                throw new IllegalStateException("No 'products' array found in " + PRODUCTS_JSON_FILE);
            }

            this.products = new ArrayList<>();
            this.productsByName = new HashMap<>();
            this.productsByAlias = new HashMap<>();

            for (Map<String, Object> productData : productsData) {
                Product product = mapToProduct(productData);
                products.add(product);
                productsByName.put(product.getName().toLowerCase(), product);

                // Index by aliases for flexible lookup
                if (product.getAliases() != null) {
                    for (String alias : product.getAliases()) {
                        productsByAlias.put(alias.toLowerCase(), product);
                    }
                }
            }

            log.info("Loaded {} products from {}", products.size(), PRODUCTS_JSON_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load products from " + PRODUCTS_JSON_FILE, e);
        }
    }

    private Product mapToProduct(Map<String, Object> data) {
        Product product = new Product();
        product.setId(((Number) data.get("id")).intValue());
        product.setName((String) data.get("name"));
        product.setPrice((String) data.get("price"));
        product.setDescription((String) data.get("description"));
        product.setSku((String) data.get("sku"));

        List<String> aliases = (List<String>) data.get("aliases");
        if (aliases != null) {
            product.setAliases(aliases);
        }

        return product;
    }

    /**
     * Get a product by its exact name
     */
    public Product getProductByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        Product product = productsByName.get(name.toLowerCase().trim());
        if (product == null) {
            log.warn("Product not found by name: {}", name);
            // Try searching by alias
            product = productsByAlias.get(name.toLowerCase().trim());
        }
        return product;
    }

    /**
     * Get a product by name or alias
     */
    public Product findProduct(String nameOrAlias) {
        if (nameOrAlias == null || nameOrAlias.isBlank()) {
            throw new IllegalArgumentException("Product identifier cannot be null or blank");
        }
        String searchTerm = nameOrAlias.toLowerCase().trim();

        // First try exact name match
        Product product = productsByName.get(searchTerm);
        if (product != null) {
            return product;
        }

        // Then try alias match
        product = productsByAlias.get(searchTerm);
        if (product != null) {
            return product;
        }

        // Finally try partial name match
        product = products.stream()
                .filter(p -> p.getName().toLowerCase().contains(searchTerm))
                .findFirst()
                .orElse(null);

        if (product == null) {
            log.warn("No product found matching: {}", nameOrAlias);
        }
        return product;
    }

    /**
     * Get all products
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    /**
     * Get product by ID
     */
    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Get all product names
     */
    public List<String> getAllProductNames() {
        return products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());
    }

    /**
     * Get product price by name
     */
    public String getProductPrice(String productName) {
        Product product = findProduct(productName);
        return product != null ? product.getPrice() : null;
    }

    /**
     * Get product description by name
     */
    public String getProductDescription(String productName) {
        Product product = findProduct(productName);
        return product != null ? product.getDescription() : null;
    }

    /**
     * Get the most expensive product
     */
    public Product getMostExpensiveProduct() {
        return products.stream()
                .max(Comparator.comparingDouble(p -> parsePrice(p.getPrice())))
                .orElse(null);
    }

    /**
     * Get the cheapest product
     */
    public Product getCheapestProduct() {
        return products.stream()
                .min(Comparator.comparingDouble(p -> parsePrice(p.getPrice())))
                .orElse(null);
    }

    /**
     * Check if a product exists
     */
    public boolean productExists(String nameOrAlias) {
        return findProduct(nameOrAlias) != null;
    }

    private double parsePrice(String price) {
        return Double.parseDouble(price.replaceAll("[^0-9.]", ""));
    }
}

