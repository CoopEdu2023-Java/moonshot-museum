package cn.msa.msa_museum_server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {

    MISSING_PARAMETERS(1001, "Missing Parameters"),

    USER_EXISTS(2001, "User exists"),
    USER_DOES_NOT_EXIST(2002, "User does not exist"),
    WRONG_PASSWORD(2003, "Wrong password"),
    USERNAME_ALREADY_EXISTS(400, "用户名已存在");

    private final Integer code;
    private final String message;
}
