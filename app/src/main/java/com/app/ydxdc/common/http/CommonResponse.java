package com.app.ydxdc.common.http;



/**
 * 统一服务器响应对象，根据项目进行定制修改
 * CommonResponse
 */

public class CommonResponse<T> {
    private String msg;
    private int code = -1;
    private T rows;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }
}
