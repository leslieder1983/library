package com.manage.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.manage.entity.User;
import com.manage.service.IUserService;
import com.manage.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm  extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    IUserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof  JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //登录验证
        JwtToken jwtToken=(JwtToken) authenticationToken;
        //获取userId
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        //查出用户
        User user = userService.getById(Long.valueOf(userId.trim()));
        if(user == null){
            throw new UnknownError("账户不存在");
        }
//        if(user.getStatus() == -1){
//            throw  new LockedAccountException("账户已被锁定");
//        }
        AccountProfile accountProfile=new AccountProfile();
        BeanUtil.copyProperties(user,accountProfile);

        return new SimpleAuthenticationInfo(accountProfile,jwtToken.getCredentials(),getName());
    }
}
