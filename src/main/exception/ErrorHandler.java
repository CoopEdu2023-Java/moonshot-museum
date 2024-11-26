package cn.msa.museum.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.msa.museum.dto.ResponseDto;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseDto<String> exceptionHandler(Exception e) {
        System.out.println("Exception: " + e);
        return new ResponseDto<String>(1000, "Unknwon Error: " + e.getMessage(), null);
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseDto<String> exceptionHandler(BusinessException e) {
        System.out.println("Exception: " + e);
        return new ResponseDto<String>(e.getCode(), "Unknwon Error: " + e.getMessage(), null);
    }
}
