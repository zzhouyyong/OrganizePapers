package com.papers.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperTest;
import com.papers.mapper.paperExamTitleMapper;
import com.papers.mapper.paperTestMapper;
import com.papers.service.paperTestService;
import com.qcloud.cos.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhouyong
 * @date 2021/3/9 10:42
 */
@Service
public class IPaperTest implements paperTestService {

    @Autowired
    private paperTestMapper testMapper;

    @Autowired
    private paperExamTitleMapper examTitleMapper;

    @Override
    public Integer addTestTitle(paperTest exam) {
        return testMapper.addTestTitle(exam);
    }

    @Override
    public Integer deleteTestTitle(String ids) {
        return testMapper.deleteTestTitle(ids);
    }

    @Override
    public Integer updateTestTitleById(paperTest exam) {
        String detailId[] = exam.getExamDetailId().split(",");
        Integer id = exam.getId();
        // 传进来的id即要删除的id
        paperTest testTitleById = testMapper.findTestTitleById(id);
        String examDetailId[] = testTitleById.getExamDetailId().split(",");
        StringBuffer stringBuffer = new StringBuffer();
        // 删除某个题目
        if( null == exam.getTestName() ) {
            for (int i = 0; i < examDetailId.length; i++) {// 36,37
                boolean flag = true;
                for (int j = 0; j < detailId.length; j++) {// 36
                    if (detailId[j].equals(examDetailId[i])) {
                        flag = false;
                        break;
                    }
                }
                if (flag == true) {
                    stringBuffer.append(examDetailId[i]);
                    if (i != examDetailId.length - 1) {
                        stringBuffer.append(",");
                    }
                }
            }
        // 更新整个考试
        }else{
            for (int i = 0; i < examDetailId.length; i++){
                for (int j = 0; j < detailId.length; j++) {
                    if (detailId[j].equals(examDetailId[i]) && !detailId[j].equals("") && !examDetailId[i].equals("")) {
                        throw new RuntimeException("考试题目中有存在的题目，请重新选择");
                    }
                }
            }
            for (String s : detailId) {
                if(!"".equals(s)){
                    stringBuffer.append(s).append(",");
                }
            }
            for (String s : examDetailId) {
                if(!"".equals(s)){
                    stringBuffer.append(s).append(",");
                }
            }
            if( stringBuffer.length() > 0 ) {
                stringBuffer.replace(stringBuffer.length() - 1, stringBuffer.length(), "");
            }
        }
        exam.setExamDetailId(stringBuffer.toString());
        exam.setTestPoint(null == examTitleMapper.calTestPoint(stringBuffer.toString()) ? 0 : examTitleMapper.calTestPoint(stringBuffer.toString()));
        return testMapper.updateTestTitleById(exam);
    }

    @Override
    public List<paperTest> findTestTitleByParams(JSONObject jsonObject) {
        List<paperTest> testTitleByParams = testMapper.findTestTitleByParams(jsonObject);
        for( paperTest test : testTitleByParams ) {
            long today = new Date().getTime();
            long startTime = test.getStartTime().getTime();
            long endTime = test.getEndTime().getTime();
            if( startTime > today ) {
                test.setTestState(2);
            }
            if( startTime < today && endTime >= today ) {
                test.setTestState(0);
            }
            if( endTime < today ) {
                test.setTestState(1);
            }
        }
        return testTitleByParams;
    }

    @Override
    public List<paperTest> findAllTestTitle() {
        List<paperTest> allTestTitle = testMapper.findAllTestTitle();
        for( paperTest test : allTestTitle ) {
            long today = new Date().getTime();
            long startTime = test.getStartTime().getTime();
            long endTime = test.getEndTime().getTime();
            if( startTime > today ) {
                test.setTestState(2);
            }
            if( startTime < today && endTime >= today ) {
                test.setTestState(0);
            }
            if( endTime < today ) {
                test.setTestState(1);
            }
        }
        return allTestTitle;
    }

    /**
     * 根据id查询考试
     * @param id
     * @return
     */
    @Override
    public paperTest findTestTitleById(Integer id) {
        return testMapper.findTestTitleById(id);
    }
}
