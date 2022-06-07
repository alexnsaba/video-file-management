package com.videouploadchallenge.exception;

public class CorruptFileException extends RuntimeException{
    public CorruptFileException(String message){
        super(message);
    }
}
