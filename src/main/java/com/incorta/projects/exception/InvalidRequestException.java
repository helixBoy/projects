package com.incorta.projects.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException{
    private String reason;

    public InvalidRequestException(String reason) {
        super(reason);
        this.reason = reason;
    }
}
