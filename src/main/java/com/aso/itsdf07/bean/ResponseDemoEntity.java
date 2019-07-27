package com.aso.itsdf07.bean;

import com.aso.itsdf07.entity.DemoEntity;

import java.util.List;

/**
 * @Description: DemoEntity实例数据响应
 * @Auther: itsdf07
 * @Date: 2019/7/26/026 18:58
 */
public class ResponseDemoEntity extends BaseResponseBean {
    /**
     * 数据集
     */
    private List<DemoEntity> data;

    public List<DemoEntity> getData() {
        return data;
    }

    public void setData(List<DemoEntity> data) {
        this.data = data;
    }
}
