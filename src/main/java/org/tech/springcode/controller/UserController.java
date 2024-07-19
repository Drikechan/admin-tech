package org.tech.springcode.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.tech.springcode.pojo.User;
import org.tech.springcode.service.UserService;
import org.tech.springcode.utils.JwtUtil;
import org.tech.springcode.utils.Md5Util;
import org.tech.springcode.utils.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        User userData = userService.findByUserName(user.getUsername());
        if (userData != null) {
            return Result.error("用户已经注册,请勿重新注册");
        }
        userService.register(user);
        return Result.success("注册成功");
    }

    @PostMapping("/login")
    public Result<HashMap<String, String>> login(@RequestBody User user) {
        User userData = userService.findByUserName(user.getUsername());
        if (userData == null) {
            return Result.error("用户不存在，请先注册");
        }
        String cryptPassword = Md5Util.getMD5String(user.getPassword());
        if (userData.getPassword().equals(cryptPassword)) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", userData.getId());
            map.put("username", user.getUsername());

            String token = JwtUtil.genToken(map);

            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(token, token, 72, TimeUnit.HOURS);
            HashMap<String, String> loginInfo = new HashMap<>();
            loginInfo.put("token", token);
            return Result.success(loginInfo);
        }
        return Result.error("密码错误");
    }
}
