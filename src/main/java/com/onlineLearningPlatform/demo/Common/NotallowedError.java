package com.onlineLearningPlatform.demo.Common;

public class NotallowedError extends RuntimeException{
    public NotallowedError(String message){
        super(message);
    }
}
