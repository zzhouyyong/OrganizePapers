package com.papers.service;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperExam;

import java.util.List;

/**
 * @author zhouyong
 * @date 2021/2/27 22:31
 */
public interface paperExamService {
    Integer addExam(paperExam exam);
    Integer deleteExam(String ids);
    Integer updateExamById(paperExam exam);
    Integer findExamNumber(Integer id);
    List<paperExam> findExamByParams(JSONObject jsonObject);
    List<paperExam> findAllExam();
}
