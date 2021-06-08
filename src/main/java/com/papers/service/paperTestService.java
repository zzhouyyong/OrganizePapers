package com.papers.service;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperExamTitle;
import com.papers.domain.paperTest;

import java.util.List;

/**
 * @author zhouyong
 * @date 2021/3/9 10:41
 */
public interface paperTestService {
    Integer addTestTitle(paperTest exam);
    Integer deleteTestTitle(String ids);
    Integer updateTestTitleById(paperTest exam);
    List<paperTest> findTestTitleByParams(JSONObject jsonObject);
    List<paperTest> findAllTestTitle();
    paperTest findTestTitleById(Integer id);
}
