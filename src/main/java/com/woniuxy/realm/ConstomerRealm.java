package com.woniuxy.realm;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.config.JwtToken;
import com.woniuxy.entity.Role;
import com.woniuxy.entity.User;
import com.woniuxy.mapper.RoleMapper;
import com.woniuxy.mapper.UserMapper;
import com.woniuxy.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

//自定义的realm：
@Component
public class ConstomerRealm extends AuthorizingRealm {

//    组件注入：di
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof JwtToken;
    }


//    授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("》》》进入授权《《《");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 获取token
        String token = (String) principalCollection.getPrimaryPrincipal();
        // 由于过滤器的原因：获取到token需要进行解密
        DecodedJWT decodedJWT = JWTUtil.verify(token);
        // 获取用户名：
        String username = decodedJWT.getClaim("username").asString();
        List<Role> roles = roleMapper.findRoleByUsername(username);
        roles.forEach(role -> {
            System.out.println(role.getRolename());
            simpleAuthorizationInfo.addRole(role.getRolename());
        });
        return simpleAuthorizationInfo;
    }

//    认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("》》》进入认证《《《");
//        获取身份信息
        String token = (String) authenticationToken.getPrincipal();
        // 由于过滤器的原因：获取到token需要进行解密
        DecodedJWT decodedJWT = JWTUtil.verify(token);
        // 获取用户名：
        String username = decodedJWT.getClaim("username").asString();
        System.out.println(!StringUtils.hasLength(username));
        if (!StringUtils.hasLength(username)) {
            throw new AuthenticationException("》》》认证信息失败《《《");
        }
        return new SimpleAuthenticationInfo(token,token,this.getName());
    }
}
