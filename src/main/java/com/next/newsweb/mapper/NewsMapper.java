package com.next.newsweb.mapper;

import com.next.newsweb.model.News;
import com.next.newsweb.model.NewsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface NewsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Sun May 03 22:47:23 CST 2020
     */
    long countByExample(NewsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Sun May 03 22:47:23 CST 2020
     */
    int deleteByExample(NewsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Sun May 03 22:47:23 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Sun May 03 22:47:23 CST 2020
     */
    int insert(News record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Sun May 03 22:47:23 CST 2020
     */
    int insertSelective(News record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Sun May 03 22:47:23 CST 2020
     */
    List<News> selectByExampleWithRowbounds(NewsExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Sun May 03 22:47:23 CST 2020
     */
    List<News> selectByExample(NewsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Sun May 03 22:47:23 CST 2020
     */
    News selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Sun May 03 22:47:23 CST 2020
     */
    int updateByExampleSelective(@Param("record") News record, @Param("example") NewsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Sun May 03 22:47:23 CST 2020
     */
    int updateByExample(@Param("record") News record, @Param("example") NewsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Sun May 03 22:47:23 CST 2020
     */
    int updateByPrimaryKeySelective(News record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Sun May 03 22:47:23 CST 2020
     */
    int updateByPrimaryKey(News record);
}