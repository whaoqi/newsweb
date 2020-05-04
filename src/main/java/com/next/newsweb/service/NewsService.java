package com.next.newsweb.service;

import com.next.newsweb.dto.NewsDTO;
import com.next.newsweb.dto.NewsQueryDTO;
import com.next.newsweb.dto.PaginationDTO;
import com.next.newsweb.enums.SortEnum;
import com.next.newsweb.exception.CustomizeErrorCode;
import com.next.newsweb.exception.CustomizeException;
import com.next.newsweb.mapper.NewsExtMapper;
import com.next.newsweb.mapper.NewsMapper;
import com.next.newsweb.mapper.UserMapper;
import com.next.newsweb.model.News;
import com.next.newsweb.model.NewsExample;
import com.next.newsweb.model.User;
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
public class NewsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsExtMapper newsExtMapper;

    public PaginationDTO list(String search, String tag, Long creator, String sort, Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays
                    .stream(tags)
                    .filter(StringUtils::isNotBlank)
                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
            //+&*引起的搜索异常
        }

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        totalPage = 0;

        /*        Integer totalCount = (int) newsMapper.countByExample(new NewsExample());//返回long，强转
         *//*        @Select("select count(*) from news")
        Integer count();*/

        NewsQueryDTO newsQueryDTO = new NewsQueryDTO();
        newsQueryDTO.setSearch(search);
        if (StringUtils.isNotBlank(tag)) {
            tag = tag.replace("+", "").replace("*", "").replace("?", "");
            newsQueryDTO.setTag(tag);
        }
/*        if (creator == 0)
            creator = null;*/
        newsQueryDTO.setCreator(creator);

        for (SortEnum sortEnum : SortEnum.values()) {
            if (sortEnum.name().toLowerCase().equals(sort)) {
                newsQueryDTO.setSort(sort);

                if (sortEnum == SortEnum.HOT7) {
                    newsQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
                }
                if (sortEnum == SortEnum.HOT30) {
                    newsQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
                }
                break;
            }
        }

        Integer totalCount = newsExtMapper.countBySearch(newsQueryDTO);
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (totalPage == 0) {
            totalPage = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }

        paginationDTO.setPagination(totalPage, page);
        Integer offset = page < 1 ? 0 : size * (page - 1);
        newsQueryDTO.setSize(size);
        newsQueryDTO.setPage(offset);
        List<News> newses = newsExtMapper.selectBySearch(newsQueryDTO);
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

        paginationDTO.setData(newsDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {//个人发布展示

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        totalPage = 0;

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
        if (totalPage == 0) {
            totalPage = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }

        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);
        NewsExample example = new NewsExample();
        example.createCriteria()
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

        paginationDTO.setData(newsDTOList);
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

    public void delete(Long id) {
        newsMapper.deleteByPrimaryKey(id);
    }
}
