package cn.msa.msa_museum_server.service;

import cn.msa.msa_museum_server.dto.Reset_PasswordDto;

public interface ResetPasswordService {
    public void resetPassword(Reset_PasswordDto reset_passwordDto);
}
