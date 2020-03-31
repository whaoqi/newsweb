package com.next.newsweb.service;

import com.next.newsweb.dto.NewsDTO;
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

    public List<NewsDTO> list() {
        List<News> newses = newsMapper.list();//查询所有news对象
        List<NewsDTO> newsDTOList = new ArrayList<>();
        for (News news : newses) {
            User user = userMapper.findById(news.getCreator());
            NewsDTO newsDTO = new NewsDTO();//把news转换为newsDTO,把news和user封装到一个类里
            BeanUtils.copyProperties(news, newsDTO);//spring自带方法完成上述拷贝
            newsDTO.setUser(user);
            newsDTOList.add(newsDTO);
        }
        return newsDTOList;
    }
}
