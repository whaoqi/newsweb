package com.next.newsweb.service;

import com.next.newsweb.dto.NewsDTO;
import com.next.newsweb.dto.NewsQueryDTO;
import com.next.newsweb.dto.PaginationDTO;
import com.next.newsweb.mapper.AttentiontagMapper;
import com.next.newsweb.mapper.NewsExtMapper;
import com.next.newsweb.mapper.UserMapper;
import com.next.newsweb.model.Attentiontag;
import com.next.newsweb.model.AttentiontagExample;
import com.next.newsweb.model.News;
import com.next.newsweb.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttentiontagService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AttentiontagMapper attentiontagMapper;

    @Autowired
    private NewsExtMapper newsExtMapper;

    public List<Attentiontag> taglist(Long userId) {

        AttentiontagExample attentiontagExample = new AttentiontagExample();
        attentiontagExample.createCriteria()
                .andUseridEqualTo(userId);
        attentiontagExample.setOrderByClause("id desc");/*倒序排列*/
        List<Attentiontag> attentiontags = attentiontagMapper.selectByExample(attentiontagExample);

        return attentiontags;
    }

    public PaginationDTO list(String tag, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        NewsQueryDTO newsQueryDTO = new NewsQueryDTO();
        if (StringUtils.isNotBlank(tag)) {
            tag = tag.replace("+", "").replace("*", "").replace("?", "");
            newsQueryDTO.setTag(tag);
        }

        Integer totalCount = newsExtMapper.countBySearch(newsQueryDTO);
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
        newsQueryDTO.setSize(size);
        newsQueryDTO.setPage(offset);
        List<News> newses = newsExtMapper.selectBySearch(newsQueryDTO);

        List<NewsDTO> newsDTOList = new ArrayList<>();

        for (News news : newses) {
            User user = userMapper.selectByPrimaryKey(news.getCreator());
            NewsDTO newsDTO = new NewsDTO();//把news转换为newsDTO,把news和user封装到一个类里
            BeanUtils.copyProperties(news, newsDTO);//spring自带方法完成上述拷贝
            newsDTO.setUser(user);
            newsDTOList.add(newsDTO);
        }

        paginationDTO.setData(newsDTOList);
        return paginationDTO;
    }
}
