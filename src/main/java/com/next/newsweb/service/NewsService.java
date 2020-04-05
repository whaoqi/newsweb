package com.next.newsweb.service;

import com.next.newsweb.dto.NewsDTO;
import com.next.newsweb.dto.PaginationDTO;
import com.next.newsweb.mapper.NewsMapper;
import com.next.newsweb.mapper.UserMapper;
import com.next.newsweb.model.News;
import com.next.newsweb.model.User;
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

    public PaginationDTO list(Integer page, Integer size) {//分页

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;

        Integer totalCount = newsMapper.count();
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
        List<News> newses = newsMapper.list(offset, size);//查询所有news对象
        List<NewsDTO> newsDTOList = new ArrayList<>();

        for (News news : newses) {
            User user = userMapper.findById(news.getCreator());
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

        Integer totalCount = newsMapper.countByUserId(userId);

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
        List<News> newses = newsMapper.listByUserId(userId, offset, size);//查询所有news对象
        List<NewsDTO> newsDTOList = new ArrayList<>();

        for (News news : newses) {
            User user = userMapper.findById(news.getCreator());
            NewsDTO newsDTO = new NewsDTO();//把news转换为newsDTO,把news和user封装到一个类里
            BeanUtils.copyProperties(news, newsDTO);//spring自带方法完成上述拷贝,把左复制到右
            newsDTO.setUser(user);
            newsDTOList.add(newsDTO);
        }

        paginationDTO.setNewses(newsDTOList);
        return paginationDTO;
    }

    public NewsDTO getById(Integer id) {
        News news = newsMapper.getById(id);
        NewsDTO newsDTO = new NewsDTO();
        BeanUtils.copyProperties(news, newsDTO);
        User user = userMapper.findById(news.getCreator());
        newsDTO.setUser(user);
        return newsDTO;
    }

    public void createOrUpdate(News news) {
        if (news.getId() == null) {
            news.setGmtCreate(System.currentTimeMillis());
            news.setGmtModified(news.getGmtCreate());
            newsMapper.create(news);
        } else {
            news.setGmtModified(System.currentTimeMillis());
            newsMapper.update(news);
        }
    }
}
