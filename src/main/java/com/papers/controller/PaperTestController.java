package com.papers.controller;

import com.alibaba.fastjson.JSONObject;
import com.papers.domain.paperExamTitle;
import com.papers.domain.paperTest;
import com.papers.domain.paperTestVo;
import com.papers.domain.response.responseData;
import com.papers.service.paperExamTitleService;
import com.papers.service.paperTestService;
import com.papers.utils.AutoToPaperUtil;
import com.papers.utils.GA.Global;
import com.papers.utils.GA.RuleBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author zhouyong
 * @date 2021/3/9 10:59
 */
@RestController
@RequestMapping("/papertest")
public class PaperTestController {

    private final Logger logger = LoggerFactory.getLogger(PaperTestController.class);

    @Autowired
    private paperTestService testService;

    @Autowired
    private paperExamTitleService examTitleService;

    /**
     * 试卷集合，种群
     */
    private paperTest[] papers;

    /**
     * 变异概率
     */
    private static final double mutationRate = 0.085;

    /**
     * 精英主义:每一代个体中较好的一部分个体，不参与交叉和变异，直接保存到下一代。
     */
    private static final boolean elitism = true;

    /**
     * 淘汰数组大小
     */
    private static final int tournamentSize = 5;

    /**
     * 查询所有考试
     * @return
     */
    @GetMapping("/findAllTestTitle")
    public responseData findAllTestTitle(){
        try {
            List<paperTest> allTestTitle = testService.findAllTestTitle();
            return new responseData(200,"success", allTestTitle);
        }catch (Exception e){
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @GetMapping("/deleteTestTitle")
    public responseData deleteTestTitle(@RequestParam String ids){
        try {
            Integer integer = testService.deleteTestTitle(ids);
            if( integer > 0 ) {
                return new responseData(200, "success");
            }
            return new responseData(500, "fail");
        }catch(Exception e){
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }

    /**
     * 统计单选题、多选题、简答题的试题数量
     * @return
     */
    @GetMapping("/findAllTestTitleByAuto")
    public responseData findAllTestTitleByAuto(@RequestParam Integer examId, @RequestParam Integer titleLevel) {
        try {
            // 查询所有试题
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("examId", examId);
            jsonObject.put("titleLevel", titleLevel);
            List<paperExamTitle> examTitleByParams = examTitleService.findExamTitleByParams(jsonObject);
            HashMap<String,Object> map = new HashMap<>();
            // 统计单选题、多选题、简答题的试题数量
            int singleChoose = 0, multipleChoose = 0, questionsChoose = 0;
            for (paperExamTitle testTitleByParam : examTitleByParams) {
                if( testTitleByParam.getTitleType() == 1 ) {
                    singleChoose++;
                }
                if( testTitleByParam.getTitleType() == 2 ) {
                    multipleChoose++;
                }
                if( testTitleByParam.getTitleType() == 4 ) {
                    questionsChoose++;
                }
            }
            map.put("singleChoose", singleChoose);
            map.put("multipleChoose", multipleChoose);
            map.put("questionsChoose", questionsChoose);
            return new responseData(200, "success", map);
        }catch(Exception e){
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }


    /**
     * 添加考试 - 自动组卷
     * @return
     */
    @PostMapping("/addTestTitleByAuto")
    public responseData addTestTitleByAuto(@RequestBody paperTestVo testVo, HttpServletRequest request) {
        try {
            // 设置规则bean
            RuleBean ruleBean = new RuleBean();
            ruleBean.setId(1);
            ruleBean.setSingleNum(Integer.parseInt(testVo.getSingleChoose1()));
            ruleBean.setSingleScore(1);
            ruleBean.setCompleteNum(Integer.parseInt(testVo.getMultipleChoose1()));
            ruleBean.setCompleteScore(2);
            ruleBean.setSubjectiveNum(Integer.parseInt(testVo.getQuestionsChoose1()));
            ruleBean.setSubjectiveScore(3);
            ruleBean.setCreateBy("zhouyong");
            ruleBean.setCreateTime(new Date());
            ruleBean.setDifficulty(0.90);
            Double score = new Double(ruleBean.getSingleNum() * ruleBean.getSingleScore()
                    + ruleBean.getCompleteNum() * ruleBean.getCompleteNum()
                    + ruleBean.getSubjectiveNum() * ruleBean.getSubjectiveNum());
            ruleBean.setTotalMark(score.intValue());
            ruleBean.setStartTime(testVo.getStartTime());
            ruleBean.setEndTime(testVo.getEndTime());
            ruleBean.setTestName(testVo.getTestName());
            ruleBean.setTestPassPoint(testVo.getTestPassPoint());
            ruleBean.setTestTime(testVo.getTestTime());

            // 获取所有的种群，设定初始化种群容量大小为5
            paperTest[] populations = getPopulations(5, true, ruleBean);

            // 定义 交叉和突变计数器 和 适应度期望值
            int count = 0;
            int circulateCount = 10;
            double expand = 0.85;
            paperTest resultPaper = null;
            StringBuffer concatString = new StringBuffer();
            // 选择算子选择出最优的个体
            double adaptationDegree = this.getFitness().getAdaptationDegree();
            // 若适应度已经高于期望值的话，则直接输出
            if( adaptationDegree >= expand ){
                resultPaper = this.getFitness();
            }else{
                while (count < circulateCount && this.getFitness().getAdaptationDegree() < expand) {
                    count++;
                    // 进行交叉算子、突变算子
                    populations = evolvePopulation(populations, ruleBean);
                    System.out.println("第 " + count + " 次进化，适应度为： " + this.getFitness().getAdaptationDegree());
                }
                System.out.println("进化次数： " + count);
                System.out.println("最终适应度为： " + this.getFitness().getAdaptationDegree());
                // 选出最优试卷
                resultPaper = this.getFitness();
            }
            resultPaper.setExamId(resultPaper.getQuestionList().get(0).getExamId());
            for (paperExamTitle examTitle : resultPaper.getQuestionList()) {
                concatString.append(examTitle.getId()).append(",");
            }
            resultPaper.setExamDetailId(concatString.toString().substring(0,concatString.length() - 1));
            resultPaper.setTestPoint(ruleBean.getTotalMark());
            // 添加考试
            Integer integer = testService.addTestTitle(resultPaper);
            if( integer > 0 ) {
                return new responseData(0, "success");
            }
            return new responseData(-1,"fail");
        }catch(Exception e){
            logger.error(e.getMessage());
            return new responseData(500, "服务异常" , e.getMessage());
        }
    }

    /**
     * 添加考试 - 自由组卷
     * @param test
     * @return
     */
    @PostMapping("/addTestTitle")
    public responseData addTestTitle(@RequestBody paperTest test, HttpServletRequest request) {
        try {
            // 设置总分
            String examDetailId = test.getExamDetailId();
            Integer testPoint = examTitleService.calTestPoint(examDetailId);
            test.setTestPoint(testPoint);
            // 设置创建时间和创建人
            test.setCreateTime(new Date());
            test.setCreateBy(request.getSession().getAttribute("login-status").toString());
            Integer integer = testService.addTestTitle(test);
            if( integer > 0 ) {
                return new responseData(200, "success");
            }
            return new responseData(500,"fail");
        }catch(Exception e){
            e.printStackTrace();
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }


    /**
     * 通过查询条件查询考试
     * @param jsonObject
     * @return
     */
    @PostMapping("/findTestTitleByParams")
    public responseData findTestTitleByParams(@RequestBody JSONObject jsonObject){
        try {
            List<paperTest> testTitleByParams = testService.findTestTitleByParams(jsonObject);
            return new responseData(200,"success", testTitleByParams);
        }catch(Exception e){
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }

    /**
     * 根据id查询考试
     * @param id
     * @return
     */
    @GetMapping("/findTestTitleById")
    public responseData findTestTitleById(@RequestParam Integer id){
        try {
            paperTest testTitleById = testService.findTestTitleById(id);
            // 获取题目id
            String[] split = testTitleById.getExamDetailId().split(",");
            // 根据id查询题目
            JSONObject jsonObject = new JSONObject();
            LinkedList<paperExamTitle> paperExamTitleLinkedList = new LinkedList<>();
            HashMap<String,Object> hashMap = new HashMap<>();
            if( !"".equals(split[0]) ) {
                for( String s : split ) {
                    jsonObject.put("id", Integer.parseInt(s));
                    List<paperExamTitle> examTitleByParams = examTitleService.findExamTitleByParams(jsonObject);
                    paperExamTitleLinkedList.add(examTitleByParams.get(0));
                    jsonObject.remove("id");
                }
            }
            hashMap.put("testTitleById",testTitleById);
            hashMap.put("examTitle",paperExamTitleLinkedList);
            return new responseData(200,"success", hashMap);
        }catch(Exception e){
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }

    /**
     * 根据id更新试卷信息
     * @param test
     * @param request
     * @return
     */
    @PostMapping("/updateTestTitleById")
    public responseData updateTestTitleById(@RequestBody paperTest test, HttpServletRequest request){
        try {
            test.setUpdateTime(new Date());
            test.setUpdateBy(request.getSession().getAttribute("login-status").toString());
            Integer integer = testService.updateTestTitleById(test);
            if( integer > 0 ) {
                return new responseData(200,"success");
            }
            return new responseData(500,"fail");
        }catch(Exception e){
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }


    /**
     * 对单选题进行打乱
     * @param examTitleByParams
     * @return
     */
    public List<Integer> getSingle(List<paperExamTitle> examTitleByParams) {
        ArrayList<Integer> singleList = new ArrayList<>();
        // 获取单选题数量
        for (paperExamTitle examTitleByParam : examTitleByParams) {
            // 单选题
            if( examTitleByParam.getTitleType() == 1 ) {
                singleList.add(examTitleByParam.getId());
            }
        }
        AutoToPaperUtil.shufflePaperIds(singleList);
        return singleList;
    }


    /**
     * 将多选题打乱
     * @param examTitleByParams
     * @return
     */
    public List<Integer> getMultiple(List<paperExamTitle> examTitleByParams) {
        ArrayList<Integer> multipleList = new ArrayList<>();
        // 获取多选题数量
        for (paperExamTitle examTitleByParam : examTitleByParams) {
            // 多选题
            if( examTitleByParam.getTitleType() == 2 ) {
                multipleList.add(examTitleByParam.getId());
            }
        }
        AutoToPaperUtil.shufflePaperIds(multipleList);
        return multipleList;
    }

    /**
     * 将简答题打乱
     * @param examTitleByParams
     * @return
     */
    public List<Integer> getQuestions(List<paperExamTitle> examTitleByParams) {
        ArrayList<Integer> questionList = new ArrayList<>();
        // 获取简答题数量
        for (paperExamTitle examTitleByParam : examTitleByParams) {
            // 简答题
            if( examTitleByParam.getTitleType() == 4 ) {
                questionList.add(examTitleByParam.getId());
            }
        }
        AutoToPaperUtil.shufflePaperIds(questionList);
        return questionList;
    }

    /**
     * 重载getPopulations方法
     * @param length
     * @return
     */
    public paperTest[] getPopulations(int length){
        papers = new paperTest[length];
        return papers;
    }

    /**
     * 初始化种群
     * @param populationSize 种群个数
     * @param initFlag
     * @param rule 规则bean即从前端传回的参数
     * @return
     */
    public paperTest[] getPopulations(int populationSize, boolean initFlag, RuleBean rule){
        papers = new paperTest[populationSize];
        if (initFlag) {
            paperTest paper;
            Random random = new Random();
            for (int i = 0; i < populationSize; i++) {
                paper = new paperTest();
                paper.setId(i + 1);
                // 获取每份试卷的试题总分
                Integer testPointByPaper = 0;
                while ( testPointByPaper <= rule.getTotalMark() ) {
                    paper.getQuestionList().clear();
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
                        generateQuestion(4, random, rule.getSubjectiveNum(), rule.getSubjectiveScore(),
                                "主观题数量不够，组卷失败", paper);
                    }
                    testPointByPaper += paper.getTestPointByPaper();
                    if( testPointByPaper > rule.getTotalMark()){
                        break;
                    }
                }
                // 计算试卷适应度
                paper.setAdaptationDegree(rule, Global.DIFFCULTY_WEIGHt);
                // 设置一系列参数
                paper.setTestName(rule.getTestName());
                paper.setTestTime(rule.getTestTime());
                paper.setTestPoint(rule.getTotalMark());
                paper.setTestPassPoint(rule.getTestPassPoint());
                paper.setStartTime(rule.getStartTime());
                paper.setEndTime(rule.getEndTime());
                papers[i] = paper;
            }
        }
        return papers;
    }

    /**
     * 为每个个体添加试题
     * @param type
     * @param random
     * @param qustionNum
     * @param score
     * @param errorMsg
     * @param paper
     */
    private void generateQuestion(int type, Random random, int qustionNum, double score,
                                  String errorMsg, paperTest paper) {
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
            // 添加试题
            paper.addQuestion(singleArray.get(index));
            // 保证不会重复添加试题，将已经存入paper的试题和singleArray.size() - j - 1的试题进行替换
            examTitle = singleArray.get(singleArray.size() - j - 1);
            singleArray.set(singleArray.size() - j - 1, singleArray.get(index));
            singleArray.set(index, examTitle);
        }
    }

    /**
     * 获取种群中最优秀个体
     * @return
     */
    public paperTest getFitness() {
        paperTest paper = papers[0];
        for (int i = 1; i < papers.length; i++) {
            if( null == papers[i] ) {
                break;
            }
            if (paper.getAdaptationDegree() < papers[i].getAdaptationDegree()) {
                paper = papers[i];
            }
        }
        return paper;
    }

    /**
     * 获取种群中某个个体
     *
     * @param index
     * @return
     */
    public paperTest getPaper(int index) {
        return papers[index];
    }

    /**
     * 设置种群中某个个体
     *
     * @param index
     * @param paper
     */
    public void setPaper(int index, paperTest paper) {
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
    public paperTest[] getPaper() {
        return papers;
    }

    /**
     * 进化种群
     * @param pop
     * @param rule
     * @return
     */
    public paperTest[] evolvePopulation(paperTest[] pop, RuleBean rule) {
        //Population newPopulation = new Population(pop.getLength());
        paperTest[] populations = getPopulations(pop.length);
        paperTest fitness = null;
        int elitismOffset;
        // 精英主义
        if (elitism) {
            elitismOffset = 1;
            // 保留上一代最优秀个体
            papers = pop;
            fitness = this.getFitness();
            fitness.setId(0);
            papers = populations;
        }
        // 种群交叉操作
        for (int i = elitismOffset; i < populations.length; i++) {
            // 选择适应度较高的个体
            paperTest parent1 = select(pop);
            paperTest parent2 = select(pop);
            while (parent2.getId() == parent1.getId()) {
                // 若两者是一样的，则重新选择算子
                parent2 = select(pop);
            }
            if( i == 1) {
                populations = getPopulations(pop.length);
                papers = populations;
                setPaper(0, fitness);
            }
            // 交叉
            paperTest child = crossover(parent1, parent2, rule);
            child.setId(i);
            setPaper(i, child);
        }
        // 种群变异操作
        paperTest tmpPaper;
        for (int i = elitismOffset; i < populations.length; i++) {
            tmpPaper = getPaper(i);
            // 变异
            mutate(tmpPaper);
            // 计算适应度
            tmpPaper.setAdaptationDegree(rule, Global.DIFFCULTY_WEIGHt);
        }
        return populations;
    }

    /**
     * 交叉算子 - 选择parent1的基因片段（2-6），然后在将没有选到的基因片段（0-2不包含2 and 6-8包含6不包含8）从parent2中取的
     * @param parent1
     * @param parent2
     * @return
     */
    public paperTest crossover(paperTest parent1, paperTest parent2, RuleBean rule) {
        paperTest child = new paperTest(parent1.getQuestionList().size());
        int s1 = (int) (Math.random() * parent1.getQuestionList().size());
        int s2 = (int) (Math.random() * parent1.getQuestionList().size());
        int count = 0;

        // parent1的startPos endPos之间的序列，会被遗传到下一代
        int startPos = s1 < s2 ? s1 : s2;
        int endPos = s1 > s2 ? s1 : s2;
        for (int i = startPos; i < endPos; i++) {
            child.saveQuestion(i, parent1.getQuestionList().get(i));
            count++;
        }

        // 为了去重
        Set<Integer> set = new TreeSet<>();
        for (paperExamTitle examTitle : child.getQuestionList()) {
            if( null == examTitle ) {
                continue;
            }
            set.add(examTitle.getId());
        }

        // 防止出现重复的元素
        for (int i = 0; i < startPos; i++) {
            if (!child.containsQuestion(parent2.getQuestionList().get(i))) {
                child.saveQuestion(i, parent2.getQuestionList().get(i));
            } else {
                // 根据题型数量来获取题型类型
                int type = getTypeByIndex(i, rule);
                // getQuestionArray()用来选择指定类型和知识点的试题数组
                List<paperExamTitle> singleArray = examTitleService.findExamTitleByTitleType(String.valueOf(type));
                if( singleArray.size() > 0 && null != singleArray) {
                    int random = (int) (Math.random() * singleArray.size());
                    while( set.size() == count ) {
                        random = (int) (Math.random() * singleArray.size());
                        set.add(singleArray.get(random).getId());
                    }
                    child.saveQuestion(i, singleArray.get(random));
                    count++;
                }
            }
        }
        for (int i = endPos; i < parent2.getQuestionList().size(); i++) {
            if (!child.containsQuestion(parent2.getQuestionList().get(i))) {
                child.saveQuestion(i, parent2.getQuestionList().get(i));
            } else {
                int type = getTypeByIndex(i, rule);
                List<paperExamTitle> singleArray = examTitleService.findExamTitleByTitleType(String.valueOf(type));
                if( singleArray.size() > 0 && null != singleArray ) {
                    int random = (int) (Math.random() * singleArray.size());
                    while( set.size() == count ) {
                        random = (int) (Math.random() * singleArray.size());
                        set.add(singleArray.get(random).getId());
                    }
                    child.saveQuestion(i, singleArray.get(random));
                    count++;
                }
            }
        }
        // 设置一系列参数
        child.setTestPoint(rule.getTotalMark());
        child.setTestPassPoint(rule.getTestPassPoint());
        child.setTestName(rule.getTestName());
        child.setTestTime(rule.getTestTime());
        child.setStartTime(rule.getStartTime());
        child.setEndTime(rule.getEndTime());
        child.setCreateTime(rule.getCreateTime());
        child.setCreateBy("zhouyong");
        child.setDifficulty(rule.getDifficulty());
        return child;
    }

    /**
     * 突变算子 每个个体的每个基因都有可能突变
     * @param paper
     */
    public void mutate(paperTest paper) {
        paperExamTitle tmpQuestion;
        List<paperExamTitle> list;
        Set<Integer> set = new TreeSet<>();
        int index = 0;
        for (paperExamTitle paperExamTitle : paper.getQuestionList()) {
            set.add(paperExamTitle.getId());
        }
        for (int i = 0; i < paper.getQuestionList().size(); i++) {
            if (Math.random() < mutationRate) {
                // 进行突变，第i道
                tmpQuestion = paper.getQuestionList().get(i);
                // 从题库中获取和变异的题目类型一样分数相同的题目（不包含变异题目）
                list = examTitleService.findExamTitleByTitleType(String.valueOf(tmpQuestion.getTitleType()));
                if (list.size() > 0) {
                    // 随机获取一道，判断变异的题型在已经组成的试题中主键id是否重复，只有不重复才能插入其中
                    while( set.size() == paper.getQuestionList().size() ) {
                        index = (int) (Math.random() * list.size());
                        set.add(list.get(index).getId());
                    }
                    // 设置分数
                    list.get(index).setTitlePoint(tmpQuestion.getTitlePoint());
                    paper.saveQuestion(i, list.get(index));
                    // 保存完要清理set集合，否则会造成试题重复
                    set.remove(list.get(index).getId());
                }
            }
        }
    }

    /**
     * 选择算子
     * @param pop
     */
    private paperTest select(paperTest[] pop) {
        for (int i = 0; i < tournamentSize; i++) {
            setPaper(i, pop[(int)(Math.random() * pop.length)]);
        }
        return getFitness();
    }

    private static int getTypeByIndex(int index, RuleBean rule) {
        int type = 0;
        // 单选
        if (index < rule.getSingleNum()) {
            type = 1;
        } else if (index < rule.getSingleNum() + rule.getCompleteNum()) {
            // 多选
            type = 2;
        } else {
            // 简答
            type = 4;
        }
        return type;
    }
}
