package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for reading and parsing JSON test data files
 */
public class JsonReader {
    
    private static final Logger logger = LoggerFactory.getLogger(JsonReader.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Read JSON file and convert to specified class type
     * 
     * @param filePath Path to JSON file in resources/testdata/
     * @param clazz Target class type
     * @return Parsed object of specified type
     */
    public static <T> T readJson(String filePath, Class<T> clazz) {
        try (InputStream inputStream = JsonReader.class.getClassLoader()
                .getResourceAsStream("testdata/" + filePath)) {
            
            if (inputStream == null) {
                throw new RuntimeException("JSON file not found: testdata/" + filePath);
            }
            
            T result = objectMapper.readValue(inputStream, clazz);
            logger.info("Successfully read JSON file: {}", filePath);
            return result;
            
        } catch (IOException e) {
            logger.error("Error reading JSON file: {}", filePath, e);
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }
    
    /**
     * Read JSON file and auto-infer class type for User objects (legacy method)
     * 
     * @param filePath Path to JSON file in resources/testdata/
     * @return Parsed User object
     */
    public static Object readJson(String filePath) {
        // For user files, default to User class
        if (filePath.toLowerCase().contains("user")) {
            try {
                return readJson(filePath, Class.forName("models.User"));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("User class not found", e);
            }
        }
        
        throw new RuntimeException("Cannot infer class type for file: " + filePath + ". Use readJson(filePath, Class) instead.");
    }
    

    
    /**
     * Convert object to JSON string
     */
    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.error("Error converting object to JSON string", e);
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }
}