package com.papers.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.papers.utils.GA.Paper;
import com.papers.utils.GA.RuleBean;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhouyong
 * @date 2021/3/9 10:26
 */
@Table(name = "paper_test")
public class paperTest {
    private Integer id;
    private Integer examId;
    private String examDetailId;
    /**
     * 考试名称
     */
    private String testName;
    /**
     * 考试时长
     */
    private Integer testTime;
    /**
     * 考试总分
     */
    private Integer testPoint;
    /**
     * 考试适应度
     */
    private double adaptationDegree = 0.00;
    /**
     * 考试难度系数
     */
    private double difficulty = 0.00;
    /**
     * 考试及格分
     */
    private Integer testPassPoint;
    /**
     * 考试状态
     */
    private Integer testState;
    /**
     * 个体包含的试题集合
     */
    private List<paperExamTitle> questionList = new ArrayList<>();

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 删除标记
     */
    private Integer deleteFlag;

    public paperTest(int size) {
        for (int i = 0; i < size; i++) {
            questionList.add(null);
        }
    }

    public paperTest(){}

    /**
     * 计算个体适应度 公式为：f=1-(1-M/N)*f1-|EP-P|*f2
     * 其中M/N为知识点覆盖率，EP为期望难度系数，P为种群个体难度系数，f1为知识点分布的权重
     * ，f2为难度系数所占权重。当f1=0时退化为只限制试题难度系数，当f2=0时退化为只限制知识点分布
     *
     * @param rule 组卷规则
     * @param f2   难度系数的权重
     */
    public void setAdaptationDegree(RuleBean rule, double f2) {
        if (adaptationDegree == 0) {
            adaptationDegree = 1 - Math.abs(rule.getDifficulty() - getDifficulty()) * f2;
        }
    }

    /**
     * 计算个体的难度系数
     * @return
     */
    public double getDifficulty() {
        double _difficulty = 0;
        for (paperExamTitle examTitle : questionList) {
            // 相加每个题目的难度系数
            _difficulty += examTitle.getTitlePoint() * examTitle.getTitleDiffculty();
        }
        difficulty = _difficulty / getTestPointByPaper();
        return difficulty;
    }

    /**
     * 设置难度系数
     * @param difficulty
     */
    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * 增加试题
     * @param examTitle
     */
    public void saveQuestion(int index, paperExamTitle examTitle) {
        this.questionList.set(index, examTitle);
        this.testPoint = 0;
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
        this.testPoint = 0;
        this.adaptationDegree = 0;
        this.difficulty = 0;
        //this.kPCoverage = 0;
    }

    /**
     * 计算每个个体的试题总分
     * @return
     */
    public Integer getTestPointByPaper(){
        Integer totalScore = 0;
        for (paperExamTitle paperExamTitle : questionList) {
            if (totalScore == 0) {
                Integer total = 0;
                for (paperExamTitle examTitle : questionList) {
                    total += examTitle.getTitlePoint();
                }
                totalScore = total;
            }
        }
        return totalScore;
    }

    /**
     * 检查是否有重复的试题
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
                    if (aQuestionList.getId() == examTitle.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getExamDetailId() {
        return examDetailId;
    }

    public void setExamDetailId(String examDetailId) {
        this.examDetailId = examDetailId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getTestTime() {
        return testTime;
    }

    public void setTestTime(Integer testTime) {
        this.testTime = testTime;
    }

    public Integer getTestPoint() {
        return testPoint;
    }

    public void setTestPoint(Integer testPoint) {
        this.testPoint = testPoint;
    }

    public double getAdaptationDegree() {
        return adaptationDegree;
    }

    public void setAdaptationDegree(double adaptationDegree) {
        this.adaptationDegree = adaptationDegree;
    }

    public Integer getTestPassPoint() {
        return testPassPoint;
    }

    public void setTestPassPoint(Integer testPassPoint) {
        this.testPassPoint = testPassPoint;
    }

    public Integer getTestState() {
        return testState;
    }

    public void setTestState(Integer testState) {
        this.testState = testState;
    }

    public List<paperExamTitle> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<paperExamTitle> questionList) {
        this.questionList = questionList;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
