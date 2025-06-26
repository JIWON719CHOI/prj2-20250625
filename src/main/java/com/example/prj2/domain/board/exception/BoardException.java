package com.example.prj2.domain.board.exception;

public abstract class BoardException extends RuntimeException {
    public BoardException(String message) {
        super(message);
    }
}