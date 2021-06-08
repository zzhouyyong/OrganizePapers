package com.papers.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zhouyong
 * @date 2021/2/27 22:27
 */
@Table(name = "paper_exam")
public class paperExam {
    private Integer id;

    @Column(name = "exam_name")
    private String examName;

    @Column(name = "exam_type")
    private Integer examType;

    @Column(name = "exam_remark")
    private String examRemark;

    @Column(name = "exam_number")
    private Integer examNumber;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    @Column(name = "delete_flag")
    private Integer deleteFlag;

    public paperExam() {
    }

    public paperExam(Integer id, String examName, Integer examType, String examRemark, Integer examNumber, Date createTime, Integer deleteFlag) {
        this.id = id;
        this.examName = examName;
        this.examType = examType;
        this.examRemark = examRemark;
        this.examNumber = examNumber;
        this.createTime = createTime;
        this.deleteFlag = deleteFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }

    public String getExamRemark() {
        return examRemark;
    }

    public void setExamRemark(String examRemark) {
        this.examRemark = examRemark;
    }

    public Integer getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(Integer examNumber) {
        this.examNumber = examNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public String toString() {
        return "paperExam{" +
                "id=" + id +
                ", examName='" + examName + '\'' +
                ", examType=" + examType +
                ", examRemark='" + examRemark + '\'' +
                ", examNumber=" + examNumber +
                ", createTime=" + createTime +
                ", deleteFlag=" + deleteFlag +
                '}';
    }
}
