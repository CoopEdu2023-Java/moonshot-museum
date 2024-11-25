package cn.msa.msa_museum_server.service;

import cn.msa.msa_museum_server.dto.LoginDto;

public interface UserService {
    public String login(LoginDto loginDto);
}