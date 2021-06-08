package com.papers.mapper;

import com.papers.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhouyong
 * @date 2021/2/27 19:34
 */
public interface paperUserMapper {
    User login(User user);
    Integer register(User user);
    Integer updateUser(User user);
    User findUserById(Integer id);
}
