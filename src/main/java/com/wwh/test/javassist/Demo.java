package com.wwh.test.javassist;

public class Demo {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String printMsg() {
        System.out.println(msg);
        return msg;
    }
}
