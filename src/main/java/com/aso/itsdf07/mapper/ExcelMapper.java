package com.aso.itsdf07.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface ExcelMapper {

    void insertData();

    void updateData();

    void findData();
}