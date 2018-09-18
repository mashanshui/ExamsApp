package cn.droidlover.xdroidmvp.net.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author 张继淮
 * @date 2017/9/28
 * @description 网络请求返回类型类
 */

public class RequestResults<T> {
    @SerializedName(value = "state", alternate = {"code"})
    private int state;
    @SerializedName(value = "msg", alternate = {"message"})
    private String msg;
    @SerializedName(value = "obj", alternate = {"data"})
    private T obj;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Response{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                ", obj='" + obj + '\'' +
                '}';
    }
}
