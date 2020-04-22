package com.next.newsweb.service;

import com.next.newsweb.dto.AttentiontagDTO;
import com.next.newsweb.dto.NewsDTO;
import com.next.newsweb.dto.PaginationDTO;
import com.next.newsweb.exception.CustomizeErrorCode;
import com.next.newsweb.exception.CustomizeException;
import com.next.newsweb.mapper.AttentiontagMapper;
import com.next.newsweb.mapper.NewsExtMapper;
import com.next.newsweb.mapper.NewsMapper;
import com.next.newsweb.mapper.UserMapper;
import com.next.newsweb.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttentiontagService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private AttentiontagMapper attentiontagMapper;

    @Autowired
    private NewsExtMapper newsExtMapper;

    public PaginationDTO list(Long userId, String tag, Integer page, Integer size) {

        PaginationDTO<AttentiontagDTO> paginationDTO = new PaginationDTO<>();

        Integer totalPage;

        AttentiontagExample attentiontagExample = new AttentiontagExample();
        attentiontagExample.createCriteria()
                .andUseridEqualTo(userId);
        Integer totalCount = (int) attentiontagMapper.countByExample(attentiontagExample);

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
        Integer offset = page < 1 ? 0 : size * (page - 1);
        AttentiontagExample example = new AttentiontagExample();
        example.createCriteria()
                .andUseridEqualTo(userId);
        example.setOrderByClause("id desc");/*倒序排列*/

        List<Attentiontag> attentiontags = attentiontagMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        if (attentiontags.size() == 0) {
            return paginationDTO;
        }

        List<AttentiontagDTO> attentiontagDTOS = new ArrayList<>();

        for (Attentiontag attentiontag : attentiontags) {
            User user = userMapper.selectByPrimaryKey(attentiontag.getUserid());
            AttentiontagDTO attentiontagDTO = new AttentiontagDTO();
            BeanUtils.copyProperties(attentiontag, attentiontagDTO);
            attentiontagDTO.setUser(user);
            attentiontagDTOS.add(attentiontagDTO);
        }
        paginationDTO.setData(attentiontagDTOS);
        return paginationDTO;
    }

    public NewsDTO getById(Long id) {
        News news = newsMapper.selectByPrimaryKey(id);
        if (news == null) {
            throw new CustomizeException(CustomizeErrorCode.NEWS_NOT_FOUND);
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
            // 创建
            news.setGmtCreate(System.currentTimeMillis());
            news.setGmtModified(news.getGmtCreate());
            news.setViewCount(0);
            news.setLikeCount(0);
            news.setCommentCount(0);
            newsMapper.insert(news);
            /*          @Insert("insert into news(title,content,gmt_create,gmt_modified,creator,tag) values (#{title},#{content},#{gmtCreate},#{gmtModified},#{creator},#{tag})")*/
        } else {
            // 更新
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
                throw new CustomizeException(CustomizeErrorCode.NEWS_NOT_FOUND);
            }
            /*@Update("update news set title = #{title}, content = #{content}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")*/
        }
    }

    public void incView(Long id) {
        News news = new News();
        news.setId(id);
        news.setViewCount(1);
        newsExtMapper.incView(news);
    }

    public List<NewsDTO> selectRelated(NewsDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays
                .stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));
        News news = new News();
        news.setId(queryDTO.getId());
        news.setTag(regexpTag);

        List<News> newses = newsExtMapper.selectRelated(news);
        List<NewsDTO> newsDTOS = newses.stream().map(q -> {
            NewsDTO newsDTO = new NewsDTO();
            BeanUtils.copyProperties(q, newsDTO);
            return newsDTO;
        }).collect(Collectors.toList());
        return newsDTOS;
    }
}
