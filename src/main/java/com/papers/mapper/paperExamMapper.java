package com.papers.mapper;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperExam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhouyong
 * @date 2021/2/27 22:32
 */
public interface paperExamMapper {
    Integer addExam(paperExam exam);
    Integer deleteExam(@Param("ids") String ids);
    Integer updateExamById(paperExam exam);
    Integer findExamNumber(Integer id);
    List<paperExam> findExamByParams(JSONObject jsonObject);
    List<paperExam> findAllExam();
}
