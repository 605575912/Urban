package com.squareup.lib;


/**
 * Created by Administrator on 2017/05/25 0025.
 */

public class EventThreadObject<T> {
    private  T data;
    private  String command;
    private boolean success;

    private EventThreadObject() {

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public EventThreadObject(T data) {
        this.data = data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
