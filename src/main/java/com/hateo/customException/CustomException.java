package com.hateo.customException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException{
    String msg;
    public CustomException(String message) {
        super(String.format("%s",message));
        this.msg=message;
    }
}
