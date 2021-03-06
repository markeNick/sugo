package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoIssue;
import com.sugo.sql.entity.SugoIssueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SugoIssueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    long countByExample(SugoIssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    int deleteByExample(SugoIssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    int insert(SugoIssue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    int insertSelective(SugoIssue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    SugoIssue selectOneByExample(SugoIssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    SugoIssue selectOneByExampleSelective(@Param("example") SugoIssueExample example, @Param("selective") SugoIssue.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    List<SugoIssue> selectByExampleSelective(@Param("example") SugoIssueExample example, @Param("selective") SugoIssue.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    List<SugoIssue> selectByExample(SugoIssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    SugoIssue selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SugoIssue.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    SugoIssue selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    SugoIssue selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SugoIssue record, @Param("example") SugoIssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SugoIssue record, @Param("example") SugoIssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SugoIssue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SugoIssue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SugoIssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_issue
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}