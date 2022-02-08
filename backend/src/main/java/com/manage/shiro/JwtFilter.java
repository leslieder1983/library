package com.manage.shiro;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.manage.common.lang.Result;
import com.manage.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.val;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter  extends AuthenticatingFilter {
    @Autowired
    JwtUtils jwtUtils;
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //强转
       HttpServletRequest request=(HttpServletRequest) servletRequest;
       //HTTP协议中的 Authorization 请求消息头含有服务器用于验证用户代理身份的凭证
       String jwt= request.getHeader( "Authorization");
       if(StringUtils.isEmpty(jwt)){
           return null;
       }
        //生成token
        return new JwtToken(jwt);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //拦截，判断jwt是否过期等
        //强转
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        //HTTP协议中的 Authorization 请求消息头含有服务器用于验证用户代理身份的凭证
        String jwt= request.getHeader( "Authorization");
        if(StringUtils.isEmpty(jwt)){
            return true;
        }else{
            //校验jwt是否异常或过期,登录处理
            Claims claimByToken = jwtUtils.getClaimByToken(jwt);
            if(claimByToken ==null ||jwtUtils.isTokenExpired(claimByToken.getExpiration())){
                throw new ExpiredCredentialsException("token已经失效，请重新登录");
            }
            //执行登录
            return  executeLogin(servletRequest,servletResponse);
        }

    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
       Throwable throwable=  e.getCause()==null ?e :e.getCause();
       //封装json数据
        Result result = Result.fail(throwable.getMessage());
        String json = JSONUtil.toJsonStr(result);
        HttpServletResponse httpServletResponse=(HttpServletResponse) response;
        try {
            //返回给前端数据
            httpServletResponse.getWriter().print(json);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //跨域处理
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest= WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse=WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin",httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-control-Allow-Methods","GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-control-Allow-Headers",httpServletRequest.getHeader("Access-Control-Request-Headers"));
        //跨域时会首先发送一个option请求，这里给options返回正常状态
        if(httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
