package com.example.prj2.domain.member.exception;

// 회원 조회 실패 예외
public class MemberNotFoundException extends MemberException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}