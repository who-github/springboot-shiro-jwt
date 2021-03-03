package com.woniuxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.entity.User;
import com.woniuxy.povo.Result;
import com.woniuxy.povo.StatusCode;
import com.woniuxy.povo.UserVO;
import com.woniuxy.service.UserService;
import com.woniuxy.utils.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author clk
 * @since 2021-03-02
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    // 登录的方法
    @PostMapping("/login")
    public Result login(@RequestBody UserVO userVO){
        // 由于使用了jwt过滤器，不再使用shiro进行登录
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userVO.getUsername());
        User userDB = userService.getOne(queryWrapper);
        if (!ObjectUtils.isEmpty(userDB)) {
            // 使用MD5加密
            Md5Hash md5Hash = new Md5Hash(userVO.getPassword(),userDB.getSalt(),1024);
            String md5Password = md5Hash.toHex();
            // 判断密码：
            if (md5Password.equals(userDB.getPassword())) {
                // 使用jwt
                HashMap<String, String> map = new HashMap<>();
                map.put("username",userVO.getUsername());
                String JwtToken = JWTUtil.createToken(map);
                return new Result(true, StatusCode.OK,"登录成功",JwtToken);
            }else {
                return new Result(false,StatusCode.LOGINERROR,"密码错误");
            }
        }
        return new Result(false,StatusCode.ERROR,"用户未注册");
    }

    @GetMapping("/findAll")
    // 添加注解，判断是否存在角色
    @RequiresRoles(value = {"董事长","人事总监"},logical = Logical.OR)
    public Result findAll(){
        List<User> users = userService.list(null);
        return new Result(true,StatusCode.OK,"查询所有用户信息成功",users);
    }


}

