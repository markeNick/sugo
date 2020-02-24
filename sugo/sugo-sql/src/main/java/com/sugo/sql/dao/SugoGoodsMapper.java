package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoGoods;
import com.sugo.sql.entity.SugoGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SugoGoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    long countByExample(SugoGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    int deleteByExample(SugoGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    int insert(SugoGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    int insertSelective(SugoGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    SugoGoods selectOneByExample(SugoGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    SugoGoods selectOneByExampleSelective(@Param("example") SugoGoodsExample example, @Param("selective") SugoGoods.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    SugoGoods selectOneByExampleWithBLOBs(SugoGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    List<SugoGoods> selectByExampleSelective(@Param("example") SugoGoodsExample example, @Param("selective") SugoGoods.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    List<SugoGoods> selectByExampleWithBLOBs(SugoGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    List<SugoGoods> selectByExample(SugoGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    SugoGoods selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SugoGoods.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    SugoGoods selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    SugoGoods selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SugoGoods record, @Param("example") SugoGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") SugoGoods record, @Param("example") SugoGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SugoGoods record, @Param("example") SugoGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SugoGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(SugoGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SugoGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SugoGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_goods
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}