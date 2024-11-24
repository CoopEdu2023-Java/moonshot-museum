package cn.msa.msa_museum_server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {

    MISSING_PARAMETERS(1001, "Missing parameters"),
    
    FILE_NOT_FOUND(2004, "File not found");

    private final int code;
    private final String message;
}
