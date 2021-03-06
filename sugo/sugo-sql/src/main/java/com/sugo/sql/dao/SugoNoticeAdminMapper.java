package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoNoticeAdmin;
import com.sugo.sql.entity.SugoNoticeAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SugoNoticeAdminMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    long countByExample(SugoNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    int deleteByExample(SugoNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    int insert(SugoNoticeAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    int insertSelective(SugoNoticeAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    SugoNoticeAdmin selectOneByExample(SugoNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    SugoNoticeAdmin selectOneByExampleSelective(@Param("example") SugoNoticeAdminExample example, @Param("selective") SugoNoticeAdmin.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    List<SugoNoticeAdmin> selectByExampleSelective(@Param("example") SugoNoticeAdminExample example, @Param("selective") SugoNoticeAdmin.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    List<SugoNoticeAdmin> selectByExample(SugoNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    SugoNoticeAdmin selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SugoNoticeAdmin.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    SugoNoticeAdmin selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    SugoNoticeAdmin selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SugoNoticeAdmin record, @Param("example") SugoNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SugoNoticeAdmin record, @Param("example") SugoNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SugoNoticeAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SugoNoticeAdmin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SugoNoticeAdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_notice_admin
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}