package com.aso.itsdf07.service;

import com.aso.itsdf07.entity.DemoEntity;
import com.aso.itsdf07.mapper.DemoEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @Auther: itsdf07
 * @Date: 2019/7/26/026 18:01
 */
@Service
public class DemoService {

    /**
     * 注入dao
     */
    @Autowired
    private DemoEntityMapper demoEntityMapper;

    /**
     * 根据某一个字段查出Entity对象
     *
     * @param userName
     * @return
     */
    public DemoEntity getDemoEntity(String userName) {
        return demoEntityMapper.getDemoEntity(userName);
    }

    /**
     * 查询整张t_demo表的数据
     *
     * @return
     */
    public List<DemoEntity> getAllDemoEntity() {
        return demoEntityMapper.getAllDemoEntity();
    }



}
