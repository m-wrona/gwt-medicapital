package com.medicapital.client.exception;

/**
 * Exception informs that access to some resources of service is forbidden.
 * 
 * @author mwronski
 * 
 */
public class AccessDeniedException extends RuntimeException {

    private static final long serialVersionUID = 7558824641612426412L;

    public AccessDeniedException() {

    }

    public AccessDeniedException(String msg) {
        super(msg);
    }

    public AccessDeniedException(Throwable throwable) {
        super(throwable);
    }

    public AccessDeniedException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
