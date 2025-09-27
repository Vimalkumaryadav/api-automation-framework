package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test Hooks for setup and teardown operations
 * Executes before and after each test scenario
 */
public class TestHooks {
    
    private static final Logger logger = LoggerFactory.getLogger(TestHooks.class);
    
    @Before
    public void setUp(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());
        logger.info("Tags: {}", scenario.getSourceTagNames());
        
        // Add any setup logic here (e.g., test data preparation)
    }
    
    @After
    public void tearDown(Scenario scenario) {
        logger.info("Finished scenario: {} - Status: {}", scenario.getName(), scenario.getStatus());
        
        if (scenario.isFailed()) {
            logger.error("Scenario failed: {}", scenario.getName());
            // Add any cleanup logic for failed tests
        }
        
        // Add any cleanup logic here (e.g., test data cleanup)
    }
}