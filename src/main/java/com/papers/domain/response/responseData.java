package com.papers.domain.response;

import java.io.Serializable;

/**
 * @author zhouyong
 * @date 2021/2/27 19:47
 */
public class responseData implements Serializable {
    private String msg;
    private Integer code;
    private Object data;

    public responseData() {

    }

    public responseData(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public responseData(Integer code, String msg, Object data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "responseData{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
