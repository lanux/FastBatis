package org.lx.mybatis;


public class FastMapperException extends RuntimeException {
    public FastMapperException() {
        super();
    }

    public FastMapperException(String message) {
        super(message);
    }

    public FastMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public FastMapperException(Throwable cause) {
        super(cause);
    }

}
