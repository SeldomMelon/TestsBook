package com.example.testsbook.exceptionBook;

public class BookNotRegisteredException extends RuntimeException {
    public BookNotRegisteredException(String message) {
        super(message);
    }
}
