package com.papers.domain;

/**
 * @author zhouyong
 * @date 2021/3/18 11:44
 */
public class paperTestVo extends paperTest {
    private String titleLevel;
    private String singleChoose1;
    private String multipleChoose1;
    private String questionsChoose1;

    public String getTitleLevel() {
        return titleLevel;
    }

    public void setTitleLevel(String titleLevel) {
        this.titleLevel = titleLevel;
    }

    public String getSingleChoose1() {
        return singleChoose1;
    }

    public void setSingleChoose1(String singleChoose1) {
        this.singleChoose1 = singleChoose1;
    }

    public String getMultipleChoose1() {
        return multipleChoose1;
    }

    public void setMultipleChoose1(String multipleChoose1) {
        this.multipleChoose1 = multipleChoose1;
    }

    public String getQuestionsChoose1() {
        return questionsChoose1;
    }

    public void setQuestionsChoose1(String questionsChoose1) {
        this.questionsChoose1 = questionsChoose1;
    }
}
