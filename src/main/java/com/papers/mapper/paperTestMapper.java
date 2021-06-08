package com.papers.mapper;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhouyong
 * @date 2021/3/9 10:43
 */
public interface paperTestMapper {
    Integer addTestTitle(paperTest exam);
    Integer deleteTestTitle(@Param("ids") String ids);
    Integer updateTestTitleById(paperTest exam);
    List<paperTest> findTestTitleByParams(JSONObject jsonObject);
    List<paperTest> findAllTestTitle();
    paperTest findTestTitleById(Integer id);
}
