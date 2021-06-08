package com.papers.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zhouyong
 * @date 2021/3/11 10:09
 */
@Entity
@Table(name = "papers_exam_detail_answer")
public class paperExamDetailAnswer {
    @Column(name = "id")
    private Integer id;

    @Column(name = "exam_detail_id")
    private Integer examDetailId;

    @Column(name = "exam_detail_answer")
    private Integer examDetailAnswer;

    @Column(name = "exam_detail_content")
    private String examDetailContent;

    @Column(name = "exam_detail_explain")
    private String examDetailExplain;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Column(name = "delete_flag")
    private Integer deleteFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExamDetailId() {
        return examDetailId;
    }

    public void setExamDetailId(Integer examDetailId) {
        this.examDetailId = examDetailId;
    }

    public Integer getExamDetailAnswer() {
        return examDetailAnswer;
    }

    public void setExamDetailAnswer(Integer examDetailAnswer) {
        this.examDetailAnswer = examDetailAnswer;
    }

    public String getExamDetailContent() {
        return examDetailContent;
    }

    public void setExamDetailContent(String examDetailContent) {
        this.examDetailContent = examDetailContent;
    }

    public String getExamDetailExplain() {
        return examDetailExplain;
    }

    public void setExamDetailExplain(String examDetailExplain) {
        this.examDetailExplain = examDetailExplain;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public String toString() {
        return "paperExamDetailAnswer{" +
                "id=" + id +
                ", examDetailId=" + examDetailId +
                ", examDetailAnswer=" + examDetailAnswer +
                ", examDetailContent='" + examDetailContent + '\'' +
                ", examDetailExplain='" + examDetailExplain + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                '}';
    }
}
