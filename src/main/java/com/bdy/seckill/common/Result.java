package com.bdy.seckill.common;

/**
 * restful 的返回结果
 */
public class Result<T> {

    private int code; //状态码
    private String msg;  //返回信息
    private T data;     //返回数据


    public static  <T> Result<T> success(T data){return  new Result<>(data);}

    public static  <T> Result<T> error (CodeMsg cm){return  new Result<>(cm);}

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm){
        if(cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
