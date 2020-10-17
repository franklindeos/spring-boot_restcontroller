package br.com.torne.exception;

public class RestException extends RuntimeException {
    private String message;
    private Object[] args;
}
