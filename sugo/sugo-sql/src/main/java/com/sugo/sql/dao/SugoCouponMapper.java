package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoCoupon;
import com.sugo.sql.entity.SugoCouponExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SugoCouponMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    long countByExample(SugoCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    int deleteByExample(SugoCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    int insert(SugoCoupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    int insertSelective(SugoCoupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    SugoCoupon selectOneByExample(SugoCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    SugoCoupon selectOneByExampleSelective(@Param("example") SugoCouponExample example, @Param("selective") SugoCoupon.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    List<SugoCoupon> selectByExampleSelective(@Param("example") SugoCouponExample example, @Param("selective") SugoCoupon.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    List<SugoCoupon> selectByExample(SugoCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    SugoCoupon selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SugoCoupon.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    SugoCoupon selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    SugoCoupon selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SugoCoupon record, @Param("example") SugoCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SugoCoupon record, @Param("example") SugoCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SugoCoupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SugoCoupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SugoCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_coupon
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}