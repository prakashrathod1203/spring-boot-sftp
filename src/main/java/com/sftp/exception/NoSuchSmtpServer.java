package com.sftp.exception;

public class NoSuchSmtpServer extends Exception {

    public NoSuchSmtpServer(String errorMessage) {
        super(errorMessage);
    }
}
