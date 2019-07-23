package com.aso.itsdf07.service;

import com.aso.itsdf07.entity.UserEntity;
import com.aso.itsdf07.mapper.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Auther: itsdf07
 * @Date: 2019/7/23/023 14:44
 */
@Service
public class UserService {
    /**
     * 注入dao
     */
    @Autowired
    private UserEntityMapper userEntityMapper;

    /**
     * 账号密码登陆
     *
     * @param username
     * @param passwd
     * @return
     */
    public UserEntity onUserLogin(String username, String passwd) {
        return userEntityMapper.userLogin(username, passwd);
    }
}
