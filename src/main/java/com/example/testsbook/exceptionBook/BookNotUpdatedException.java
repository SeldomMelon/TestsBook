package com.example.testsbook.exceptionBook;

public class BookNotUpdatedException extends RuntimeException {
    public BookNotUpdatedException(String message) {
        super(message);
    }
}
