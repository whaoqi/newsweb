package com.next.newsweb.cache;

import com.next.newsweb.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Component
@Data
public class HotTagCache {
    private List<String> hots = new ArrayList<>();

    public void updateTags(Map<String, Integer> tags) {
        int max = 10;/*size*/
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);//priorityQueue优先队列->实现大顶堆小顶堆,使用小顶堆。（topN）
        /*给HotTagDTO对象，override compareTo方法*/
        tags.forEach((name, priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            if (priorityQueue.size() < max) {/*小于直接放入*/
                priorityQueue.add(hotTagDTO);
            } else {
                HotTagDTO minHot = priorityQueue.peek();/*最不热门*/
                if (hotTagDTO.compareTo(minHot) > 0) {/*当前标签priority大于最小热度标签，就插入*/
                    priorityQueue.poll();/*最小元素拿出*/
                    priorityQueue.add(hotTagDTO);/*当前元素放入*/
                }
            }
        });


        List<String> sortedTags = new ArrayList<>();

        HotTagDTO poll = priorityQueue.poll();/*poll是最小元素*/
        while (poll != null) {
            sortedTags.add(0, poll.getName());/*找最大的那个排序*/
            poll = priorityQueue.poll();
        }
        hots = sortedTags;
    }
}
