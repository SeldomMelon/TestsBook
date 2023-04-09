package com.example.testsbook.exceptionPerson;

public class PersonNotRegisteredException extends RuntimeException {
    public PersonNotRegisteredException(String message) {
        super(message);
    }
}

