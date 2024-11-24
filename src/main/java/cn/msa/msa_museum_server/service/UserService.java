package cn.msa.msa_museum_server.service;

import org.springframework.stereotype.Service;

import cn.msa.msa_museum_server.dto.LoginDto;
import cn.msa.msa_museum_server.dto.RegisterDto;

@Service
public interface UserService {
    
    public String login(LoginDto loginDto);
    public String register(RegisterDto registerDto);

}
