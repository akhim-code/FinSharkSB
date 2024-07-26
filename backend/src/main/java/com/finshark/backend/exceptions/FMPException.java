package com.finshark.backend.exceptions;

public class FMPException extends RuntimeException {

    public FMPException(Exception e) {
        super("Error while parsing data", e);
    }
    
}
