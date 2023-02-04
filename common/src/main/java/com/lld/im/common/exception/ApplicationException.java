package com.lld.im.common.exception;

import lombok.Data;

@Data
public class ApplicationException extends RuntimeException{
    private int code;
    private String error;
}
