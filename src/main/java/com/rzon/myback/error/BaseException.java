package com.rzon.myback.error;

import lombok.Data;

@Data
public class BaseException extends RuntimeException{

    private int code;

    private String message;

    public BaseException() {
        super();
    }

    public BaseException(BaseErrorEnum baseErrorEnum) {
        super();
        this.code = baseErrorEnum.getCode();
        this.message = baseErrorEnum.getMessage();
    }
}
