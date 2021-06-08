package com.papers.utils.GA;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

/**
 * @author zhouyong
 * @date 2021/3/29 22:50
 */
public class MainTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        Paper resultPaper = null;
        // 自定义Rule
        RuleBean ruleBean = new RuleBean();
        ruleBean.setId(1);
        ruleBean.setExamId(2);
        ruleBean.setTotalMark(100);
        // 设置难度系数
        ruleBean.setDifficulty(0.09);
        ruleBean.setSingleScore(1);
        ruleBean.setSingleNum(10);
        ruleBean.setCompleteScore(2);
        ruleBean.setCompleteNum(20);
        ruleBean.setSubjectiveScore(3);
        ruleBean.setSubjectiveNum(30);
        ruleBean.setCreateTime(new Date());
        // 迭代计数器
        int count = 0;
        int runCount = 100;
        // 适应度期望值
        double expand = 0.98;
        // 可自己初始化组卷规则rule
        if (ruleBean != null) {
            // 初始化种群
            Population population = new Population(20, true, ruleBean);
            System.out.println("初次适应度  " + population.getFitness().getAdaptationDegree());
            while (count < runCount && population.getFitness().getAdaptationDegree() < expand) {
                count++;
                population = Ga.evolvePopulation(population, ruleBean);
                System.out.println("第 " + count + " 次进化，适应度为： " + population.getFitness().getAdaptationDegree());
            }
            System.out.println("进化次数： " + count);
            System.out.println(population.getFitness().getAdaptationDegree());
            resultPaper = population.getFitness();
        }
        System.out.println(resultPaper);
    }
}
