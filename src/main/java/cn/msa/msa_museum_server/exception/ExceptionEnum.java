package cn.msa.msa_museum_server.exception;

import lombok.Getter;

@Getter
public enum ExceptionEnum {

    FILE_NOT_FOUND("404", "File not found"),
    INVALID_PARAMETER("400", "Invalid parameter"),
    INTERNAL_SERVER_ERROR("500", "Internal server error"),
    INVALID_ENTRY("1001", "Invalid entry");

    private final String code;
    private final String message;

    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
