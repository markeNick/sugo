package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoCategory;
import com.sugo.sql.entity.SugoCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SugoCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    long countByExample(SugoCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    int deleteByExample(SugoCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    int insert(SugoCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    int insertSelective(SugoCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    SugoCategory selectOneByExample(SugoCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    SugoCategory selectOneByExampleSelective(@Param("example") SugoCategoryExample example, @Param("selective") SugoCategory.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    List<SugoCategory> selectByExampleSelective(@Param("example") SugoCategoryExample example, @Param("selective") SugoCategory.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    List<SugoCategory> selectByExample(SugoCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    SugoCategory selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SugoCategory.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    SugoCategory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    SugoCategory selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SugoCategory record, @Param("example") SugoCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SugoCategory record, @Param("example") SugoCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SugoCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SugoCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SugoCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_category
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}