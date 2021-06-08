package com.papers.service;

import com.papers.domain.User;

/**
 * @author zhouyong
 * @date 2021/2/27 19:35
 */
public interface paperUserService {
    User login(User user);
    Integer register(User user);
    Integer updateUser(User user);
    User findUserById(Integer id);
}
