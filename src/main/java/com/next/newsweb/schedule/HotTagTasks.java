package com.next.newsweb.schedule;

import com.next.newsweb.cache.HotTagCache;
import com.next.newsweb.mapper.NewsMapper;
import com.next.newsweb.model.News;
import com.next.newsweb.model.NewsExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000)/*1k毫秒*1min*1h*3h更新一次*/
    public void hotTagSchedule() {
        int offset = 0;
        int limit = 20;
        log.info("hotTagSchedule start {}", new Date());
        List<News> list = new ArrayList<>();

        Map<String, Integer> priorities = new HashMap<>();/*先计算权重值放进map*/
        while (offset == 0 || list.size() == limit) {//需要分页时
            list = newsMapper.selectByExampleWithRowbounds(new NewsExample(), new RowBounds(offset, limit));
            for (News news : list) {
                String[] tags = StringUtils.split(news.getTag(), ",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority != null) {
                        priorities.put(tag, priority + 5 + news.getCommentCount());
                    } else {
                        priorities.put(tag, 5 + news.getCommentCount());
                    }
                }
            }
            offset += limit;
        }
/*        priorities.forEach(
                (k,v) ->{
                    System.out.print(k);
                    System.out.print(":");
                    System.out.print(v);
                    System.out.println();
                }
        );*/
        hotTagCache.updateTags(priorities);/*map不能排序，使用priorityQueue排序（topN）*/
        log.info("hotTagSchedule stop {}", new Date());
    }
}
