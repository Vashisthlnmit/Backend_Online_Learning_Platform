package com.onlineLearningPlatform.demo.ErrorHandling;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BussinessErrorCode{
    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED,"No code found"),
    ACCOUNT_LOCKED(302,HttpStatus.FORBIDDEN,"Account locked"),
    INCORRECT_CURRENT_PASSWORD(400,HttpStatus.BAD_REQUEST,"Incorrect current password"),
    NEW_PASSWORD_DOES_NOT_MATCH(401,HttpStatus.BAD_REQUEST,"Password does not match"),
    ACCOUNT_DISABLED(303,HttpStatus.FORBIDDEN,"Account disabled"),
    BAD_CREDENTIALS(303,HttpStatus.BAD_REQUEST,"Bad credentials");
    private int code;
    private HttpStatus httpStatus;
    private String message;

    BussinessErrorCode(int code, HttpStatus httpStatus,String message){
        this.code=code;
        this.httpStatus=httpStatus;
        this.message=message;
    }
}
