package com.aso.itsdf07.bean;

import com.aso.itsdf07.entity.UserEntity;

import java.util.List;

/**
 * @Description: TODO
 * @Auther: itsdf07
 * @Date: 2019/7/27/027 14:18
 */
public class ResponseUserEntity extends BaseResponseBean {
    private List<UserEntity> data;

    public List<UserEntity> getData() {
        return data;
    }

    public void setData(List<UserEntity> data) {
        this.data = data;
    }
}
