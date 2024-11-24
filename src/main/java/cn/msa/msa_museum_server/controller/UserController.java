package cn.msa.msa_museum_server.controller;

import cn.msa.msa_museum_server.entity.User;
import cn.msa.msa_museum_server.exception.BusinessException;
import cn.msa.msa_museum_server.exception.ExceptionEnum;
import cn.msa.msa_museum_server.repository.UserRepository;
import cn.msa.msa_museum_server.service.impl.UserServiceimpl;
import cn.msa.msa_museum_server.dto.LoginDto;
import cn.msa.msa_museum_server.dto.LoginRequest;
import cn.msa.msa_museum_server.dto.ResponseDto;
import cn.msa.msa_museum_server.dto.RegisterDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import cn.msa.msa_museum_server.entity.User;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserServiceimpl userService;
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
	public ResponseDto<String> login(@RequestBody LoginDto loginDto) {
        if (loginDto.getUsername() == null || loginDto.getPassword() == null) {
			throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
		}

        String token = userService.login(loginDto);
		return new ResponseDto<String>(token);
	}

    @PostMapping("/register")
    public ResponseDto<String> register(@RequestBody RegisterDto registerDto) {
        if (registerDto.getUsername() == null || registerDto.getPassword() == null) {
            throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
        }

        String token = userService.register(registerDto);
        return new ResponseDto<String>(token);
    }

}
    
