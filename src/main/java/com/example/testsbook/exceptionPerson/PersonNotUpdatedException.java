package com.example.testsbook.exceptionPerson;

public class PersonNotUpdatedException  extends RuntimeException {
    public PersonNotUpdatedException(String message) {
        super(message);
    }
}
