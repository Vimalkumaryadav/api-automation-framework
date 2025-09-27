package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * Utility class for implementing retry logic for flaky API requests
 */
public class RetryUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(RetryUtil.class);
    
    /**
     * Retry a supplier operation with specified number of attempts
     * 
     * @param operation The operation to retry
     * @param maxAttempts Maximum number of retry attempts
     * @return Result of the operation
     * @throws RuntimeException if all retry attempts fail
     */
    public static <T> T retry(Supplier<T> operation, int maxAttempts) {
        Exception lastException = null;
        
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                logger.debug("Executing operation, attempt {}/{}", attempt, maxAttempts);
                return operation.get();
            } catch (Exception e) {
                lastException = e;
                logger.warn("Operation failed on attempt {}/{}: {}", attempt, maxAttempts, e.getMessage());
                
                if (attempt < maxAttempts) {
                    try {
                        // Exponential backoff: wait 1s, 2s, 4s, etc.
                        long waitTime = 1000L * (long) Math.pow(2, attempt - 1);
                        logger.info("Waiting {}ms before retry...", waitTime);
                        Thread.sleep(waitTime);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Retry interrupted", ie);
                    }
                }
            }
        }
        
        throw new RuntimeException("Operation failed after " + maxAttempts + " attempts", lastException);
    }
    
    /**
     * Retry with default 3 attempts
     */
    public static <T> T retry(Supplier<T> operation) {
        return retry(operation, 3);
    }
}