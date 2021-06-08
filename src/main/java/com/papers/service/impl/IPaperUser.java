package com.papers.service.impl;

import com.papers.domain.User;
import com.papers.mapper.paperUserMapper;
import com.papers.service.paperUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author zhouyong
 * @date 2021/2/27 19:35
 */
@Service
public class IPaperUser implements paperUserService {

    @Autowired
    private paperUserMapper userMapper;

    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    @Override
    public Integer register(User user) {
        String phoneOrEmail = user.getPhoneOrEmail();
        // 设置邮箱或者手机号
        if( phoneOrEmail.contains("@") || phoneOrEmail.contains("com") ) {
            user.setEmail(phoneOrEmail);
        }else{
            user.setPhone(phoneOrEmail);
        }
        // 随机生成一个用户名
        user.setUsername(UUID.randomUUID().toString().substring(0,10));
        return userMapper.register(user);
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User findUserById(Integer id) {
        return userMapper.findUserById(id);
    }
}
