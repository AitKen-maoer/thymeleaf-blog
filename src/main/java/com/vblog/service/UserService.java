package com.vblog.service;

import com.vblog.entity.User;

public interface UserService {
    public User CheckUser(String username,String password);
}
