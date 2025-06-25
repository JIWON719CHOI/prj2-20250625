package com.example.prj2.domain.member.exception;

public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException(String message) {
        super(message);
    }
}
