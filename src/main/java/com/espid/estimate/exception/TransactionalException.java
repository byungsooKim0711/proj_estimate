package com.espid.estimate.exception;

import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
public class TransactionalException extends Exception {

    public TransactionalException() {
        super();
    }

    public TransactionalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionalException(String message) {
        super(message);
    }

    public TransactionalException(Throwable cause) {
        super(cause);
    }
}