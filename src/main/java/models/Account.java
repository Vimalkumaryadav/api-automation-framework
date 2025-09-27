package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * POJO for Account entity with Jackson XML annotations for XML mapping
 */
@JacksonXmlRootElement(localName = "account")
public class Account {
    
    @JsonProperty("id")
    private Integer id;
    
    @JsonProperty("customerId")
    private Integer customerId;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("balance")
    private Double balance;
    
    // Default constructor
    public Account() {}
    
    // Constructor with all fields
    public Account(Integer id, Integer customerId, String type, Double balance) {
        this.id = id;
        this.customerId = customerId;
        this.type = type;
        this.balance = balance;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Double getBalance() {
        return balance;
    }
    
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                '}';
    }
}