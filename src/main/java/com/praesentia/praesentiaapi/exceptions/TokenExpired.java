package com.praesentia.praesentiaapi.exceptions;

public class TokenExpired extends RuntimeException{
    public TokenExpired(){super();}
    public TokenExpired(String message){
        super(message);
    }
}