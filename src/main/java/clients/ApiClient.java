package clients;

import config.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RetryUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic API Client for REST operations
 * Supports GET, POST, PUT, DELETE, PATCH with retry logic and logging
 */
public class ApiClient {
    
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);
    
    /**
     * Get base request specification with common configurations
     */
    private RequestSpecification getBaseRequestSpec() {
        return RestAssured.given()
                .baseUri(ConfigManager.getBaseUrl())
                .filter(new AllureRestAssured())
                .relaxedHTTPSValidation()
                .config(RestAssured.config().httpClient(
                    io.restassured.config.HttpClientConfig.httpClientConfig()
                        .setParam("http.socket.timeout", ConfigManager.getTimeout())
                        .setParam("http.connection.timeout", ConfigManager.getTimeout())
                ));
    }
    
    /**
     * Add headers and auth to request specification
     */
    private RequestSpecification addHeadersAndAuth(RequestSpecification requestSpec, Map<String, String> headers) {
        if (headers != null) {
            requestSpec.headers(headers);
        }
        
        // Add basic auth if configured
        String[] basicAuth = ConfigManager.getBasicAuthCredentials();
        if (basicAuth != null) {
            requestSpec.auth().basic(basicAuth[0], basicAuth[1]);
        }
        
        return requestSpec;
    }
    
    /**
     * GET request with retry logic
     */
    public Response get(String endpoint, Map<String, String> headers) {
        logger.info("Sending GET request to: {}", endpoint);
        
        return RetryUtil.retry(() -> {
            RequestSpecification requestSpec = getBaseRequestSpec();
            requestSpec = addHeadersAndAuth(requestSpec, headers);
            
            Response response = requestSpec.get(endpoint);
            logger.info("GET response status: {}", response.getStatusCode());
            return response;
        }, ConfigManager.getRetryCount());
    }
    
    /**
     * POST request with body and retry logic
     */
    public Response post(String endpoint, Object body, Map<String, String> headers) {
        logger.info("Sending POST request to: {}", endpoint);
        
        return RetryUtil.retry(() -> {
            RequestSpecification requestSpec = getBaseRequestSpec();
            requestSpec = addHeadersAndAuth(requestSpec, headers);
            
            if (body != null) {
                requestSpec.body(body);
            }
            
            Response response = requestSpec.post(endpoint);
            logger.info("POST response status: {}", response.getStatusCode());
            return response;
        }, ConfigManager.getRetryCount());
    }
    
    /**
     * PUT request with body and retry logic
     */
    public Response put(String endpoint, Object body, Map<String, String> headers) {
        logger.info("Sending PUT request to: {}", endpoint);
        
        return RetryUtil.retry(() -> {
            RequestSpecification requestSpec = getBaseRequestSpec();
            requestSpec = addHeadersAndAuth(requestSpec, headers);
            
            if (body != null) {
                requestSpec.body(body);
            }
            
            Response response = requestSpec.put(endpoint);
            logger.info("PUT response status: {}", response.getStatusCode());
            return response;
        }, ConfigManager.getRetryCount());
    }
    
    /**
     * DELETE request with retry logic
     */
    public Response delete(String endpoint, Map<String, String> headers) {
        logger.info("Sending DELETE request to: {}", endpoint);
        
        return RetryUtil.retry(() -> {
            RequestSpecification requestSpec = getBaseRequestSpec();
            requestSpec = addHeadersAndAuth(requestSpec, headers);
            
            Response response = requestSpec.delete(endpoint);
            logger.info("DELETE response status: {}", response.getStatusCode());
            return response;
        }, ConfigManager.getRetryCount());
    }
    
    /**
     * PATCH request with body and retry logic
     */
    public Response patch(String endpoint, Object body, Map<String, String> headers) {
        logger.info("Sending PATCH request to: {}", endpoint);
        
        return RetryUtil.retry(() -> {
            RequestSpecification requestSpec = getBaseRequestSpec();
            requestSpec = addHeadersAndAuth(requestSpec, headers);
            
            if (body != null) {
                requestSpec.body(body);
            }
            
            Response response = requestSpec.patch(endpoint);
            logger.info("PATCH response status: {}", response.getStatusCode());
            return response;
        }, ConfigManager.getRetryCount());
    }
}