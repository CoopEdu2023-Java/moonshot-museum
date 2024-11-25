package cn.msa.msa_museum_server.controller;

import cn.msa.msa_museum_server.dto.Reset_PasswordDto;
import cn.msa.msa_museum_server.dto.ResponseDto;
import cn.msa.msa_museum_server.exception.BusinessException;
import cn.msa.msa_museum_server.exception.ExceptionEnum;
import cn.msa.msa_museum_server.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class ResetPasswordController {
    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/reset-password")
    public ResponseDto<Void> resetPassword(@RequestBody Reset_PasswordDto reset_passwordDto) {
        if(reset_passwordDto.getUsername() == null || reset_passwordDto.getPassword() == null || reset_passwordDto.getNew_password() == null) {
            throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
        }
        resetPasswordService.resetPassword(reset_passwordDto);
        return new ResponseDto<>();
    }
}
