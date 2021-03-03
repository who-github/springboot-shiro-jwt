package com.woniuxy.povo;

import lombok.Data;

import java.io.Serializable;

//自定义错误状态码
@Data
public class StatusCode implements Serializable {

    public static final int OK = 20000;//成功
    public static final int ERROR = 20001;//失败
    public static final int LOGINERROR = 20002;//用户名或密码错误
    public static final int ACCESSERROR = 20003;//权限不足
    public static final int NULLPOINTERROR = 20004;//空指向异常
    public static final int UNKNOWNERROR = 20005;//未知异常
    public static final int ARRAYINDEXOUTOFBOUNDERROR = 20006;//数组越界异常

}
