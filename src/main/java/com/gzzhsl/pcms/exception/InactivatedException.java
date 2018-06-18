package com.gzzhsl.pcms.exception;

import org.apache.shiro.authc.AuthenticationException;

public class InactivatedException extends AuthenticationException {
    public InactivatedException(String msg) {
        super(msg);
    }
}
