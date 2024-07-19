package org.tech.springcode.service;

import org.tech.springcode.pojo.User;

public interface UserService {
    void register(User user);

    User findByUserName(String username);

}
