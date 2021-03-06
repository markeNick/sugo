package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoGrouponRules;
import com.sugo.sql.entity.SugoGrouponRulesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SugoGrouponRulesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    long countByExample(SugoGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    int deleteByExample(SugoGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    int insert(SugoGrouponRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    int insertSelective(SugoGrouponRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    SugoGrouponRules selectOneByExample(SugoGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    SugoGrouponRules selectOneByExampleSelective(@Param("example") SugoGrouponRulesExample example, @Param("selective") SugoGrouponRules.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    List<SugoGrouponRules> selectByExampleSelective(@Param("example") SugoGrouponRulesExample example, @Param("selective") SugoGrouponRules.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    List<SugoGrouponRules> selectByExample(SugoGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    SugoGrouponRules selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SugoGrouponRules.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    SugoGrouponRules selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    SugoGrouponRules selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SugoGrouponRules record, @Param("example") SugoGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SugoGrouponRules record, @Param("example") SugoGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SugoGrouponRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SugoGrouponRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SugoGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_groupon_rules
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}