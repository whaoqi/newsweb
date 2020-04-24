package com.next.newsweb.service;

import com.next.newsweb.mapper.AttentiontagMapper;
import com.next.newsweb.model.Attentiontag;
import com.next.newsweb.model.AttentiontagExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private AttentiontagMapper attentiontagMapper;

    public void addtag(Attentiontag attentiontag) {
        attentiontagMapper.insert(attentiontag);
    }

    public void deletetag(Attentiontag attentiontag) {
        AttentiontagExample attentiontagExample = new AttentiontagExample();
        attentiontagExample.createCriteria()
                .andUseridEqualTo(attentiontag.getUserid())
                .andAttentiontagEqualTo(attentiontag.getAttentiontag());
        attentiontagMapper.deleteByExample(attentiontagExample);
    }
}
