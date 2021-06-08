package com.papers.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * @author zhouyong
 * @date 2021/3/4 23:10
 */
@Table(name = "papers_exam_title")
public class paperExamTitle {
    @Column(name = "id")
    private Integer id;

    @Column(name = "exam_id")
    private Integer examId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "title_name")
    private String titleName;

    @Column(name = "title_type")
    private Integer titleType;

    @Column(name = "title_level")
    private Integer titleLevel;

    @Column(name = "title_point")
    private Integer titlePoint;

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

    private List<Object> tableList;

    /**
     * 难度系数 0.3-1之间
     */
    @Column(name = "title_difficulty")
    private double titleDifficulty;


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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public Integer getTitleType() {
        return titleType;
    }

    public void setTitleType(Integer titleType) {
        this.titleType = titleType;
    }

    public Integer getTitleLevel() {
        return titleLevel;
    }

    public void setTitleLevel(Integer titleLevel) {
        this.titleLevel = titleLevel;
    }

    public Integer getTitlePoint() {
        return titlePoint;
    }

    public void setTitlePoint(Integer titlePoint) {
        this.titlePoint = titlePoint;
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

    public List<Object> getTableList() {
        return tableList;
    }

    public void setTableList(List<Object> tableList) {
        this.tableList = tableList;
    }

    public double getTitleDiffculty() {
        return titleDifficulty;
    }

    public void setTitleDiffculty(double titleDiffculty) {
        this.titleDifficulty = titleDiffculty;
    }

    @Override
    public String toString() {
        return "paperExamTitle{" +
                "id=" + id +
                ", examId=" + examId +
                ", orderId=" + orderId +
                ", titleName='" + titleName + '\'' +
                ", titleType=" + titleType +
                ", titleLevel=" + titleLevel +
                ", titlePoint=" + titlePoint +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                ", tableList=" + tableList +
                ", titleDiffculty=" + titleDifficulty +
                '}';
    }
}
