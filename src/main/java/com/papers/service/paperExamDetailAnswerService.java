package com.papers.service;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperExamDetailAnswer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhouyong
 * @date 2021/3/11 10:14
 */
public interface paperExamDetailAnswerService {
    Integer addExamDetailAnswer(paperExamDetailAnswer exam, HttpServletRequest request);
    Integer deleteExamDetailAnswer(String ids);
    Integer updateExamDetailAnswerById(paperExamDetailAnswer exam, HttpServletRequest request);
    List<paperExamDetailAnswer> findExamDetailAnswerByParams(JSONObject jsonObject);
    List<paperExamDetailAnswer> findAllExamDetailAnswer(Integer id);
}
