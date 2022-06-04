package com.videouploadchallenge.exception;

public class FilesNotFoundException extends RuntimeException{
    public FilesNotFoundException(String message){
        super(message);
    }
}
