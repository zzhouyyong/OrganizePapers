package com.papers.mapper;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperExamTitle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhouyong
 * @date 2021/3/4 23:21
 */
public interface paperExamTitleMapper {
    Integer addExamTitle(paperExamTitle exam);
    Integer deleteExamTitle(@Param("ids") String ids);
    Integer updateExamTitleById(paperExamTitle exam);
    List<paperExamTitle> findExamTitleByParams(JSONObject jsonObject);
    List<paperExamTitle> findExamTitleByTitleType(@Param("types") String types);
    List<paperExamTitle> findAllExamTitle(@Param("id") Integer id);
    Integer calTestPoint(@Param("ids") String ids);
}
