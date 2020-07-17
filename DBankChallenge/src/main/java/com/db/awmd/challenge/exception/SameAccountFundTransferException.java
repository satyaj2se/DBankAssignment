package com.db.awmd.challenge.exception;

public class SameAccountFundTransferException extends RuntimeException {

    public SameAccountFundTransferException(String message){
        super(message);
    }

}
