import clients.ApiClient;
import config.ConfigManager;
import io.restassured.response.Response;

public class ResponsePrinter {
    public static void main(String[] args) {
        System.setProperty("env", "qa");
        
        ApiClient apiClient = new ApiClient();
        String endpoint = "services_proxy/bank/customers/14099/accounts";
        
        System.out.println("Making API call to: " + ConfigManager.getBaseUrl() + "/" + endpoint);
        System.out.println("Headers: " + ConfigManager.getHeaders());
        
        Response response = apiClient.get(endpoint, ConfigManager.getHeaders());
        
        System.out.println("\n=== COMPLETE API RESPONSE ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Status Line: " + response.getStatusLine());
        System.out.println("Headers: " + response.getHeaders().toString());
        System.out.println("Response Time: " + response.getTime() + " ms");
        System.out.println("Content Type: " + response.getContentType());
        System.out.println("\nResponse Body:");
        System.out.println(response.asString());
        System.out.println("\n=== END OF RESPONSE ===");
    }
}