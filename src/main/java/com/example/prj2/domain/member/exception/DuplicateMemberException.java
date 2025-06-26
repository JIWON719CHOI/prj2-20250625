package com.example.prj2.domain.member.exception;

// 가입 중복 예외
public class DuplicateMemberException extends MemberException {
    public DuplicateMemberException(String message) {
        super(message);
    }
}