package com.papers.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperExamDetailAnswer;
import com.papers.mapper.paperExamDetailAnswerMapper;
import com.papers.service.paperExamDetailAnswerService;
import com.qcloud.cos.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author zhouyong
 * @date 2021/3/11 10:16
 */
@Service
public class IPaperExamDetailAnswer implements paperExamDetailAnswerService {

    @Autowired
    private paperExamDetailAnswerMapper examDetailAnswerMapper;

    @Override
    public Integer addExamDetailAnswer(paperExamDetailAnswer exam, HttpServletRequest request) {
        exam.setCreateTime(new Date());
        exam.setCreateBy(request.getSession().getAttribute("login-status").toString());
        return examDetailAnswerMapper.addExamDetailAnswer(exam);
    }

    @Override
    public Integer deleteExamDetailAnswer(String ids) {
        return examDetailAnswerMapper.deleteExamDetailAnswer(ids);
    }

    @Override
    public Integer updateExamDetailAnswerById(paperExamDetailAnswer exam, HttpServletRequest request) {
        exam.setCreateTime(new Date());
        exam.setCreateBy(request.getSession().getAttribute("login-status").toString());
        return examDetailAnswerMapper.updateExamDetailAnswerById(exam);
    }

    @Override
    public List<paperExamDetailAnswer> findExamDetailAnswerByParams(JSONObject jsonObject) {
        List<paperExamDetailAnswer> examDetailAnswerByParams = null;
        if(!StringUtils.isNullOrEmpty(jsonObject.get("id").toString()) ) {
            examDetailAnswerByParams = examDetailAnswerMapper.findExamDetailAnswerByParams(jsonObject);
        }
        return examDetailAnswerByParams;
    }

    @Override
    public List<paperExamDetailAnswer> findAllExamDetailAnswer(Integer id) {
        return null;
    }
}
