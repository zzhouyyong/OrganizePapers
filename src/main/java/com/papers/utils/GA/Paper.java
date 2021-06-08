package com.papers.utils.GA;

import com.papers.domain.paperExamTitle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 个体
 * @author zhouyong
 * @date 2021/3/29 20:46
 */
public class Paper {
    /**
     * 个体id
     */
    private int id;

    /**
     * 适应度
     */
    private double adaptationDegree = 0.00;

    /**
     * 试卷总分
     */
    private double totalScore = 0.00;

    /**
     * 试卷难度系数
     */
    private double difficulty = 0.00;

    /**
     * 个体包含的试题集合
     */
    private List<paperExamTitle> questionList = new ArrayList<>();

    public Paper(int size) {
        for (int i = 0; i < size; i++) {
            questionList.add(null);
        }
    }

    public Paper() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAdaptationDegree() {
        return adaptationDegree;
    }

    public List<paperExamTitle> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<paperExamTitle> questionList) {
        this.questionList = questionList;
    }

    /**
     * 计算试卷总分
     * @return
     */
    public double getTotalScore() {
        if (totalScore == 0) {
            double total = 0;
            for (paperExamTitle examTitle : questionList) {
                total += examTitle.getTitlePoint();
            }
            totalScore = total;
        }
        return totalScore;
    }

    /**
     * 计算试卷个体难度系数 计算公式： 每题难度*分数求和除总分
     * @return
     */
    public double getDifficulty() {
        if (difficulty == 0) {
            double _difficulty = 0;
            for (paperExamTitle examTitle : questionList) {
                _difficulty += examTitle.getTitlePoint() * examTitle.getTitleDiffculty();
            }
            difficulty = _difficulty / getTotalScore();
        }
        return difficulty;
    }

    /**
     * 获取试题数量
     * @return
     */
    public int getQuestionSize() {
        return questionList.size();
    }

    /**
     * 获取指定下标的试题信息
     * @param index
     * @return
     */
    public paperExamTitle getQuestion(int index) {
        return questionList.get(index);
    }

    /**
     * 增加试题
     * @param examTitle
     */
    public void saveQuestion(int index, paperExamTitle examTitle) {
        this.questionList.set(index, examTitle);
        this.totalScore = 0;
        this.adaptationDegree = 0;
        this.difficulty = 0;
        //this.kPCoverage = 0;
    }

    /**
     * 增加试题
     * @param examTitle
     */
    public void addQuestion(paperExamTitle examTitle) {
        this.questionList.add(examTitle);
        this.totalScore = 0;
        this.adaptationDegree = 0;
        this.difficulty = 0;
        //this.kPCoverage = 0;
    }

    /**
     * 计算个体适应度 公式为：f=1-(1-M/N)*f1-|EP-P|*f2
     * 其中M/N为知识点覆盖率，EP为期望难度系数，P为种群个体难度系数，f1为知识点分布的权重
     * ，f2为难度系数所占权重。当f1=0时退化为只限制试题难度系数，当f2=0时退化为只限制知识点分布
     *
     * @param rule 组卷规则
     * @param f1   知识点分布的权重
     * @param f2   难度系数的权重
     */
    public void setAdaptationDegree(RuleBean rule, double f1, double f2) {
        if (adaptationDegree == 0) {
            adaptationDegree = 1 - (1 - 0) * f1 - Math.abs(rule.getDifficulty() - getDifficulty()) * f2;
        }
    }

    /**
     * @param examTitle
     * @return
     */
    public boolean containsQuestion(paperExamTitle examTitle) {
        if (examTitle == null) {
            for (int i = 0; i < questionList.size(); i++) {
                if (questionList.get(i) == null) {
                    return true;
                }
            }
        } else {
            for (paperExamTitle aQuestionList : questionList) {
                if (aQuestionList != null) {
                    if (aQuestionList.equals(examTitle)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
