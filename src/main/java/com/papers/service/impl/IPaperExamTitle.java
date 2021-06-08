package com.papers.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperExamTitle;
import com.papers.mapper.paperExamTitleMapper;
import com.papers.service.paperExamTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhouyong
 * @date 2021/3/4 23:19
 */
@Service
public class IPaperExamTitle implements paperExamTitleService {

    @Autowired
    private paperExamTitleMapper examTitleMapper;

    @Override
    public Integer addExamTitle(paperExamTitle exam) {
        Integer titleLevel = exam.getTitleLevel();
        if( titleLevel == 1 ) {
            exam.setTitleDiffculty(0.7);
            exam.setTitlePoint(1);
        }
        if (titleLevel == 2) {
            exam.setTitleDiffculty(0.75);
            exam.setTitlePoint(2);
        }
        if( titleLevel == 3 ) {
            exam.setTitleDiffculty(0.9);
            exam.setTitlePoint(3);
        }
        return examTitleMapper.addExamTitle(exam);
    }

    @Override
    public Integer deleteExamTitle(String ids) {
        return examTitleMapper.deleteExamTitle(ids);
    }

    @Override
    public Integer updateExamTitleById(paperExamTitle exam) {
        Integer titleLevel = exam.getTitleLevel();
        if( titleLevel == 1 ) {
            exam.setTitleDiffculty(0.7);
            exam.setTitlePoint(1);
        }
        if (titleLevel == 2) {
            exam.setTitleDiffculty(0.75);
            exam.setTitlePoint(2);
        }
        if( titleLevel == 3 ) {
            exam.setTitleDiffculty(0.9);
            exam.setTitlePoint(3);
        }
        return examTitleMapper.updateExamTitleById(exam);
    }

    @Override
    public List<paperExamTitle> findExamTitleByParams(JSONObject jsonObject) {
        return examTitleMapper.findExamTitleByParams(jsonObject);
    }

    @Override
    public List<paperExamTitle> findExamTitleByTitleType(String types) {
        return examTitleMapper.findExamTitleByTitleType(types);
    }

    @Override
    public List<paperExamTitle> findAllExamTitle(Integer id) {
        return examTitleMapper.findAllExamTitle(id);
    }

    @Override
    public Integer calTestPoint(String ids) {
        return examTitleMapper.calTestPoint(ids);
    }
}
