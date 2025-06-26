package com.example.prj2.domain.member.exception;

// 비밀번호 불일치
public class InvalidPasswordException extends MemberException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}