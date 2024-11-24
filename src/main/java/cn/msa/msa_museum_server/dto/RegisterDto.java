package cn.msa.msa_museum_server.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;

    public RegisterDto(String username, String password) {
    }
}