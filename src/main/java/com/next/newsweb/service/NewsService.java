package com.next.newsweb.service;

import com.next.newsweb.dto.NewsDTO;
import com.next.newsweb.dto.PaginationDTO;
import com.next.newsweb.exception.CustomizeErrorCode;
import com.next.newsweb.exception.CustomizeException;
import com.next.newsweb.mapper.NewsExtMapper;
import com.next.newsweb.mapper.NewsMapper;
import com.next.newsweb.mapper.UserMapper;
import com.next.newsweb.model.News;
import com.next.newsweb.model.NewsExample;
import com.next.newsweb.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsExtMapper newsExtMapper;

    public PaginationDTO list(Integer page, Integer size) {//分页

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;

        Integer totalCount = (int) newsMapper.countByExample(new NewsExample());//返回long，强转
/*        @Select("select count(*) from news")
        Integer count();*/
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);
        //size*(page-1)
        Integer offset = size * (page - 1);
        List<News> newses = newsMapper.selectByExampleWithRowbounds(new NewsExample(), new RowBounds(offset, size));
/*      @Select("select * from news limit #{offset}, #{size}")
        List<News> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);*/
        List<NewsDTO> newsDTOList = new ArrayList<>();

        for (News news : newses) {
            User user = userMapper.selectByPrimaryKey(news.getCreator());
            /*          @Select("select * from user where id = #{id}")*/
            NewsDTO newsDTO = new NewsDTO();//把news转换为newsDTO,把news和user封装到一个类里
            BeanUtils.copyProperties(news, newsDTO);//spring自带方法完成上述拷贝
            newsDTO.setUser(user);
            newsDTOList.add(newsDTO);
        }

        paginationDTO.setNewses(newsDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {//个人发布展示

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;

        NewsExample newsExample = new NewsExample();
        newsExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) newsMapper.countByExample(newsExample);
        /*      @Select("select count(1) from news where creator = #{userId}")*/

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);
        NewsExample example = new NewsExample();
        newsExample.createCriteria()
                .andCreatorEqualTo(userId);
        List<News> newses = newsMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        /*      @Select("select * from news where creator = #{userId} limit #{offset}, #{size}")*/
        List<NewsDTO> newsDTOList = new ArrayList<>();

        for (News news : newses) {
            User user = userMapper.selectByPrimaryKey(news.getCreator());
            NewsDTO newsDTO = new NewsDTO();//把news转换为newsDTO,把news和user封装到一个类里
            BeanUtils.copyProperties(news, newsDTO);//spring自带方法完成上述拷贝,把左复制到右
            newsDTO.setUser(user);
            newsDTOList.add(newsDTO);
        }

        paginationDTO.setNewses(newsDTOList);
        return paginationDTO;
    }

    public NewsDTO getById(Integer id) {
        News news = newsMapper.selectByPrimaryKey(id);
        if (news == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        /*      @Select("select * from news where id = #{id}")*/
        NewsDTO newsDTO = new NewsDTO();
        BeanUtils.copyProperties(news, newsDTO);
        User user = userMapper.selectByPrimaryKey(news.getCreator());
        newsDTO.setUser(user);
        return newsDTO;
    }

    public void createOrUpdate(News news) {
        if (news.getId() == null) {
            news.setGmtCreate(System.currentTimeMillis());
            news.setGmtModified(news.getGmtCreate());
            newsMapper.insert(news);
            /*          @Insert("insert into news(title,content,gmt_create,gmt_modified,creator,tag) values (#{title},#{content},#{gmtCreate},#{gmtModified},#{creator},#{tag})")*/
        } else {
            News updateNews = new News();
            updateNews.setGmtModified(System.currentTimeMillis());
            updateNews.setTitle(news.getTitle());
            updateNews.setContent(news.getContent());
            updateNews.setTag(news.getTag());
            NewsExample example = new NewsExample();
            example.createCriteria()
                    .andIdEqualTo(news.getId());
            int updated = newsMapper.updateByExampleSelective(updateNews, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            /*@Update("update news set title = #{title}, content = #{content}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")*/
        }
    }

    public void incView(Integer id) {
        News news = new News();
        news.setId(id);
        news.setViewCount(1);
        newsExtMapper.incView(news);
    }
}
