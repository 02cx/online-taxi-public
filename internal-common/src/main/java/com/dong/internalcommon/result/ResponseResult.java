package com.dong.internalcommon.result;

import com.dong.internalcommon.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;


    /**
     *  成功响应的方法
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponseResult success(T data){
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getMessage()).setData(data);
    }

    /**
     *  成功响应的方法
     */
    public static <T> ResponseResult success(Integer code,String message){
        return new ResponseResult().setCode(code).setMessage(message);
    }

    public static <T> ResponseResult success(){
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getMessage());
    }

    /**
     *  失败：统一的失败
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponseResult fail(T data){
        return new ResponseResult().setCode(CommonStatusEnum.FAIL.getCode()).setMessage(CommonStatusEnum.FAIL.getMessage()).setData(data);
    }

    /**
     *  失败： 自定义失败  错误码和提示信息
     * @param code
     * @param message
     * @return
     */
    public static ResponseResult fail(int code,String message){
        return new ResponseResult().setCode(code).setMessage(message);
    }

    /**
     *  失败： 自定义失败  错误码、提示信息、错误信息
     * @param code
     * @param message
     * @return
     */
    public static <T>ResponseResult fail(int code,String message,T data){
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }

    /**
     *  失败：枚举类信息
     * @param commonStatusEnum
     * @return
     */
    public static ResponseResult fail(CommonStatusEnum commonStatusEnum){
        return new ResponseResult().setCode(commonStatusEnum.getCode()).setMessage(commonStatusEnum.getMessage());
    }

    public static <T>ResponseResult fail(CommonStatusEnum commonStatusEnum,T data){
        return new ResponseResult().setCode(commonStatusEnum.getCode()).setMessage(commonStatusEnum.getMessage()).setData(data);
    }

}
