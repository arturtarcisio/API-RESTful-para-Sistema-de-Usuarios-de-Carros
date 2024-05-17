package io.github.arturtcs.controller.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    @Getter
    private List<String> errors;

    public ApiErrors(List<String> errors){
        this.errors = errors;
    }

    public ApiErrors(String message, HttpStatusCode status){
        StringBuilder messageError = new StringBuilder();
        messageError.append("Message: ").append(message).append(". errorCode: ").append(status);
        this.errors = Arrays.asList(messageError.toString());
    }

}
