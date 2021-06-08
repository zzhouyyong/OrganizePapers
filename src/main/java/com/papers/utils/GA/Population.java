package com.papers.utils.GA;

import com.papers.domain.paperExamTitle;
import com.papers.service.paperExamTitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * 种群
 * @author zhouyong
 * @date 2021/3/29 20:46
 */
public class Population {
    private Logger logger = LoggerFactory.getLogger(Population.class);

    @Autowired
    private paperExamTitleService examTitleService;

    /**
     * 试卷集合
     */
    private Paper[] papers;

    /**
     * 初始种群
     * @param populationSize 种群规模
     * @param initFlag       初始化标志 true-初始化
     * @param rule           规则bean
     */
    public Population(int populationSize, boolean initFlag, RuleBean rule) {
        papers = new Paper[populationSize];
        if (initFlag) {
            Paper paper;
            Random random = new Random();
            for (int i = 0; i < populationSize; i++) {
                paper = new Paper();
                paper.setId(i + 1);
                while (paper.getTotalScore() != rule.getTotalMark()) {
                    paper.getQuestionList().clear();
                    // String idString = rule.getPointIds().toString();
                    // 单选题
                    if (rule.getSingleNum() > 0) {
                        generateQuestion(1, random, rule.getSingleNum(), rule.getSingleScore(),
                                "单选题数量不够，组卷失败", paper);
                    }
                    // 多选题
                    if (rule.getCompleteNum() > 0) {
                        generateQuestion(2, random, rule.getCompleteNum(), rule.getCompleteScore(),
                                "填空题数量不够，组卷失败", paper);
                    }
                    // 简答题
                    if (rule.getSubjectiveNum() > 0) {
                        generateQuestion(3, random, rule.getSubjectiveNum(), rule.getSubjectiveScore(),
                                "主观题数量不够，组卷失败", paper);
                    }
                }
                // 计算试卷知识点覆盖率
                // paper.setKpCoverage(rule);
                // 计算试卷适应度
                paper.setAdaptationDegree(rule, Global.KP_WEIGHT, Global.DIFFCULTY_WEIGHt);
                papers[i] = paper;
            }
        }
    }

    /**
     * 个体
     * @param type 试题种类
     * @param random
     * @param qustionNum
     * @param score
     * @param errorMsg
     * @param paper
     */
    private void generateQuestion(int type, Random random, int qustionNum, double score,
                                  String errorMsg, Paper paper) {
//        QuestionBean[] singleArray = QuestionService.getQuestionArray(type, idString
//                .substring(1, idString.indexOf("]")));
        List<paperExamTitle> singleArray = examTitleService.findExamTitleByTitleType(String.valueOf(type));
        if (singleArray.size() < qustionNum) {
            return;
        }
        paperExamTitle examTitle;
        for (int j = 0; j < qustionNum; j++) {
            int index = random.nextInt(singleArray.size() - j);
            // 初始化分数
            Double d = new Double(score);
            singleArray.get(index).setTitlePoint(d.intValue());
            paper.addQuestion(singleArray.get(index));
            // 保证不会重复添加试题
            examTitle = singleArray.get(singleArray.size() - j - 1);
            singleArray.set(singleArray.size() - j - 1, singleArray.get(index));
            singleArray.set(index, examTitle);
        }
    }

    /**
     * 获取种群中最优秀个体
     * @return
     */
    public Paper getFitness() {
        Paper paper = papers[0];
        for (int i = 1; i < papers.length; i++) {
            if (paper.getAdaptationDegree() < papers[i].getAdaptationDegree()) {
                paper = papers[i];
            }
        }
        return paper;
    }

    public Population(int populationSize) {
        papers = new Paper[populationSize];
    }

    /**
     * 获取种群中某个个体
     *
     * @param index
     * @return
     */
    public Paper getPaper(int index) {
        return papers[index];
    }

    /**
     * 设置种群中某个个体
     *
     * @param index
     * @param paper
     */
    public void setPaper(int index, Paper paper) {
        papers[index] = paper;
    }

    /**
     * 返回种群规模
     *
     * @return
     */
    public int getLength() {
        return papers.length;
    }

    /**
     * 返回所有个体
     *
     * @return
     */
    public Paper[] getPaper() {
        return papers;
    }
}
