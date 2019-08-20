package com.aso.itsdf07.mapper;

import com.aso.itsdf07.entity.TransactionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TransactionEntityMapper {


    /**
     * 匹配数据
     *
     * @param transactionEntity
     * @return
     */
    int findTransactionByOrderNoAndDataTime(TransactionEntity transactionEntity);

    /**
     * 更新数据
     *
     * @param transactionEntity
     * @return
     */
    int updateTransactionByOrderNoAndDataTime(TransactionEntity transactionEntity);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction
     *
     * @mbggenerated
     */
    int insert(TransactionEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction
     *
     * @mbggenerated
     */
    int insertSelective(TransactionEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction
     *
     * @mbggenerated
     */
    TransactionEntity selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TransactionEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table transaction
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TransactionEntity record);
}