package cn.droidlover.xdroidmvp.net;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：Tornado
 * 创作日期：2017/7/14.
 * 描述：网络请求返回结果的POJO
 */

public class HttpResult<T> {

    @SerializedName("status")
    private Boolean state;
    @SerializedName("message")
    private String message;

    /**
     * 主题数据
     */
    private T data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
