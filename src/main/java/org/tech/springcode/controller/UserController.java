package org.tech.springcode.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.tech.springcode.model.resp.LoginResp;
import org.tech.springcode.pojo.User;
import org.tech.springcode.service.UserService;
import org.tech.springcode.utils.JwtUtil;
import org.tech.springcode.utils.Md5Util;
import org.tech.springcode.utils.Result;
import org.tech.springcode.utils.ThreadLocalUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Tag(name = "认证中心")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        User userData = userService.findByUserName(user.getUsername());
        if (userData != null) {
            return Result.error("用户已经注册,请勿重新注册");
        }
        userService.register(user);
        return Result.success("注册成功");
    }


    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<LoginResp> login(@RequestBody User user) {
        User userData = userService.findByUserName(user.getUsername());
        if (userData == null) {
            return Result.error("用户不存在，请先注册");
        }
        String cryptPassword = Md5Util.getMD5String(user.getPassword());
        if (userData.getPassword().equals(cryptPassword)) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", userData.getId());
            claims.put("username", user.getUsername());

            String token = JwtUtil.genToken(claims);

            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(token, token, 72, TimeUnit.HOURS);
            LoginResp loginInfo = new LoginResp();
            loginInfo.setToken(token);
            return Result.success(loginInfo);
        }
        return Result.error("密码错误");
    }

    @PostMapping("/getUserInfo")
    public Result<User> getUserInfo() {
        Map<String, String> map = ThreadLocalUtil.get();
        String username = map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PostMapping("/delete")
    public Result deleteUser(@RequestBody User user) {
        userService.deleteUser(user);
        return Result.success();
    }
}
