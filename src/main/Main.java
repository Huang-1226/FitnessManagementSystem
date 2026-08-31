package main;
import model.CardioRecord;
import model.DailyTraining;
import model.Exercise;
import model.Person;
import model.WorkoutLog;
import util.NutritionCalculator;
import workout.WorkoutLibrary;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import model.WeightRecord;
import java.time.LocalDate;
import model.MuscleRecoverRule;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Person user = null;
    private static DailyTraining todayTrain = null;
    private static WorkoutLibrary workoutLibrary = new WorkoutLibrary();
    private static ArrayList<DailyTraining> historyTrainList = new ArrayList<>();
    
    
    private static void saveUser() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.dat"))) {
            oos.writeObject(user);
            System.out.println("个人信息保存成功");
        } catch (Exception e) {
            System.out.println("保存个人信息失败：" + e.getMessage());
        }
    }
   
    private static void saveHistoryTrainList() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("history.dat"))) {
            oos.writeObject(historyTrainList);
            System.out.println("训练历史保存成功");
        } catch (Exception e) {
            System.out.println("保存训练历史失败：" + e.getMessage());
        }
    }
    private static void saveWorkoutLibrary() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("exercise.dat"))) {
            oos.writeObject(workoutLibrary);
            System.out.println("动作库保存成功");
        } catch (Exception e) {
            System.out.println("动作保存失败：" + e.getMessage());
        }
    }
    private static void archiveTodayTrain() {
        checkBaseReady();
        if (todayTrain == null) {
            System.out.println("今日没有训练可归档！");
            return;
        }
        historyTrainList.add(todayTrain);
        String tip = "已归档 " + todayTrain.getDate() + " 的训练记录，可以在历史记录中查看";
        System.out.println(tip);
        todayTrain = null;
    }
    
    private static void viewHistoryTrain() {
        if (historyTrainList == null || historyTrainList.isEmpty()) {
            System.out.println("暂无任何归档的历史训练记录");
            return;
        }
        System.out.println("\n==== 历史归档训练列表 ====");
        for (int i = 0; i < historyTrainList.size(); i++) {
            DailyTraining dt = historyTrainList.get(i);
            double totalBurn = dt.getStrengthBurn() + dt.getCardioBurn();
            String line = "[" + (i + 1) + "] 日期：" + dt.getDate() + "  当日总消耗：" + (int) totalBurn + " kcal";
            System.out.println(line);
        }

        System.out.print("\n输入序号查看详细训练内容，输入0返回：");
        String input = scanner.nextLine().trim();
        int idx;
        try {
            idx = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("输入格式错误，请输入数字序号");
            return;
        }

        if (idx == 0) {
            return;
        }
        if (idx < 1 || idx > historyTrainList.size()) {
            System.out.println("序号不存在");
            return;
        }
        DailyTraining target = historyTrainList.get(idx - 1);

        String detailContent = "\n=========== 该日完整训练记录 ===========\n" + target.showAllDetail();
        System.out.println(detailContent);
    }
    @SuppressWarnings("unchecked")
    private static void loadAllData() {
        File userFile = new File("user.dat");
        if(userFile.exists()){
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile))){
                user = (Person) ois.readObject();
                System.out.println("已加载你的个人档案");
            }catch (Exception ignored){}
        }
        File historyFile = new File("history.dat");
        if (historyFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(historyFile))) {
                historyTrainList = (ArrayList<DailyTraining>) ois.readObject();
                System.out.println("已加载历史训练记录");
            } catch (Exception ignored) {
           }
        }
        File exerciseFile = new File("exercise.dat");
        if(exerciseFile.exists()){
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(exerciseFile))){
                workoutLibrary = (WorkoutLibrary) ois.readObject();
                System.out.println("已加载本地动作库");
            }catch (Exception e){
                System.out.println("动作库读取失败，使用内置默认动作");
            }
        }
    }

    public static void main(String[] args) {
    	loadAllData();
        showMainMenu();
    }


    private static void showMainMenu() {
        while (true) {
            System.out.println("\n========== 健身管理系统 ==========");
            System.out.println("【一、个人身体素质管理】");
            System.out.println("1  创建/编辑个人身体档案（附带BMI体质评估）");
            System.out.println("2 录入今日体重记录");
            System.out.println("3 查看体重变化日志");

            System.out.println("\n【二、训练打卡模块】");
            System.out.println("4  新建今日训练记录");
            System.out.println("5  力量训练打卡");
            System.out.println("6  有氧训练打卡");

            System.out.println("\n【三、训练数据查看】");
            System.out.println("7  统计今日训练总热量消耗");
            System.out.println("8  查看今日完整训练详情");
            System.out.println("9  设置今日训练备注");
            System.out.println("10  归档今日训练（存入历史归档列表）");
            System.out.println("11 查看归档历史训练记录");
            System.out.println("12 查看近7天训练汇总统计");

            System.out.println("\n【四、训练计划 & 饮食方案】");
            System.out.println("13  自动生成力量分化训练计划");
            System.out.println("14 根据目标肌群筛选主打训练动作");
            System.out.println("15 生成个性化饮食营养方案");

            System.out.println("\n【五、数据持久化 & 拓展动作库】");
            System.out.println("16 一键保存全部数据至本地");
            System.out.println("17 热量调试明细（排查消耗归零问题）");
            System.out.println("18 新增力量动作（存入力量动作库）");
            System.out.println("19 自定义新增有氧类型存入有氧库");

            System.out.println("\n【0 退出系统】");
            System.out.print("\n请输入功能序号：");

            int op;
            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("输入格式错误，请输入纯数字序号！");
                continue;
            }

            switch (op) {
                case 1:
                    createUserProfile();
                    break;
                case 2:
                    addWeightRecord();
                    break;
                case 3:
                    showWeightRecord();
                    break;
                case 4:
                    createTodayTrain();
                    break;
                case 5:
                    recordStrengthTrain();
                    break;
                case 6:
                    recordCardioTrain();
                    break;
                case 7:
                    calcTotalBurn();
                    break;
                case 8:
                    showTodayTrainingDetail();
                    break;
                case 9:
                    setTodayTrainRemark(); 
                    break;
                case 10:
                    archiveTodayTrain();
                    break;
                case 11:
                	viewHistoryTrain();
                	break;
                case 12:
                    showLast7DayStat();
                    break;
                case 13:
                    createTrainingPlan();
                    break;
                case 14:
                    filterMuscleMainExercise();
                    break;
                case 15:
                    buildDietPlan();
                    break;
                case 16:
                    saveUser();
                    saveHistoryTrainList();
                    saveWorkoutLibrary();
                    System.out.println(" 用户档案、训练记录、动作库保存完毕");
                    break;
                case 17:
                    debugBurnDetail();
                    break;
                case 18:
                    addStrengthExercise();
                    break;
                case 19:
                	addCardioTraining();
                    break;
                case 0:
                    System.out.println("即将自动保存数据并退出程序...");
                    saveUser();
                    saveHistoryTrainList();
                    saveWorkoutLibrary();
                    scanner.close();
                    return;
                default:
                    System.out.println("不存在该功能序号，请重新选择！");
            }
        }
    }
    private static void createUserProfile() {
        if (user != null && user.getHeight() > 0 && user.getAge() > 0) {
            System.out.print("已存在个人档案，是否重新编辑？(y/n)：");
            String edit = scanner.nextLine().trim();
            if (!"y".equalsIgnoreCase(edit)) {
                System.out.println("放弃编辑，返回菜单");
                return;
            }
        }

        try {
            System.out.print("性别(male/female)：");
            String sex = scanner.nextLine().trim();
            System.out.print("年龄：");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("身高(cm)：");
            double height = Double.parseDouble(scanner.nextLine());
            System.out.print("体重(kg)：");
            double weight = Double.parseDouble(scanner.nextLine());

            user = Person.createPerson(sex, height, weight, age);
            saveUser();
            String autoSaveTip = "个人档案已自动保存";
            System.out.println(autoSaveTip);

            
            System.out.println("\n=== 设置你的日常长期活动水平 ===");
            System.out.println("1 久坐（办公室/学生，几乎无走动）");
            System.out.println("2 轻度活动（每周散步1-3次）");
            System.out.println("3 中度活动（每周规律训练3~5次）");
            System.out.println("4 高强度活动（每周训练6~7次）");
            int actCode;
            while (true) {
                System.out.print("请输入数字1-4：");
                String actStr = scanner.nextLine().trim();
                try {
                    actCode = Integer.parseInt(actStr);
                    if (actCode >= 1 && actCode <= 4) {
                        break;
                    } else {
                        System.out.println("只能输入1、2、3、4，请重试");
                    }
                } catch (Exception e) {
                    System.out.println("格式错误，请输入数字");
                }
            }
            user.setActivityCode(actCode);
          

            System.out.println("个人档案创建成功！");
            double bmi = user.getBMI();
            int bmiInt = (int) (bmi * 100 + 0.5);
            String bmiShow = bmiInt / 100 + "." + bmiInt % 100;
            System.out.println("\n==== 体质评估结果 ====");
            System.out.println("BMI：" + bmiShow);
            System.out.println("BMI评价：" + user.getBMILevel());
            System.out.println("基础代谢BMR：" + (int) user.getBMR() + " kcal");
        } catch (IllegalArgumentException e) {
            System.out.println("创建失败：" + e.getMessage());
        } catch (Exception e) {
            System.out.println("输入格式错误，请输入数字");
        }
    }
    
    private static void addWeightRecord() {
        checkBaseReady();
        System.out.print("请输入今日体重(kg)：");
        String input = scanner.nextLine().trim();
        double weight;
        try{
            weight = Double.parseDouble(input);
        }catch (Exception e){
            System.out.println("输入格式错误！");
            return;
        }
        if(weight <= 0){
            System.out.println("体重必须大于0");
            return;
        }
        LocalDate now = LocalDate.now();
        WeightRecord record = new WeightRecord(now, weight);
        user.addWeightRecord(record);
        saveUser();
        String tip = "体重记录保存成功，日期：" + now + " 体重：" + weight + " kg";
        System.out.println(tip);
    }
    
    private static void showWeightRecord() {
        checkBaseReady();
        ArrayList<WeightRecord> list = user.getWeightRecordList();
        if(list.isEmpty()){
            System.out.println("暂无体重记录");
            return;
        }
        System.out.println("\n====体重追踪记录====");
        for(WeightRecord r : list){
            String line = "日期：" + r.getRecordDate() + "  体重：" + r.getWeight() + " kg";
            System.out.println(line);
        }
    }


    private static void createTodayTrain() {
    	if (todayTrain != null) {
            System.out.println("警告：今日已经存在训练记录，尚未归档！");
            System.out.print("是否清空现有训练、重新创建今日训练？覆盖会丢失今天所有训练数据(y/n)：");
            String opt = scanner.nextLine().trim();
            if (!"y".equalsIgnoreCase(opt)) {
                System.out.println("取消创建。");
                return;
            }
        }
        todayTrain = new DailyTraining(user);
        System.out.println("今日训练容器创建完毕");
    }
    
    private static void recordStrengthTrain() {
        if (todayTrain == null) {
            System.out.println("请先执行功能4：新建今日训练记录");
            return;
        }
        if (user == null) {
            System.out.println("请先创建个人身体档案");
            return;
        }

        
        System.out.print("请输入本次训练的动作名称：");
        String exName = scanner.nextLine().trim();
        Exercise targetEx = workoutLibrary.getExerciseByName(exName);
        
        if (targetEx == null) {
            System.out.println("该动作不存在，可使用功能18添加新动作到动作库");
            return;
        }
        checkMuscleRecoverWarn(targetEx.getTrainGroup());
        WorkoutLog log = new WorkoutLog(targetEx);
        while (true) {
            System.out.println("\n1 录入一组训练数据  0 结束录入该动作，返回菜单");
            System.out.print("选择：");
            String optStr = scanner.nextLine().trim();
            if ("0".equals(optStr)) {
                break;
            }
            if (!"1".equals(optStr)) {
                System.out.println("只能输入0或1");
                continue;
            }

            try {
                System.out.print("本组负重(kg)：");
                double weight = Double.parseDouble(scanner.nextLine());
                System.out.print("本组次数：");
                int rep = Integer.parseInt(scanner.nextLine());
                System.out.print("本组用时(分钟)：");
                int minute = Integer.parseInt(scanner.nextLine());

                log.addSet(weight, rep, minute);
                System.out.println("本组录入成功");
            } catch (NumberFormatException e) {
                System.out.println("错误：必须输入数字");
            } catch (IllegalArgumentException e) {
                System.out.println("录入失败：" + e.getMessage());
            }
        }

        if (log.hasSetRecord()) {
            todayTrain.addWorkoutLog(log);
            System.out.println(exName + " 的全部训练记录已存入今日训练日志");
        } else {
            System.out.println("未录入任何组数，本条训练舍弃");
        }
    }

    private static void recordCardioTrain() {
        if (todayTrain == null) {
            System.out.println("请先执行功能4：新建今日训练记录");
            return;
        }
        if (user == null) {
            System.out.println("请先创建个人身体档案");
            return;
        }

        System.out.println("\n====可选有氧列表====");
        workoutLibrary.printAllCardioPlan();

        System.out.print("输入要训练的有氧名称：");
        String cardioName = scanner.nextLine().trim();

        WorkoutLibrary.CardioTemplate targetCardio = workoutLibrary.getCardioByName(cardioName);
        if (targetCardio == null) {
            System.out.println("未找到该有氧类型");
            return;
        }

        int trainMin;
        try {
            System.out.print("本次有氧训练时长(分钟)：");
            trainMin = Integer.parseInt(scanner.nextLine());
            if (trainMin <= 0) {
                System.out.println("时长必须大于0");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("时长必须输入合法数字");
            return;
        }

       
        CardioRecord cardioRecord = new CardioRecord(targetCardio.name, targetCardio.met, trainMin);
        todayTrain.addCardio(cardioRecord);
        System.out.println("有氧训练打卡成功，已存入今日训练日志");
    }
    
    private static void setTodayTrainRemark() {
        if (todayTrain == null) {
            System.out.println("请先新建今日训练记录！");
            return;
        }
        System.out.print("输入本次训练备注（训练状态、身体感受等）：");
        String note = scanner.nextLine();
        todayTrain.setRemark(note);
        System.out.println("训练备注保存成功");
    }
    
    private static void showLast7DayStat() {
        if(historyTrainList.isEmpty()){
            System.out.println("暂无归档训练数据");
            return;
        }
        LocalDate now = LocalDate.now();
        LocalDate sevenDayAgo = now.minusDays(7);

        double totalBurn = 0;
        int trainDayCount = 0;

        for(DailyTraining dt : historyTrainList){
            LocalDate trainDate = dt.getDate();
            if(trainDate.isAfter(sevenDayAgo) || trainDate.isEqual(sevenDayAgo)){
                trainDayCount = trainDayCount + 1;
                double dayBurn = dt.getStrengthBurn() + dt.getCardioBurn();
                totalBurn = totalBurn + dayBurn;
            }
        }
        double avgBurn = 0;
        if(trainDayCount > 0){
            avgBurn = totalBurn / trainDayCount;
        }
        System.out.println("\n====近7天训练统计====");
        String line1 = "有效训练天数：" + trainDayCount + " 天";
        String line2 = "七天总热量消耗：" + Math.round(totalBurn) + " kcal";
        String line3 = "训练日平均消耗：" + Math.round(avgBurn) + " kcal";
        System.out.println(line1);
        System.out.println(line2);
        System.out.println(line3);
    }
    
    private static void addStrengthExercise() {
        checkBaseReady();
        System.out.print("动作名称：");
        String name = scanner.nextLine();
        System.out.print("所属肌群(腿部/胸部/背部/肩部/核心/手臂)：");
        String group = scanner.nextLine();
        System.out.print("推荐组数：");
        int sets = Integer.parseInt(scanner.nextLine());
        System.out.print("推荐次数：");
        int reps = Integer.parseInt(scanner.nextLine());
        System.out.print("MET值：");
        double met = Double.parseDouble(scanner.nextLine());

        Exercise ex = new Exercise(name, group, sets, reps, met);
        System.out.print("是否录入发力肌肉占比(y/n)：");
        String input = scanner.nextLine();
        if ("y".equalsIgnoreCase(input)) {
            while (true) {
                System.out.print("肌肉名称(输入end结束)：");
                String muscle = scanner.nextLine();
                if ("end".equalsIgnoreCase(muscle)) {
                    break;
                }
                System.out.print("发力占比(百分比数字)：");
                double ratio = Double.parseDouble(scanner.nextLine());
                ex.getMuscleRatios().add(new Exercise.MuscleRatio(muscle, ratio));
            }
        }
        workoutLibrary.addExercise(ex);

        WorkoutLog log = new WorkoutLog(ex);
        workoutLibrary.addExercise(ex);
        saveWorkoutLibrary();
        System.out.println("新动作已自动持久化保存到本地");
        System.out.print("需要录入多少组实际训练数据：");
        int groupCount = Integer.parseInt(scanner.nextLine());
        for (int i = 1; i <= groupCount; i++) {
            System.out.print("第" + i + "组 负重kg：");
            double weight = Double.parseDouble(scanner.nextLine());
            System.out.print("第" + i + "组 完成次数：");
            int realRep = Integer.parseInt(scanner.nextLine());
            System.out.print("第" + i + "组 用时分钟：");
            int useMin = Integer.parseInt(scanner.nextLine());
            log.addSet(weight, realRep, useMin);
        }
        todayTrain.addWorkoutLog(log);
        System.out.println("力量动作录入完成，存入动作库");
    }
    
    private static void addCardioTraining() {
        checkBaseReady();
        workoutLibrary.printAllCardioPlan();
        System.out.print("\n输入有氧项目名称：");
        String cardioName = scanner.nextLine();
        WorkoutLibrary.CardioTemplate template = workoutLibrary.getCardioByName(cardioName);

        CardioRecord cardio;
        if (template == null) {
            System.out.println("无该模板，自定义录入");
            System.out.print("有氧名称：");
            String name = scanner.nextLine();
            System.out.print("MET：");
            double met = Double.parseDouble(scanner.nextLine());
            System.out.print("时长(min)：");
            int min = Integer.parseInt(scanner.nextLine());
            cardio = new CardioRecord(name, met, min);
        } else {
            System.out.print("推荐时长" + template.recommendMin + "分钟，使用推荐时长？(y/n)：");
            String choose = scanner.nextLine();
            int time;
            if ("y".equalsIgnoreCase(choose)) {
                time = template.recommendMin;
            } else {
                System.out.print("自定义时长：");
                time = Integer.parseInt(scanner.nextLine());
            }
            cardio = new CardioRecord(template.name, template.met, time);
            System.out.println("训练小贴士：" + template.tip);
        }
        todayTrain.addCardio(cardio);
        System.out.println("有氧训练添加成功");
    }

    private static void calcTotalBurn() {
    	 if (todayTrain == null || user == null) {
    	        System.out.println("缺少训练记录或个人身体档案");
    	        return;
    	    }
        checkBaseReady();
        double strengthBurn = todayTrain.getStrengthBurn();
        double cardioBurn = todayTrain.getCardioBurn();
        double total = strengthBurn + cardioBurn;

        String line1 = "今日力量训练总消耗：" + (int)(strengthBurn + 0.5) + " kcal";
        String line2 = "今日有氧训练总消耗：" + (int)(cardioBurn + 0.5) + " kcal";
        String line3 = "今日训练合计消耗：" + (int)(total + 0.5) + " kcal";
        System.out.println(line1);
        System.out.println(line2);
        System.out.println(line3);
    }

    private static void buildDietPlan() {
        if (user == null) {
            System.out.println("请先执行【1 创建个人身体档案】");
            return;
        }
        double breakfastRatio = 0.3;
        double lunchRatio = 0.4;
        double dinnerRatio = 0.3;

        int activityCode = user.getActivityCode();
        if (activityCode < 1 || activityCode > 4) {
            System.out.println("活动等级异常，自动切换为等级1");
            activityCode = 1;
        }
        System.out.println("==============================");
        System.out.println("当前存档日常活动等级：" + activityCode);
        System.out.println("1 久坐 | 2 轻度 | 3 中度 | 4 高强度");
        System.out.print("如需临时修改请输入新数字，直接回车则沿用原有配置：");
        String inputAct = scanner.nextLine().trim();
        if (!inputAct.isEmpty()) {
            try {
                int newAct = Integer.parseInt(inputAct);
                if (newAct >= 1 && newAct <= 4) {
                    activityCode = newAct;
                } else {
                    System.out.println("数值非法，沿用原有等级");
                }
            } catch (Exception e) {
                System.out.println("输入非法，沿用原有等级");
            }
        }

        NutritionCalculator.Mode mode;
        System.out.println("\n请选择饮食模式：");
        System.out.println("1 减脂 LOSE_FAT");
        System.out.println("2 维持 MAINTAIN");
        System.out.println("3 增肌 GAIN_MUSCLE");
        System.out.print("输入序号：");
        String opt = scanner.nextLine().trim();
        mode = switch (opt) {
            case "1" -> NutritionCalculator.Mode.LOSE_FAT;
            case "2" -> NutritionCalculator.Mode.MAINTAIN;
            case "3" -> NutritionCalculator.Mode.GAIN_MUSCLE;
            default -> {
                System.out.println("选择错误，自动使用维持模式");
                yield NutritionCalculator.Mode.MAINTAIN;
            }
        };

        double todayTrainBurn = 0;
        if (todayTrain != null) {
            todayTrainBurn = todayTrain.getStrengthBurn() + todayTrain.getCardioBurn();
        }

        NutritionCalculator.NutritionResult result
                = NutritionCalculator.calculateFullNutrition(user, mode, activityCode, todayTrainBurn);

        System.out.println("\n================ 饮食计算说明 ================");
        if (todayTrainBurn > 0) {
            String burnTip = "已自动计入今日训练额外消耗：" + Math.round(todayTrainBurn) + " kcal";
            System.out.println(burnTip);
        } else {
            System.out.println("今日暂无训练记录，按休息日标准计算");
        }
        System.out.println("==============================================");

        System.out.println(result);

        double totalCal = result.targetCal;
        double totalPro = result.proteinG;
        double totalFat = result.fatG;
        double totalCarb = result.carbG;

        System.out.println("\n================ 三餐营养分配（3:4:3） ================");

        String breakfastLine = "早餐 | 热量:" + Math.round(totalCal * breakfastRatio)
                + "kcal  蛋白质:" + Math.round(totalPro * breakfastRatio)
                + "g  碳水:" + Math.round(totalCarb * breakfastRatio)
                + "g  脂肪:" + Math.round(totalFat * breakfastRatio) + "g";

        String lunchLine = "午餐 | 热量:" + Math.round(totalCal * lunchRatio)
                + "kcal  蛋白质:" + Math.round(totalPro * lunchRatio)
                + "g  碳水:" + Math.round(totalCarb * lunchRatio)
                + "g  脂肪:" + Math.round(totalFat * lunchRatio) + "g";

        String dinnerLine = "晚餐 | 热量:" + Math.round(totalCal * dinnerRatio)
                + "kcal  蛋白质:" + Math.round(totalPro * dinnerRatio)
                + "g  碳水:" + Math.round(totalCarb * dinnerRatio)
                + "g  脂肪:" + Math.round(totalFat * dinnerRatio) + "g";

        System.out.println(breakfastLine);
        System.out.println(lunchLine);
        System.out.println(dinnerLine);
        System.out.println("======================================================");
    }

    private static void showTodayTrainingDetail() {
	    System.out.println("");
	    System.out.println("========今日训练完整明细========");
	    System.out.println("【力量训练记录】");
	    ArrayList<WorkoutLog> strengthList = todayTrain.getWorkoutLogs();
	    if (strengthList.isEmpty()) {
	        System.out.println("暂无力量训练记录");
	    } else {
	        for (WorkoutLog log : strengthList) {
	            System.out.println(log.toString());
	        }
	    }

	    System.out.println("");
	    System.out.println("【有氧训练记录】");
	    ArrayList<CardioRecord> cardioList = todayTrain.getCardioList();
	    if (cardioList.isEmpty()) {
	        System.out.println("暂无有氧训练记录");
	    } else {
	        for (CardioRecord cardio : cardioList) {
	            String cardioInfo = "有氧项目：" + cardio.getCardioName() + " 时长：" + cardio.getMinute()
	                    + "分钟 MET系数：" + cardio.getMetValue();
	            System.out.println(cardioInfo);
	        }
	    }
	    System.out.println("==============================");
	}

    private static void createTrainingPlan() {
        checkBaseReady();
        System.out.println("\n训练计划模式：");
        System.out.println("1 全身训练");
        System.out.println("2 双部位分化训练");
        System.out.println("3 单肌群专攻");
        System.out.println("4 推拉腿三分化（PPL）");
        int mode = Integer.parseInt(scanner.nextLine());
        ArrayList<Exercise> plan = new ArrayList<>();
        List<ArrayList<Exercise>> pplList;

        switch (mode) {
            case 1:
                plan = workoutLibrary.generateFullBodyPlan();
                break;
            case 2:
                System.out.print("部位1：");
                String g1 = scanner.nextLine();
                System.out.print("部位2：");
                String g2 = scanner.nextLine();
                plan = workoutLibrary.generateTwoTargetPlan(g1, g2);
                break;
            case 3:
                System.out.print("目标肌群：");
                String g = scanner.nextLine();
                plan = workoutLibrary.getSingleTargetExercises(g);
                break;
            case 4:
                pplList = workoutLibrary.generatePPLThreeSplit();
                String[] dayName = {"【推-Push日】胸肩三头", "【拉-Pull日】背二头", "【腿-Leg日】腿臀核心"};
                for (int i = 0; i < 3; i++) {
                    System.out.println("\n==== " + dayName[i] + " ====");
                    ArrayList<Exercise> dayPlan = pplList.get(i);
                    for (Exercise ex : dayPlan) {
                        String info = ex.getName() + " | " + ex.getSets() + "组×" + ex.getReps() + "次";
                        System.out.println(info);
                    }
                }
                System.out.print("选择哪一天的计划导入今日训练？(1推 / 2拉 / 3腿)：");
                int pick = Integer.parseInt(scanner.nextLine());
                if (pick >= 1 && pick <= 3) {
                    plan = pplList.get(pick - 1);
                } else {
                    System.out.println("选择错误");
                    return;
                }
                break;
            default:
                System.out.println("模式错误");
                return;
        }

        if (plan.isEmpty()) {
            System.out.println("暂无对应动作");
            return;
        }
        System.out.println("\n===生成训练计划===");
        for (Exercise e : plan) {
            String line = "【" + e.getTrainGroup() + "】" + e.getName() + " | 推荐：" + e.getSets() + "组×" + e.getReps() + "次";
            System.out.println(line);
        }

        System.out.print("将计划导入今日训练？(y/n)：");
        String importPlan = scanner.nextLine();
        if ("y".equalsIgnoreCase(importPlan)) {
            for (Exercise ex : plan) {
                todayTrain.addWorkoutLog(new WorkoutLog(ex));
            }
            System.out.println("计划已导入，进入5号功能补充重量组数即可");
        }

        System.out.print("训练后搭配有氧？(y/n)：");
        String addCardio = scanner.nextLine();
        if ("y".equalsIgnoreCase(addCardio)) {
            workoutLibrary.printAllCardioPlan();
            System.out.print("选择有氧名称：");
            String cName = scanner.nextLine().trim();
            WorkoutLibrary.CardioTemplate ct = workoutLibrary.getCardioByName(cName);
            if (ct != null) {
                todayTrain.addCardio(new CardioRecord(ct.name, ct.met, ct.recommendMin));
                System.out.println("有氧已追加到今日训练");
            }
            else {
                System.out.println("不存在该有氧项目，请核对名称！");
            }
        }
    }

    private static void filterMuscleMainExercise() {
        System.out.print("输入目标肌肉名称：");
        String muscle = scanner.nextLine();
        System.out.print("最低发力占比阈值(数字)：");
        double limit = Double.parseDouble(scanner.nextLine());
        ArrayList<Exercise> list = workoutLibrary.findMainExerciseByMuscle(muscle, limit);
        System.out.println("\n主打该肌肉的动作：");
        if (list.isEmpty()) {
            System.out.println("无匹配动作");
            return;
        }
        for (Exercise ex : list) {
            System.out.println(ex.getName() + " — " + ex.getTrainGroup());
        }
    }

    private static void checkBaseReady() {
        if (user == null) {
            throw new RuntimeException("请先创建个人档案");
        }
        if (todayTrain == null) {
            throw new RuntimeException("请先创建今日训练记录");
        }
    }
    private static void debugBurnDetail() {
        if (todayTrain == null) {
            System.out.println("暂无今日训练记录");
            return;
        }
        if (user == null) {
            System.out.println("请先建立个人档案");
            return;
        }

        System.out.println("\n====热量调试明细====");
        double allStrengthCal = todayTrain.getStrengthBurn();

        for (WorkoutLog log : todayTrain.getWorkoutLogs()) {
            double singleLogBurn = log.getTotalBurn(todayTrain.getUserWeight());

            String info1 = "动作：" + log.getExercise().getName();
            String info2 = "，该动作总消耗：" + (int)(singleLogBurn + 0.5) + " kcal";
            System.out.println(info1 + info2);

            for (WorkoutLog.SetRecord item : log.getSetRecords()) {
                String groupInfo = "    单组用时：" + item.getMinute() + " min";
                System.out.println(groupInfo);
            }
        }
        String totalStr = "当日力量训练合计消耗：" + (int)(allStrengthCal + 0.5) + " kcal";
        System.out.println(totalStr);

        double cardioTotal = todayTrain.getCardioBurn();
        String cardioStr = "当日有氧合计消耗：" + (int)(cardioTotal + 0.5) + " kcal";
        System.out.println(cardioStr);
        System.out.println("====================\n");
    }
    
    private static LocalDate findLastTrainDateByGroup(String targetGroup) {
        LocalDate latestDate = null;
        for (DailyTraining training : historyTrainList) {
            for (WorkoutLog log : training.getWorkoutLogs()) {
                Exercise ex = log.getExercise();
                if (targetGroup.equals(ex.getTrainGroup())) {
                    LocalDate trainDate = training.getDate();
                    if (latestDate == null || trainDate.isAfter(latestDate)) {
                        latestDate = trainDate;
                    }
                    break;
                }
            }
        }
        return latestDate;
    }
    private static void checkMuscleRecoverWarn(String trainGroupName) {
        MuscleRecoverRule rule = MuscleRecoverRule.findByGroupName(trainGroupName);
        if (rule == null) {
            return;
        }
        LocalDate lastDate = findLastTrainDateByGroup(trainGroupName);
        if (lastDate == null) { 
            return;
        }
        LocalDate today = LocalDate.now();
        if (rule.needWarn(lastDate, today)) {
            long gapDay = java.time.temporal.ChronoUnit.DAYS.between(lastDate, today);
            String warnText = "肌群恢复提醒：上次训练【" + trainGroupName + "】在"
                    + lastDate + "，间隔仅" + gapDay + "天，建议最少间隔" + rule.recoverDays + "天，注意疲劳累积！";
            System.out.println(warnText);
        }
    }
}
