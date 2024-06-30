package com.hutech.demo;

public class NotFoundByIdException extends Throwable {
    public NotFoundByIdException(String message) {
        super(message);
    }
}
