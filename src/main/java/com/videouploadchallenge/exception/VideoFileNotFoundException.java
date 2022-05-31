package com.videouploadchallenge.exception;

public class VideoFileNotFoundException extends RuntimeException{
    public VideoFileNotFoundException(String message){
        super(message);
    }
}
