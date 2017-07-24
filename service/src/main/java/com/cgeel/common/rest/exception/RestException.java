package com.cgeel.common.rest.exception;

/**
 * Created by zxw on 2015/7/13.
 */
public class RestException extends Exception {

    private int code;
    private String reason;

    private RestException(String message){
        super(message);
    }

    public static RestException Error(int code, String reason){
        RestException ex = new RestException(reason);
        ex.setCode(code);
        ex.setReason(reason);
        return ex;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
