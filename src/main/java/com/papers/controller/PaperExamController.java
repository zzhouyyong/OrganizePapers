package com.papers.controller;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperExam;
import com.papers.domain.response.responseData;
import com.papers.service.paperExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author zhouyong
 * @date 2021/2/27 22:26
 */
@RestController
@RequestMapping("/paper")
public class PaperExamController {

    @Autowired
    private paperExamService examService;


    /**
     * 添加题库
     * @param exam
     * @return
     */
    @PostMapping("/addExam")
    public responseData addExam(@RequestBody paperExam exam){
        try {
            exam.setCreateTime(new Date());
            Integer integer = examService.addExam(exam);
            if( 1 == integer ) {
                return new responseData(200, "success");
            }
            return new responseData(500, "fail");
        }catch (Exception e){
            return new responseData(500,"网络异常", "【" + e.getMessage() + "】");
        }
    }

    /**
     * 查询所有题库
     * @return
     */
    @GetMapping("/findAllExam")
    public responseData findAllExam() {
        try {
            List<paperExam> allExam = examService.findAllExam();
            for( paperExam p : allExam ) {
                Integer examNumber = examService.findExamNumber(p.getId());
                p.setExamNumber(examNumber);
            }
            return new responseData(200, "success", allExam);
        }catch (Exception e){
            return new responseData(500,"网络异常", "【" + e.getMessage() + "】");
        }
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @GetMapping("/deleteExam")
    public responseData deleteExam(@RequestParam String ids){
        try {
            Integer integer = examService.deleteExam(ids);
            if( integer > 0 ) {
                return new responseData(200, "success");
            }
            return new responseData(500, "fail");
        }catch (Exception e){
            return new responseData(500,"网络异常", "【" + e.getMessage() + "】");
        }
    }

    /**
     * 通过一系列查询条件查询
     * @return
     */
    @PostMapping("/findExamByParams")
    public responseData findExamByParams(@RequestBody JSONObject jsonObject) {
        try {
            List<paperExam> examByParams = examService.findExamByParams(jsonObject);
            if( null != examByParams ) {
                return new responseData(200, "success", examByParams);
        }
            return new responseData(500, "fail");
        }catch (Exception e){
            return new responseData(500,"网络异常", "【" + e.getMessage() + "】");
        }
    }

    /**
     * 更新题库
     * @param exam
     * @return
     */
    @PostMapping("/updateExamById")
    public responseData updateExamById(@RequestBody paperExam exam){
        try {
            Integer integer = examService.updateExamById(exam);
            if( integer > 0 ) {
                return new responseData(200, "success");
            }
            return new responseData(500, "fail");
        }catch(Exception e){
            e.printStackTrace();
            return new responseData(500,"网络异常", "【" + e.getMessage() + "】");
        }
    }
}
