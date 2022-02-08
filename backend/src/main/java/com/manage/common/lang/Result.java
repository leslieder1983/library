package com.manage.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private  int code;//200正常
    private  String msg;
    private  Object data;
    public  static  Result success( int code,String msg,Object data){
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return  result;
    }
    public  static  Result success(Object data){
        return success(200,"请求成功",data);
    }
    public  static  Result fail( int code,String msg,Object data){
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return  result;
    }
    public  static  Result fail(int code, String msg){
        return fail(code,msg,null);
    }
    public  static  Result fail( String msg){
        return fail(404,msg,null);
    }
}
