package com.example.prj2.domain.member.exception;

// 공통 베이스 예외
public class MemberException extends RuntimeException {
    public MemberException(String message) {
        super(message);
    }
}

