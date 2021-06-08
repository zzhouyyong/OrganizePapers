package com.papers.utils.GA;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.*;

/**
 * 规则bean
 * @author zhouyong
 * @date 2021/3/29 20:47
 */
public class RuleBean {
    /**
     * 规则id
     */
    private long id;

    /**
     * 规则对应的考试id
     */
    private long examId;

    /**
     * 试卷总分
     */
    private int totalMark;

    /**
     * 试卷期望难度系数
     */
    private double difficulty;

    /**
     * 单选题数量
     */
    private int singleNum;

    /**
     * 单选题单个分值
     */
    private double singleScore;

    /**
     * 多选题数量
     */
    private int completeNum;

    /**
     * 填空题单个分值
     */
    private double completeScore;

    /**
     * 简答题数量
     */
    private int subjectiveNum;

    /**
     * 主观题单个分值
     */
    private double subjectiveScore;

    /**
     * 考试名称
     */
    private String testName;
    /**
     * 考试时长
     */
    private Integer testTime;

    /**
     * 考试及格分
     */
    private Integer testPassPoint;

    /**
     * 考试状态
     */
    private Integer testState;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;
    private String updateBy;
    private Integer deleteFlag;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public int getSingleNum() {
        return singleNum;
    }

    public void setSingleNum(int singleNum) {
        this.singleNum = singleNum;
    }

    public double getSingleScore() {
        return singleScore;
    }

    public void setSingleScore(double singleScore) {
        this.singleScore = singleScore;
    }

    public int getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(int completeNum) {
        this.completeNum = completeNum;
    }

    public double getCompleteScore() {
        return completeScore;
    }

    public void setCompleteScore(double completeScore) {
        this.completeScore = completeScore;
    }

    public int getSubjectiveNum() {
        return subjectiveNum;
    }

    public void setSubjectiveNum(int subjectiveNum) {
        this.subjectiveNum = subjectiveNum;
    }

    public double getSubjectiveScore() {
        return subjectiveScore;
    }

    public void setSubjectiveScore(double subjectiveScore) {
        this.subjectiveScore = subjectiveScore;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
