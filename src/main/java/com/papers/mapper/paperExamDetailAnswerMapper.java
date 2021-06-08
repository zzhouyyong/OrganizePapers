package com.papers.mapper;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperExamDetailAnswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhouyong
 * @date 2021/3/11 10:18
 */
public interface paperExamDetailAnswerMapper {
    Integer addExamDetailAnswer(paperExamDetailAnswer exam);
    Integer deleteExamDetailAnswer(@Param("ids") String ids);
    Integer updateExamDetailAnswerById(paperExamDetailAnswer exam);
    List<paperExamDetailAnswer> findExamDetailAnswerByParams(JSONObject jsonObject);
    List<paperExamDetailAnswer> findAllExamDetailAnswer(Integer id);
}
