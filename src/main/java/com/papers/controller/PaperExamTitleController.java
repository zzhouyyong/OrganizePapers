package com.papers.controller;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperExamDetailAnswer;
import com.papers.domain.paperExamTitle;
import com.papers.domain.paperTest;
import com.papers.domain.response.responseData;
import com.papers.service.paperExamDetailAnswerService;
import com.papers.service.paperExamTitleService;
import com.papers.utils.GA.Global;
import com.papers.utils.GA.RuleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.awt.image.ImageWatched;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 * @author zhouyong
 * @date 2021/3/4 23:23
 */
@RestController
@RequestMapping("/paperexam")
public class PaperExamTitleController {

    @Autowired
    private paperExamTitleService examTitleService;

    @Autowired
    private paperExamDetailAnswerService examDetailAnswerService;

    private paperTest[] papers;

    /**
     * 查询指定题库的所有试题
     * @return
     */
    @GetMapping("/findAllExamTitle")
    public responseData findAllExamTitle(@RequestParam Integer id){
        try {
            List<paperExamTitle> allExamTitle = examTitleService.findAllExamTitle(id);
            return new responseData(200, "success", allExamTitle);
        }catch (Exception e){
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }

    /**
     * 通过条件模糊查询
     * @param jsonObject
     * @return
     */
    @PostMapping("/findExamTitleByParams")
    public responseData findExamTitleByParams(@RequestBody JSONObject jsonObject){
        try {
            LinkedHashMap<String,Object> linkedHashMap = new LinkedHashMap();
            // 查询指定id的题目
            List<paperExamTitle> allExamTitle = examTitleService.findExamTitleByParams(jsonObject);
            // 查询指定id的题目答案
            if( null != jsonObject.get("id") ) {
                List<paperExamDetailAnswer> examDetailAnswerByParams = examDetailAnswerService.findExamDetailAnswerByParams(jsonObject);
                linkedHashMap.put("paperExamDetailAnswer", examDetailAnswerByParams);
            }
            linkedHashMap.put("paperExamTitle", allExamTitle);
            return new responseData(200, "success", linkedHashMap);
        }catch (Exception e){
            e.printStackTrace();
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }

    /**
     * 根据试题类型查询试题
     * @param types
     * @return
     */
    @GetMapping("/findExamTitleByTitleType")
    public responseData findExamTitleByTitleType(@RequestParam String types){
        try {
            List<paperExamTitle> examTitleByTitleType = examTitleService.findExamTitleByTitleType(types);
            return new responseData(200, "success", examTitleByTitleType);
        }catch(Exception e){
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }

    /**
     * 删除试题
     * @param ids
     * @return
     */
    @GetMapping("/deleteExamTitle")
    public responseData deleteExamTitle(@RequestParam String ids) {
        try {
            Integer integer = examTitleService.deleteExamTitle(ids);
            if( integer > 0 ) {
                // 删除题目中的答案
                Integer integer1 = examDetailAnswerService.deleteExamDetailAnswer(ids);
                if( integer1 > 0 ){
                    return new responseData(200, "success");
                }
            }
            return new responseData(500, "fail");
        }catch(Exception e){
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }

    @PostMapping("/updateExamTitleById")
    public responseData updateExamTitleById(@RequestBody paperExamTitle examTitle, HttpServletRequest request){
        try {
            examTitle.setUpdateTime(new Date());
            examTitle.setUpdateBy(request.getSession().getAttribute("login-status").toString());
            Integer integer = examTitleService.updateExamTitleById(examTitle);
            List<Object> tableList = examTitle.getTableList();
            paperExamDetailAnswer answer = new paperExamDetailAnswer();
            LinkedHashMap<String,Object> linkedHashMap = new LinkedHashMap<>();
            Integer index = 1;
            for( Object t : tableList ) {
                linkedHashMap = (LinkedHashMap<String,Object>) t;
                // 题目id
                answer.setId((Integer) linkedHashMap.get("id"));
                answer.setExamDetailId(examTitle.getId());
                answer.setExamDetailAnswer((Boolean) linkedHashMap.get("examDetailAnswer" + index) == true ? 1 : 0);
                answer.setExamDetailContent(linkedHashMap.get("examDetailContent" + index).toString());
                answer.setExamDetailExplain(linkedHashMap.get("examDetailExplain" + index).toString());
                index++;
                examDetailAnswerService.updateExamDetailAnswerById(answer, request);
            }
            if( integer > 0 ) {
                return new responseData(200, "success");
            }
            return new responseData(500, "fail");
        }catch(Exception e){
            e.printStackTrace();
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }

    @PostMapping("/addExamTitle")
    public responseData addExamTitle(@RequestBody paperExamTitle examTitle, HttpServletRequest request){
        try {
            examTitle.setCreateTime(new Date());
            examTitle.setCreateBy(request.getSession().getAttribute("login-status").toString());
            Integer integer = examTitleService.addExamTitle(examTitle);
            Integer id = examTitle.getId();
            // 存试题答案
            List<Object> tableList = examTitle.getTableList();
            paperExamDetailAnswer examDetailAnswer = new paperExamDetailAnswer();
            Integer index = 1;
            for( Object t : tableList) {
                LinkedHashMap<String,Object> map = (LinkedHashMap<String,Object>) t;
                examDetailAnswer.setExamDetailId(id);
                examDetailAnswer.setExamDetailAnswer(true == (Boolean) (map.get("examDetailAnswer" + String.valueOf(index))) ? 1 : 0);
                examDetailAnswer.setExamDetailContent(map.get("examDetailContent" + String.valueOf(index)).toString());
                examDetailAnswer.setExamDetailExplain(map.get("examDetailExplain" + String.valueOf(index)).toString());
                examDetailAnswerService.addExamDetailAnswer(examDetailAnswer, request);
                index++;
            }
            if( 1 == integer ){
                return new responseData(200, "success");
            }
            return new responseData(500, "fail");
        }catch(Exception e){
            e.printStackTrace();
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }
}
