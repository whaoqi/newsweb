package com.next.newsweb.cache;

import com.next.newsweb.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO inland = new TagDTO();
        inland.setCategoryName("国内新闻");
        inland.setTags(Arrays.asList("焦点", "本地", "北京", "哈尔滨", "港澳台", "时政要闻", "财经", "A股", "军事", "汽车", "房产", "体育", "司法", "辟谣", "历史", "天气", "彩票", "家居装修", "专题"));
        tagDTOS.add(inland);

        TagDTO international = new TagDTO();
        international.setCategoryName("国际新闻");
        international.setTags(Arrays.asList("国际热点", "海外资讯", "欧洲", "美洲", "美国", "俄罗斯", "东南亚", "日韩", "中东", "非洲"));
        tagDTOS.add(international);


        TagDTO technology = new TagDTO();
        technology.setCategoryName("科技新闻");
        technology.setTags(Arrays.asList("手机", "电脑", "最新发布电子产品", "家用电器", "编程", "科技时事", "航天", "互联网", "直播", "导购"));
        tagDTOS.add(technology);

        TagDTO entertainment = new TagDTO();
        entertainment.setCategoryName("娱乐新闻");
        entertainment.setTags(Arrays.asList("明星", "电影", "电视剧", "小说", "漫画", "动漫", "二次元", "八卦", "sqlite"));
        tagDTOS.add(entertainment);

        TagDTO focus = new TagDTO();
        focus.setCategoryName("热点新闻");
        focus.setTags(Arrays.asList("新冠病毒", "鲍某某案", "辽宁舰", "特朗普", "WHO"));
        tagDTOS.add(focus);
        return tagDTOS;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> StringUtils.isBlank(t) || !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
