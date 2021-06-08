package com.papers.controller;

import com.papers.domain.paperExamDetailAnswer;
import com.papers.domain.response.responseData;
import com.papers.service.paperExamDetailAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author zhouyong
 * @date 2021/3/11 10:19
 */
@RestController
@RequestMapping("/paperexamdetail")
public class PaperExamDetailAnswerController {

    @Autowired
    private paperExamDetailAnswerService examDetailAnswerService;

    /**
     * 添加答案
     * @param examDetailAnswer
     * @return
     */
//    @PostMapping("/addExamDetailAnswer")
//    public responseData addExamDetailAnswer(@RequestBody paperExamDetailAnswer examDetailAnswer, HttpServletRequest request){
//        try {
//            Integer integer = examDetailAnswerService.addExamDetailAnswer(examDetailAnswer);
//            if( 1 == integer){
//                return new responseData(200, "success");
//            }
//            return new responseData(500, "fail");
//        }catch(Exception e){
//            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
//        }
//    }
}
