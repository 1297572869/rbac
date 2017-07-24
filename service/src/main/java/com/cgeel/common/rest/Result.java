package com.cgeel.common.rest;

/**
 * Created by zxw on 2015/7/6.
 */
public class Result {

    private long length;
    private byte[] content;
    private int code;

    public Result(long length, byte[] content, int code) {
        this.length = length;
        this.content = content;
        this.code = code;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
