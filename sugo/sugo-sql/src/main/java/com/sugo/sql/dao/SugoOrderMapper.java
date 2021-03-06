package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoOrder;
import com.sugo.sql.entity.SugoOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SugoOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    long countByExample(SugoOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    int deleteByExample(SugoOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    int insert(SugoOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    int insertSelective(SugoOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    SugoOrder selectOneByExample(SugoOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    SugoOrder selectOneByExampleSelective(@Param("example") SugoOrderExample example, @Param("selective") SugoOrder.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    List<SugoOrder> selectByExampleSelective(@Param("example") SugoOrderExample example, @Param("selective") SugoOrder.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    List<SugoOrder> selectByExample(SugoOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    SugoOrder selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SugoOrder.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    SugoOrder selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    SugoOrder selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SugoOrder record, @Param("example") SugoOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SugoOrder record, @Param("example") SugoOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SugoOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SugoOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SugoOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_order
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}