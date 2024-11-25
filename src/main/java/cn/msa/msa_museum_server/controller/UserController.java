package cn.msa.msa_museum_server.controller;

import cn.msa.msa_museum_server.dto.LoginDto;
import cn.msa.msa_museum_server.dto.ResponseDto;
import cn.msa.msa_museum_server.exception.BusinessException;
import cn.msa.msa_museum_server.exception.ExceptionEnum;
import cn.msa.msa_museum_server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.msa.msa_museum_server.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        if(loginDto.getUsername() == null || loginDto.getPassword() == null) {
            throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
        }

        logger.info("Login attempt for user: {}", loginDto.getUsername()); // Log the username

        String result = userService.login(loginDto);

        logger.info("Login result for user {}: {}", loginDto.getUsername(), result); // Log the result

        return result;
    }
}
