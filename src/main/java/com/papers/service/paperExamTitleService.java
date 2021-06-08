package com.papers.service;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperExamTitle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhouyong
 * @date 2021/3/4 23:18
 */
public interface paperExamTitleService {
    Integer addExamTitle(paperExamTitle exam);
    Integer deleteExamTitle(String ids);
    Integer updateExamTitleById(paperExamTitle exam);
    List<paperExamTitle> findExamTitleByParams(JSONObject jsonObject);
    List<paperExamTitle> findExamTitleByTitleType(String types);
    List<paperExamTitle> findAllExamTitle(Integer id);
    Integer calTestPoint(String ids);
}
