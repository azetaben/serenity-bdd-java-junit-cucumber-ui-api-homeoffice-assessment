package homeoffice.listeners;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StepLoggingPlugin implements EventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(StepLoggingPlugin.class);

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::logStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::logStepFinished);
    }

    private void logStepStarted(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep step) {
            LOGGER.info("STEP START [{}] {}", step.getStep().getKeyword().trim(), step.getStep().getText());
        }
    }

    private void logStepFinished(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep step) {
            LOGGER.info(
                    "STEP END [{}] {} -> {}",
                    step.getStep().getKeyword().trim(),
                    step.getStep().getText(),
                    event.getResult().getStatus());
        }
    }
}
