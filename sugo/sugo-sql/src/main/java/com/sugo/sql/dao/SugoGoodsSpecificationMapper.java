package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoGoodsSpecification;
import com.sugo.sql.entity.SugoGoodsSpecificationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SugoGoodsSpecificationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    long countByExample(SugoGoodsSpecificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    int deleteByExample(SugoGoodsSpecificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    int insert(SugoGoodsSpecification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    int insertSelective(SugoGoodsSpecification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    SugoGoodsSpecification selectOneByExample(SugoGoodsSpecificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    SugoGoodsSpecification selectOneByExampleSelective(@Param("example") SugoGoodsSpecificationExample example, @Param("selective") SugoGoodsSpecification.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    List<SugoGoodsSpecification> selectByExampleSelective(@Param("example") SugoGoodsSpecificationExample example, @Param("selective") SugoGoodsSpecification.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    List<SugoGoodsSpecification> selectByExample(SugoGoodsSpecificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    SugoGoodsSpecification selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SugoGoodsSpecification.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    SugoGoodsSpecification selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    SugoGoodsSpecification selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SugoGoodsSpecification record, @Param("example") SugoGoodsSpecificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SugoGoodsSpecification record, @Param("example") SugoGoodsSpecificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SugoGoodsSpecification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SugoGoodsSpecification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SugoGoodsSpecificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods_specification
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}