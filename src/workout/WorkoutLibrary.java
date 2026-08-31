package workout;

import model.Exercise;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class WorkoutLibrary implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String GROUP_LEG = "腿部";
    public static final String GROUP_CHEST = "胸部";
    public static final String GROUP_BACK = "背部";
    public static final String GROUP_CORE = "核心";
    public static final String GROUP_SHOULDER = "肩部";
    public static final String GROUP_ARM = "手臂";

    public static final String CARDIO_JOG = "慢跑";
    public static final String CARDIO_SPRINT = "间歇冲刺跑";
    public static final String CARDIO_ROPE = "跳绳";
    public static final String CARDIO_SWIM = "游泳";
    public static final String CARDIO_CYCLE = "动感单车";
    public static final String CARDIO_WALK = "快走";
    public static final String CARDIO_ELIPTICAL = "椭圆机";

    public static class CardioTemplate implements Serializable {
        private static final long serialVersionUID = 1L;
        public String name;
        public double met;
        public int recommendMin;
        public String tip;

        public CardioTemplate(String name, double met, int recommendMin, String tip) {
            this.name = name;
            this.met = met;
            this.recommendMin = recommendMin;
            this.tip = tip;
        }
    }

    private ArrayList<Exercise> exerciseList;
    private final ArrayList<CardioTemplate> cardioBaseList;

    public WorkoutLibrary() {
        exerciseList = new ArrayList<>();
        cardioBaseList = new ArrayList<>();
        initCardioTemplate();
        initDefaultStrengthExercise();
    }
    public void reloadDefaultExercises() {
        exerciseList.clear();
        initDefaultStrengthExercise();
    }
    
    public Exercise getExerciseByName(String name) {
        for (Exercise ex : exerciseList) {
            if (ex.getName().equals(name)) {
                return ex;
            }
        }
        return null;
    }

    public WorkoutLibrary(ArrayList<Exercise> initList) {
        exerciseList = new ArrayList<>(initList);
        cardioBaseList = new ArrayList<>();
        initCardioTemplate();
    }
    
    private void initDefaultStrengthExercise() {
    	if (!this.exerciseList.isEmpty()) {
            return;
        }
        Exercise benchPress = new Exercise("平板杠铃卧推", WorkoutLibrary.GROUP_CHEST, 4, 10, 6.5);
        benchPress.getMuscleRatios().add(new Exercise.MuscleRatio("胸大肌", 85));

        Exercise inclineDumbbellBench = new Exercise("上斜哑铃卧推", WorkoutLibrary.GROUP_CHEST, 4, 10, 6.4);
        inclineDumbbellBench.getMuscleRatios().add(new Exercise.MuscleRatio("上胸", 82));

        Exercise dips = new Exercise("双杠臂屈伸", WorkoutLibrary.GROUP_CHEST, 3, 12, 6.2);
        dips.getMuscleRatios().add(new Exercise.MuscleRatio("下胸", 70));

        Exercise cableCrossover = new Exercise("绳索夹胸", WorkoutLibrary.GROUP_CHEST, 3, 15, 5.8);
        cableCrossover.getMuscleRatios().add(new Exercise.MuscleRatio("胸肌中缝", 78));

        Exercise pecDeck = new Exercise("蝴蝶机夹胸", WorkoutLibrary.GROUP_CHEST, 3, 12, 5.7);
        pecDeck.getMuscleRatios().add(new Exercise.MuscleRatio("胸大肌", 80));

        Exercise dumbbellFly = new Exercise("平板哑铃飞鸟", WorkoutLibrary.GROUP_CHEST, 3, 12, 5.9);
        dumbbellFly.getMuscleRatios().add(new Exercise.MuscleRatio("胸大肌", 75));

        Exercise machineChestPress = new Exercise("坐姿器械推胸", WorkoutLibrary.GROUP_CHEST, 4, 12, 6.3);
        machineChestPress.getMuscleRatios().add(new Exercise.MuscleRatio("胸大肌", 83));

        Exercise declineBench = new Exercise("下斜杠铃卧推", WorkoutLibrary.GROUP_CHEST, 4, 10, 6.6);
        declineBench.getMuscleRatios().add(new Exercise.MuscleRatio("下胸", 81));

        Exercise wideLatPulldown = new Exercise("宽握高位下拉", WorkoutLibrary.GROUP_BACK, 4, 10, 6.6);
        wideLatPulldown.getMuscleRatios().add(new Exercise.MuscleRatio("背阔肌外侧", 86));

        Exercise closeLatPulldown = new Exercise("窄握高位下拉", WorkoutLibrary.GROUP_BACK, 4, 10, 6.5);
        closeLatPulldown.getMuscleRatios().add(new Exercise.MuscleRatio("背阔肌内侧", 84));

        Exercise barbellRow = new Exercise("杠铃俯身划船", WorkoutLibrary.GROUP_BACK, 4, 10, 6.7);
        barbellRow.getMuscleRatios().add(new Exercise.MuscleRatio("中背部、菱形肌", 87));

        Exercise seatedCableRow = new Exercise("坐姿绳索划船", WorkoutLibrary.GROUP_BACK, 4, 12, 6.4);
        seatedCableRow.getMuscleRatios().add(new Exercise.MuscleRatio("中上背", 85));

        Exercise straightArmPulldown = new Exercise("直臂下压", WorkoutLibrary.GROUP_BACK, 3, 15, 5.6);
        straightArmPulldown.getMuscleRatios().add(new Exercise.MuscleRatio("背阔肌下束", 81));

        Exercise pullUp = new Exercise("引体向上", WorkoutLibrary.GROUP_BACK, 4, 8, 6.8);
        pullUp.getMuscleRatios().add(new Exercise.MuscleRatio("整体背阔肌", 88));

        Exercise hyperextension = new Exercise("山羊挺身", WorkoutLibrary.GROUP_BACK, 3, 12, 5.5);
        hyperextension.getMuscleRatios().add(new Exercise.MuscleRatio("竖脊肌", 79));

        Exercise singleDumbbellRow = new Exercise("单臂哑铃划船", WorkoutLibrary.GROUP_BACK, 4, 10, 6.5);
        singleDumbbellRow.getMuscleRatios().add(new Exercise.MuscleRatio("单侧背阔肌", 83));

        Exercise tBarRow = new Exercise("俯身T杠划船", WorkoutLibrary.GROUP_BACK, 4, 10, 6.9);
        tBarRow.getMuscleRatios().add(new Exercise.MuscleRatio("中下斜方肌", 85));

        Exercise barbellSquat = new Exercise("杠铃深蹲", WorkoutLibrary.GROUP_LEG, 4, 10, 6.8);
        barbellSquat.getMuscleRatios().add(new Exercise.MuscleRatio("股四头肌", 82));

        Exercise legPress = new Exercise("腿举", WorkoutLibrary.GROUP_LEG, 4, 12, 6.7);
        legPress.getMuscleRatios().add(new Exercise.MuscleRatio("股四头肌", 84));

        Exercise walkingLunge = new Exercise("箭步蹲", WorkoutLibrary.GROUP_LEG, 3, 12, 6.6);
        walkingLunge.getMuscleRatios().add(new Exercise.MuscleRatio("股四头肌、臀大肌", 76));

        Exercise legCurl = new Exercise("俯卧腿弯举", WorkoutLibrary.GROUP_LEG, 3, 12, 5.8);
        legCurl.getMuscleRatios().add(new Exercise.MuscleRatio("腘绳肌", 85));

        Exercise legExtension = new Exercise("坐姿腿屈伸", WorkoutLibrary.GROUP_LEG, 3, 15, 5.7);
        legExtension.getMuscleRatios().add(new Exercise.MuscleRatio("股四头肌", 87));

        Exercise hipThrust = new Exercise("臀推", WorkoutLibrary.GROUP_LEG, 4, 10, 6.5);
        hipThrust.getMuscleRatios().add(new Exercise.MuscleRatio("臀大肌", 88));

        Exercise standingCalf = new Exercise("站姿提踵", WorkoutLibrary.GROUP_LEG, 4, 15, 5.2);
        standingCalf.getMuscleRatios().add(new Exercise.MuscleRatio("小腿腓肠肌", 90));

        Exercise seatedCalf = new Exercise("坐姿提踵", WorkoutLibrary.GROUP_LEG, 4, 15, 5.1);
        seatedCalf.getMuscleRatios().add(new Exercise.MuscleRatio("小腿比目鱼肌", 89));

        Exercise hackSquat = new Exercise("哈克深蹲", WorkoutLibrary.GROUP_LEG, 4, 10, 6.7);
        hackSquat.getMuscleRatios().add(new Exercise.MuscleRatio("股四头肌", 83));

        Exercise rdl = new Exercise("罗马尼亚硬拉", WorkoutLibrary.GROUP_LEG, 4, 10, 7.0);
        rdl.getMuscleRatios().add(new Exercise.MuscleRatio("腘绳肌、臀部", 86));

        Exercise seatedDumbbellPress = new Exercise("坐姿哑铃推举", WorkoutLibrary.GROUP_SHOULDER, 4, 10, 6.3);
        seatedDumbbellPress.getMuscleRatios().add(new Exercise.MuscleRatio("三角肌中束", 85));

        Exercise lateralRaise = new Exercise("哑铃侧平举", WorkoutLibrary.GROUP_SHOULDER, 3, 15, 5.4);
        lateralRaise.getMuscleRatios().add(new Exercise.MuscleRatio("三角肌中束", 88));

        Exercise frontRaise = new Exercise("哑铃前平举", WorkoutLibrary.GROUP_SHOULDER, 3, 12, 5.3);
        frontRaise.getMuscleRatios().add(new Exercise.MuscleRatio("三角肌前束", 86));

        Exercise rearDeltFly = new Exercise("俯身哑铃飞鸟", WorkoutLibrary.GROUP_SHOULDER, 3, 15, 5.2);
        rearDeltFly.getMuscleRatios().add(new Exercise.MuscleRatio("三角肌后束", 87));

        Exercise cableFacePull = new Exercise("绳索面拉", WorkoutLibrary.GROUP_SHOULDER, 3, 12, 5.5);
        cableFacePull.getMuscleRatios().add(new Exercise.MuscleRatio("肩后束、斜方肌中下束", 84));

        Exercise barbellOverhead = new Exercise("杠铃推举", WorkoutLibrary.GROUP_SHOULDER, 4, 10, 6.4);
        barbellOverhead.getMuscleRatios().add(new Exercise.MuscleRatio("整体三角肌", 82));

        Exercise catCrawl = new Exercise("哑铃招财猫", WorkoutLibrary.GROUP_SHOULDER, 3, 12, 5.0);
        catCrawl.getMuscleRatios().add(new Exercise.MuscleRatio("肩袖肌群", 78));

        Exercise machineShoulderPress = new Exercise("器械肩推", WorkoutLibrary.GROUP_SHOULDER, 4, 12, 6.2);
        machineShoulderPress.getMuscleRatios().add(new Exercise.MuscleRatio("三角肌整体", 84));

        Exercise barbellCurl = new Exercise("杠铃弯举", WorkoutLibrary.GROUP_ARM, 3, 12, 5.6);
        barbellCurl.getMuscleRatios().add(new Exercise.MuscleRatio("肱二头肌", 88));

        Exercise altDumbbellCurl = new Exercise("哑铃交替弯举", WorkoutLibrary.GROUP_ARM, 3, 12, 5.5);
        altDumbbellCurl.getMuscleRatios().add(new Exercise.MuscleRatio("肱二头肌", 86));

        Exercise hammerCurl = new Exercise("锤式弯举", WorkoutLibrary.GROUP_ARM, 3, 12, 5.4);
        hammerCurl.getMuscleRatios().add(new Exercise.MuscleRatio("肱肌、肱桡肌", 85));

        Exercise concentrationCurl = new Exercise("集中弯举", WorkoutLibrary.GROUP_ARM, 3, 12, 5.3);
        concentrationCurl.getMuscleRatios().add(new Exercise.MuscleRatio("肱二头肌峰值", 87));

        Exercise cableCurl = new Exercise("绳索弯举", WorkoutLibrary.GROUP_ARM, 3, 15, 5.2);
        cableCurl.getMuscleRatios().add(new Exercise.MuscleRatio("肱二头肌", 84));

        Exercise tricepPushdown = new Exercise("绳索下压", WorkoutLibrary.GROUP_ARM, 3, 15, 5.3);
        tricepPushdown.getMuscleRatios().add(new Exercise.MuscleRatio("肱三头肌外侧头", 89));

        Exercise overheadDumbbellTricep = new Exercise("颈后哑铃臂屈伸", WorkoutLibrary.GROUP_ARM, 3, 12, 5.4);
        overheadDumbbellTricep.getMuscleRatios().add(new Exercise.MuscleRatio("肱三头肌长头", 86));

        Exercise closePushUp = new Exercise("窄距俯卧撑", WorkoutLibrary.GROUP_ARM, 3, 15, 6.0);
        closePushUp.getMuscleRatios().add(new Exercise.MuscleRatio("肱三头肌", 75));

        Exercise skullCrusher = new Exercise("仰卧臂屈伸", WorkoutLibrary.GROUP_ARM, 3, 12, 5.5);
        skullCrusher.getMuscleRatios().add(new Exercise.MuscleRatio("肱三头肌", 84));

        Exercise singleArmTricep = new Exercise("单臂绳索臂屈伸", WorkoutLibrary.GROUP_ARM, 3, 12, 5.1);
        singleArmTricep.getMuscleRatios().add(new Exercise.MuscleRatio("肱三头肌外侧", 87));

        Exercise plank = new Exercise("平板支撑", WorkoutLibrary.GROUP_CORE, 3, 1, 4.2);
        plank.getMuscleRatios().add(new Exercise.MuscleRatio("腹横肌", 88));

        Exercise crunch = new Exercise("卷腹", WorkoutLibrary.GROUP_CORE, 3, 15, 4.8);
        crunch.getMuscleRatios().add(new Exercise.MuscleRatio("腹直肌上部", 85));

        Exercise legRaise = new Exercise("仰卧举腿", WorkoutLibrary.GROUP_CORE, 3, 12, 4.9);
        legRaise.getMuscleRatios().add(new Exercise.MuscleRatio("腹直肌下部", 86));

        Exercise russianTwist = new Exercise("俄罗斯转体", WorkoutLibrary.GROUP_CORE, 3, 20, 5.0);
        russianTwist.getMuscleRatios().add(new Exercise.MuscleRatio("腹斜肌", 87));

        Exercise hangingLegRaise = new Exercise("悬垂举腿", WorkoutLibrary.GROUP_CORE, 3, 10, 5.8);
        hangingLegRaise.getMuscleRatios().add(new Exercise.MuscleRatio("下腹", 88));

        Exercise mountainClimber = new Exercise("登山跑", WorkoutLibrary.GROUP_CORE, 3, 30, 5.4);
        mountainClimber.getMuscleRatios().add(new Exercise.MuscleRatio("整体腹部", 84));

        Exercise sidePlank = new Exercise("侧平板支撑", WorkoutLibrary.GROUP_CORE, 3, 1, 4.3);
        sidePlank.getMuscleRatios().add(new Exercise.MuscleRatio("腹斜肌", 86));

        Exercise hollowBodyHold = new Exercise("平板收腹支撑", WorkoutLibrary.GROUP_CORE, 3, 1, 4.5);
        hollowBodyHold.getMuscleRatios().add(new Exercise.MuscleRatio("整个核心", 89));

        this.addExercise(benchPress);
        this.addExercise(inclineDumbbellBench);
        this.addExercise(dips);
        this.addExercise(cableCrossover);
        this.addExercise(pecDeck);
        this.addExercise(dumbbellFly);
        this.addExercise(machineChestPress);
        this.addExercise(declineBench);

        this.addExercise(wideLatPulldown);
        this.addExercise(closeLatPulldown);
        this.addExercise(barbellRow);
        this.addExercise(seatedCableRow);
        this.addExercise(straightArmPulldown);
        this.addExercise(pullUp);
        this.addExercise(hyperextension);
        this.addExercise(singleDumbbellRow);
        this.addExercise(tBarRow);

        this.addExercise(barbellSquat);
        this.addExercise(legPress);
        this.addExercise(walkingLunge);
        this.addExercise(legCurl);
        this.addExercise(legExtension);
        this.addExercise(hipThrust);
        this.addExercise(standingCalf);
        this.addExercise(seatedCalf);
        this.addExercise(hackSquat);
        this.addExercise(rdl);

        this.addExercise(seatedDumbbellPress);
        this.addExercise(lateralRaise);
        this.addExercise(frontRaise);
        this.addExercise(rearDeltFly);
        this.addExercise(cableFacePull);
        this.addExercise(barbellOverhead);
        this.addExercise(catCrawl);
        this.addExercise(machineShoulderPress);

        this.addExercise(barbellCurl);
        this.addExercise(altDumbbellCurl);
        this.addExercise(hammerCurl);
        this.addExercise(concentrationCurl);
        this.addExercise(cableCurl);
        this.addExercise(tricepPushdown);
        this.addExercise(overheadDumbbellTricep);
        this.addExercise(closePushUp);
        this.addExercise(skullCrusher);
        this.addExercise(singleArmTricep);

        this.addExercise(plank);
        this.addExercise(crunch);
        this.addExercise(legRaise);
        this.addExercise(russianTwist);
        this.addExercise(hangingLegRaise);
        this.addExercise(mountainClimber);
        this.addExercise(sidePlank);
        this.addExercise(hollowBodyHold);
    }


    private void initCardioTemplate() {
        cardioBaseList.add(new CardioTemplate(CARDIO_JOG, 8.0, 30, "匀速慢跑，心率维持最大心率60%~70%"));
        cardioBaseList.add(new CardioTemplate(CARDIO_SPRINT, 12.5, 15, "冲刺30s+慢走60s循环10组，高效燃脂"));
        cardioBaseList.add(new CardioTemplate(CARDIO_ROPE, 10.0, 20, "间歇跳绳：跳60s休息30s循环"));
        cardioBaseList.add(new CardioTemplate(CARDIO_SWIM, 8.5, 25, "自由泳，零关节压力，适合大体重人群"));
        cardioBaseList.add(new CardioTemplate(CARDIO_CYCLE, 7.5, 35, "中等阻力匀速踩踏，侧重下肢减脂"));
        cardioBaseList.add(new CardioTemplate(CARDIO_WALK, 4.5, 45, "新手、体重偏大首选，不伤膝盖"));
        cardioBaseList.add(new CardioTemplate(CARDIO_ELIPTICAL, 6.8, 30, "全身有氧，膝关节负荷极低"));
    }

    public void addExercise(Exercise e) {
        if (e != null) {
            exerciseList.add(e);
        }
    }

    public ArrayList<Exercise> getByTrainGroup(String trainGroup) {
        ArrayList<Exercise> result = new ArrayList<>();
        for (Exercise ex : exerciseList) {
            if (trainGroup.equals(ex.getTrainGroup())) {
                result.add(ex);
            }
        }
        return new ArrayList<>(result);
    }

    public ArrayList<Exercise> findAllExerciseByMuscle(String targetMuscle) {
        ArrayList<Exercise> result1 = new ArrayList<>();
        for (Exercise ex : exerciseList) {
            if (ex.getMuscleRatio(targetMuscle) > 0) {
                result1.add(ex);
            }
        }
        return new ArrayList<>(result1);
    }

    public ArrayList<Exercise> findMainExerciseByMuscle(String muscleName, double threshold) {
        ArrayList<Exercise> result2 = new ArrayList<>();
        for (Exercise ex : exerciseList) {
            double w = ex.getMuscleRatio(muscleName);
            if (w >= threshold) {
                result2.add(ex);
            }
        }
        return new ArrayList<>(result2);
    }

    public ArrayList<Exercise> generateFullBodyPlan() {
        ArrayList<Exercise> plan = new ArrayList<>();
        plan.addAll(getByTrainGroup(GROUP_LEG));
        plan.addAll(getByTrainGroup(GROUP_CHEST));
        plan.addAll(getByTrainGroup(GROUP_BACK));
        plan.addAll(getByTrainGroup(GROUP_CORE));
        plan.addAll(getByTrainGroup(GROUP_SHOULDER));
        plan.addAll(getByTrainGroup(GROUP_ARM));
        return plan;
    }

    public ArrayList<Exercise> getSingleTargetExercises(String target) {
        ArrayList<Exercise> groupData = getByTrainGroup(target);
        if (!groupData.isEmpty()) {
            return new ArrayList<>(groupData);
        }
        return findAllExerciseByMuscle(target);
    }

    public ArrayList<Exercise> generateTwoTargetPlan(String target1, String target2) {
        ArrayList<Exercise> plan = new ArrayList<>();
        ArrayList<Exercise> list1 = getSingleTargetExercises(target1);
        ArrayList<Exercise> list2 = getSingleTargetExercises(target2);
        plan.addAll(list1);
        for (Exercise ex : list2) {
            if (!plan.contains(ex)) {
                plan.add(ex);
            }
        }
        return plan;
    }

    public ArrayList<Exercise> generatePushPlan() {
        ArrayList<Exercise> push = new ArrayList<>();
        push.addAll(getByTrainGroup(GROUP_CHEST));
        push.addAll(getByTrainGroup(GROUP_SHOULDER));
        push.addAll(getByTrainGroup(GROUP_ARM));
        return push;
    }

    public ArrayList<Exercise> generatePullPlan() {
        ArrayList<Exercise> pull = new ArrayList<>();
        pull.addAll(getByTrainGroup(GROUP_BACK));
        pull.addAll(getByTrainGroup(GROUP_ARM));
        return pull;
    }

    public ArrayList<Exercise> generateLegPlan() {
        ArrayList<Exercise> leg = new ArrayList<>();
        leg.addAll(getByTrainGroup(GROUP_LEG));
        leg.addAll(getByTrainGroup(GROUP_CORE));
        return leg;
    }

    public List<ArrayList<Exercise>> generatePPLThreeSplit() {
        List<ArrayList<Exercise>> ppl = new ArrayList<>();
        ppl.add(generatePushPlan());
        ppl.add(generatePullPlan());
        ppl.add(generateLegPlan());
        return ppl;
    }

    public CardioTemplate getCardioByName(String cardioName) {
        for (CardioTemplate t : cardioBaseList) {
            if (t.name.equals(cardioName)) {
                return t;
            }
        }
        return null;
    }

    public void printAllCardioPlan() {
        System.out.println("\n===== 有氧训练方案库 =====");
        for (CardioTemplate t : cardioBaseList) {
            String line1 = "项目：" + t.name + " | 推荐时长：" + t.recommendMin + "分钟 | MET：" + t.met;
            String line2 = "训练贴士：" + t.tip;
            System.out.println(line1);
            System.out.println(line2);
        }
    }
    public boolean isEmpty() {
        return exerciseList.isEmpty();
    }
}