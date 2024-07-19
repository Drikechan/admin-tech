package org.tech.springcode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tech.springcode.mapper.UserMapper;
import org.tech.springcode.pojo.User;
import org.tech.springcode.service.UserService;
import org.tech.springcode.utils.Md5Util;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        String encryptPassword = Md5Util.getMD5String(user.getPassword());
        user.setPassword(encryptPassword);
        userMapper.register(user);
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }
}
