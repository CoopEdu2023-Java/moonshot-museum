package cn.msa.msa_museum_server.dto;

public class LoginRequest {
    private String username;
    private String password;
    
    // getter 和 setter
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
} 