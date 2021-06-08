package com.papers.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperExam;
import com.papers.mapper.paperExamMapper;
import com.papers.service.paperExamService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zhouyong
 * @date 2021/2/27 22:31
 */
@Service
public class IPaperExam implements paperExamService {

    @Autowired
    private paperExamMapper examMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addExam(paperExam exam) {
        exam.setCreateTime(new Date());
        return examMapper.addExam(exam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteExam(String ids) {
        return examMapper.deleteExam(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateExamById(paperExam exam) {
        return examMapper.updateExamById(exam);
    }

    @Override
    public Integer findExamNumber(Integer id) {
        return examMapper.findExamNumber(id);
    }

    @Override
    public List<paperExam> findExamByParams(JSONObject jsonObject) {
        return examMapper.findExamByParams(jsonObject);
    }

    @Override
    public List<paperExam> findAllExam() {
        return examMapper.findAllExam();
    }
}
