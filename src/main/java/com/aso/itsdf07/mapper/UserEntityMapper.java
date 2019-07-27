package com.aso.itsdf07.mapper;

import com.aso.itsdf07.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserEntityMapper {

    /**
     * 账号密码登陆
     *
     * @param username
     * @param passwd
     * @return
     */
    UserEntity userLogin(@Param("username") String username, @Param("passwd") String passwd);

    /**
     * 账号密码登陆
     *
     * @return
     */
    List<UserEntity> getAllUsers();
}