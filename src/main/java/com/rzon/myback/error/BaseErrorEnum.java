package com.rzon.myback.error;

public enum BaseErrorEnum implements BaseErrorInfoInterface{

    //    异常枚举值
    SUCCESS(200, "成功!"),

    BODY_NOT_MATCH( 400, "数据格式不匹配! "),

    NOT_FOUND(404, "访问资源不存在! "),

    INTERNAL_SERVER_ERROR(500, "服务器内部错误! "),

    SERVER_BUSY(503, "服务器正忙，请稍后再试!"),

    REQUEST_METHOD_SUPPORT_ERROR( 405,"当前请求方法不支持");

    private int code;

    private String message;

    BaseErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode(){
        return code;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
