package cn.msa.msa_museum_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Helper class for LoginRequest
    static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        // Getters and setters (can use Lombok if preferred)
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // Helper class for ResetPasswordRequest
    static class ResetPasswordRequest {
        private String username;
        private String password;
        private String newPassword;

        public ResetPasswordRequest(String username, String password, String newPassword) {
            this.username = username;
            this.password = password;
            this.newPassword = newPassword;
        }

        // Getters and setters (can use Lombok if preferred)
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }

    @Test
    public void testLoginAndResetPassword() throws Exception {
        // 1. Perform login and retrieve the JWT token
        String loginRequestJson = objectMapper.writeValueAsString(new LoginRequest("user", "2aA@93r3f"));

        String jwtToken = mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk()) // Ensure login is successful
                .andReturn()
                .getResponse()
                .getContentAsString(); // Extract the plain token as a string

        // Assert that the token is not null or empty
        assertNotNull(jwtToken, "Token should not be null");
        assertFalse(jwtToken.isEmpty(), "Token should not be empty");

        // 2. Use the token to reset the password
        String resetPasswordRequestJson = """
        {
            "username": "user",
            "password": "2aA@93r3f",
            "new_password": "3aA@93r3f"
        }
        """;

        mockMvc.perform(post("/users/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken) // Pass token as Bearer token
                        .content(resetPasswordRequestJson)) // Pass username, password, and new_password
                .andExpect(status().isOk()) // Ensure password reset is successful
                .andExpect(jsonPath("$.message").value("success"));
    }

    @Test
    public void testInvalidLogin() throws Exception {
        // Create a request with invalid credentials
        String invalidLoginJson = objectMapper.writeValueAsString(new LoginRequest("wrongUser", "wrongPassword"));

        // Perform the login request
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidLoginJson))
                .andExpect(status().isOk()) // Expect 200 status, even for invalid credentials
                .andExpect(jsonPath("$.code").value(2002)) // Verify the error code for "user does not exist"
                .andExpect(jsonPath("$.message").value("Error: User does not exist")); // Verify the error message
    }

    @Test
    public void testUnauthorizedResetPassword() throws Exception {
        // Attempt to reset password without a token
        String resetPasswordRequestJson = objectMapper.writeValueAsString(
                new ResetPasswordRequest("testUser", "testPassword", "newPassword")
        );

        mockMvc.perform(post("/users/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resetPasswordRequestJson))
                .andExpect(status().isForbidden()); // Expect 403 when token is missing or invalid
    }
}
