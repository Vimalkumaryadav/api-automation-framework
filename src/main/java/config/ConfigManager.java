package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Configuration Manager for handling environment-specific properties
 * Supports multiple authentication types and environment switching
 */
public class ConfigManager {
    
    private static Properties properties;
    private static final String DEFAULT_ENV = "qa";
    
    static {
        loadProperties();
    }
    
    /**
     * Load properties based on environment
     * Default environment is 'qa' if not specified
     */
    private static void loadProperties() {
        String env = System.getProperty("env", DEFAULT_ENV);
        String configFile = "config/application-" + env + ".properties";
        
        properties = new Properties();
        try (InputStream inputStream = ConfigManager.class.getClassLoader()
                .getResourceAsStream(configFile)) {
            if (inputStream != null) {
                properties.load(inputStream);
                System.out.println("Loaded configuration for environment: " + env);
            } else {
                throw new RuntimeException("Configuration file not found: " + configFile);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration file: " + configFile, e);
        }
    }
    
    /**
     * Get property value by key
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Get base URL for API endpoints
     */
    public static String getBaseUrl() {
        return getProperty("base.url");
    }
    
    /**
     * Get authentication headers based on configured auth type
     * Supports Bearer token, API Key, and Basic Auth
     */
    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        
        // Bearer token authentication
        String bearerToken = getProperty("bearer.token");
        if (bearerToken != null && !bearerToken.isEmpty()) {
            headers.put("Authorization", "Bearer " + bearerToken);
        }
        
        // API Key authentication
        String apiKey = getProperty("api.key");
        if (apiKey != null && !apiKey.isEmpty()) {
            headers.put("X-API-KEY", apiKey);
        }
        
        // Basic Auth (handled separately in ApiClient if needed)
        return headers;
    }
    
    /**
     * Get basic auth credentials
     */
    public static String[] getBasicAuthCredentials() {
        String username = getProperty("basic.auth.user");
        String password = getProperty("basic.auth.password");
        
        if (username != null && password != null) {
            return new String[]{username, password};
        }
        return null;
    }
    
    /**
     * Get timeout configuration
     */
    public static int getTimeout() {
        String timeout = getProperty("request.timeout");
        return timeout != null ? Integer.parseInt(timeout) : 30000; // Default 30 seconds
    }
    
    /**
     * Get retry configuration
     */
    public static int getRetryCount() {
        String retryCount = getProperty("retry.count");
        return retryCount != null ? Integer.parseInt(retryCount) : 3; // Default 3 retries
    }
}