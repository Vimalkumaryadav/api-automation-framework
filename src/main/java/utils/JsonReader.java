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
    public static Object readJson(String filePath) {
        try (InputStream inputStream = JsonReader.class.getClassLoader()
                .getResourceAsStream("testdata/" + filePath)) {

            if (inputStream == null) {
                throw new RuntimeException("JSON file not found: testdata/" + filePath);
            }

            // Infer the class type from the file name (e.g., User.json -> models.User)
            String className = filePath.substring(0, filePath.lastIndexOf('.'));
            String modelClass = "models." + className.substring(0, 1).toUpperCase() + className.substring(1);
            Class<?> clazz = Class.forName(modelClass);

            Object result = objectMapper.readValue(inputStream, clazz);
            logger.info("Successfully read JSON file: {}", filePath);
            return result;

        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error reading JSON file: {}", filePath, e);
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
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