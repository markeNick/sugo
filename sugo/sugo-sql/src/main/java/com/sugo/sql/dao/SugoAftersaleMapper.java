package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoAftersale;
import com.sugo.sql.entity.SugoAftersaleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SugoAftersaleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    long countByExample(SugoAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    int deleteByExample(SugoAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    int insert(SugoAftersale record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    int insertSelective(SugoAftersale record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    SugoAftersale selectOneByExample(SugoAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    SugoAftersale selectOneByExampleSelective(@Param("example") SugoAftersaleExample example, @Param("selective") SugoAftersale.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    List<SugoAftersale> selectByExampleSelective(@Param("example") SugoAftersaleExample example, @Param("selective") SugoAftersale.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    List<SugoAftersale> selectByExample(SugoAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    SugoAftersale selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SugoAftersale.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    SugoAftersale selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    SugoAftersale selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SugoAftersale record, @Param("example") SugoAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SugoAftersale record, @Param("example") SugoAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SugoAftersale record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SugoAftersale record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SugoAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_aftersale
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}