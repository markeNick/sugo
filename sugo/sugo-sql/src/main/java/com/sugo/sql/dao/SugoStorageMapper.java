package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoStorage;
import com.sugo.sql.entity.SugoStorageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SugoStorageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    long countByExample(SugoStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    int deleteByExample(SugoStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    int insert(SugoStorage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    int insertSelective(SugoStorage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    SugoStorage selectOneByExample(SugoStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    SugoStorage selectOneByExampleSelective(@Param("example") SugoStorageExample example, @Param("selective") SugoStorage.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    List<SugoStorage> selectByExampleSelective(@Param("example") SugoStorageExample example, @Param("selective") SugoStorage.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    List<SugoStorage> selectByExample(SugoStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    SugoStorage selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SugoStorage.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    SugoStorage selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    SugoStorage selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SugoStorage record, @Param("example") SugoStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SugoStorage record, @Param("example") SugoStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SugoStorage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SugoStorage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SugoStorageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sugo_storage
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}