package cn.msa.msa_museum_server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cn.msa.msa_museum_server.dto.LoginDto;
import cn.msa.msa_museum_server.dto.RegisterDto;
import cn.msa.msa_museum_server.entity.User;
import cn.msa.msa_museum_server.exception.BusinessException;
import cn.msa.msa_museum_server.exception.ExceptionEnum;
import cn.msa.msa_museum_server.repository.UserRepository;
import cn.msa.msa_museum_server.service.JwtService;
import cn.msa.msa_museum_server.service.UserService;


@Service
public class UserServiceimpl implements UserService {

    @Autowired
	private UserRepository userRepository;

    @Autowired
	private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    public String login(LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        
        User existUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.WRONG_PASSWORD));

        if (!bCryptPasswordEncoder.matches(password, existUser.getPassword())) {
            throw new BusinessException(ExceptionEnum.WRONG_PASSWORD);
        }
        return jwtService.setToken(existUser);
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BusinessException(ExceptionEnum.USER_EXISTS);
        }

        User newUser = new User();
        newUser.setUsername(registerDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        newUser.setEmail(registerDto.getEmail());

        User savedUser = userRepository.save(newUser);

        return jwtService.setToken(savedUser);
    }

}
