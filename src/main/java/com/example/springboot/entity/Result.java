package com.example.springboot.entity;

public abstract class Result<T> {

    public enum ResultStatus {
        OK("ok"),
        FAIL("fail");

        private String status;

        ResultStatus(String status) {
            this.status = status;
        }
    }

    ResultStatus status;
    String msg;
    T data;

//    public static Result failure(String message) {
//        return new Result("fail", message, false);
//    }
//
//    public static Result success(String message) {
//        return new Result("ok", message, true);
//    }

//    protected Result(String status, String msg) {
//        this(status, msg, null);
//    }

    protected Result(ResultStatus status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status.status;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}