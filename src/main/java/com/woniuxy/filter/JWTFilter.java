package com.woniuxy.filter;

import com.woniuxy.config.JwtToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 继承BasicHttpAuthentionFilter
public class JWTFilter extends BasicHttpAuthenticationFilter {
    // 需要重写内部四个方法：

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 如果请求头中带了token，进入if中验证token
        // 调用方法：
        if (isLoginAttempt(request,response)) {
            try {
                return executeLogin(request,response);
            }catch (Exception e){
                return false;
            }
        }
        // 如果没带token，意味着匿名访问，直接放行，由shiro鉴权操作进行处理
        return true;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        // 强转request
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 获取请求头当中的数据
        String jwtToken = httpServletRequest.getHeader("JwtToken");
        // 根据jwtToken返回值
        return jwtToken != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        // 使用工具类强转request请求
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        // 获取前端的token
        String token = httpServletRequest.getHeader("JwtToken");
        // 将token存储定义的JwtToken中：
        JwtToken jwtToken = new JwtToken(token);
        // 调用主体进行登录确认
        Subject subject = getSubject(request, response);
        // 调用shiro的登录：注意是不是要进行登录错误判断
        System.out.println("getPrincipal:"+jwtToken.getPrincipal());
        System.out.println("getCredentials:"+jwtToken.getCredentials());
        subject.login(jwtToken);
        return true;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        // 使用工具类强转request请求
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        // 使用工具类强转response请求
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        //处理跨域
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 如果请求方式是options，代表的是post的预检请求，因此可以直接放行
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
