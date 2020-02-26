package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoFeedback;
import com.sugo.sql.entity.SugoFeedbackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SugoFeedbackMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    long countByExample(SugoFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    int deleteByExample(SugoFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    int insert(SugoFeedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    int insertSelective(SugoFeedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    SugoFeedback selectOneByExample(SugoFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    SugoFeedback selectOneByExampleSelective(@Param("example") SugoFeedbackExample example, @Param("selective") SugoFeedback.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    List<SugoFeedback> selectByExampleSelective(@Param("example") SugoFeedbackExample example, @Param("selective") SugoFeedback.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    List<SugoFeedback> selectByExample(SugoFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    SugoFeedback selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SugoFeedback.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    SugoFeedback selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    SugoFeedback selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SugoFeedback record, @Param("example") SugoFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SugoFeedback record, @Param("example") SugoFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SugoFeedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SugoFeedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SugoFeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_feedback
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}