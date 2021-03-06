package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoKeyword;
import com.sugo.sql.entity.SugoKeywordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SugoKeywordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    long countByExample(SugoKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    int deleteByExample(SugoKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    int insert(SugoKeyword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    int insertSelective(SugoKeyword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    SugoKeyword selectOneByExample(SugoKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    SugoKeyword selectOneByExampleSelective(@Param("example") SugoKeywordExample example, @Param("selective") SugoKeyword.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    List<SugoKeyword> selectByExampleSelective(@Param("example") SugoKeywordExample example, @Param("selective") SugoKeyword.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    List<SugoKeyword> selectByExample(SugoKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    SugoKeyword selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SugoKeyword.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    SugoKeyword selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    SugoKeyword selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SugoKeyword record, @Param("example") SugoKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SugoKeyword record, @Param("example") SugoKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SugoKeyword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SugoKeyword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SugoKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_keyword
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}