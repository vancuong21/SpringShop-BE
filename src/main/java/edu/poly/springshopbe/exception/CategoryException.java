package edu.poly.springshopbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryException extends RuntimeException{

    // constructor khoi tao message
    public CategoryException(String message) {
        super(message);
    }
}
