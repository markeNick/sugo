package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoAddress;
import com.sugo.sql.entity.SugoAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SugoAddressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    long countByExample(SugoAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    int deleteByExample(SugoAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    int insert(SugoAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    int insertSelective(SugoAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    SugoAddress selectOneByExample(SugoAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    SugoAddress selectOneByExampleSelective(@Param("example") SugoAddressExample example, @Param("selective") SugoAddress.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    List<SugoAddress> selectByExampleSelective(@Param("example") SugoAddressExample example, @Param("selective") SugoAddress.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    List<SugoAddress> selectByExample(SugoAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    SugoAddress selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SugoAddress.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    SugoAddress selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    SugoAddress selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SugoAddress record, @Param("example") SugoAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SugoAddress record, @Param("example") SugoAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SugoAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SugoAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SugoAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_address
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}