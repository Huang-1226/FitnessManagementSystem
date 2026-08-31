package model;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.Serializable;
public class DailyTraining implements Serializable{
	private static final long serialVersionUID = 1L;
	private LocalDate date;
	private ArrayList<WorkoutLog> workoutLogs;
	private ArrayList<CardioRecord> cardioList = new ArrayList<>();
	private String remark;
	private Person person;
	public DailyTraining(Person person) {
        this.person = person;
        this.date = LocalDate.now();
        workoutLogs = new ArrayList<>();
        cardioList = new ArrayList<>();
    }
	public void addWorkoutLog(WorkoutLog log) {
        if(log != null){
            workoutLogs.add(log);
        }
    }
	public void addCardio(CardioRecord cardio) {
        if (cardio != null) {
            cardioList.add(cardio);
        }
    }
	
	public double getUserWeight() {
        if (person == null) {
            return 0;
        }
        return person.getWeight();
    }
	
	 public void setTrainDate(LocalDate trainDate) {
	        this.date = trainDate;
	    }
	 
	 public double calculateWholeBurn(double userWeight) {
		    double strengthBurn = 0;
		    double cardioBurn = 0;
		    for (WorkoutLog log : workoutLogs) {
		        strengthBurn += log.getTotalBurn(userWeight);
		    }
		    for (CardioRecord cardio : cardioList) {
		        cardioBurn += cardio.calcBurn(userWeight);
		    }
		    return strengthBurn + cardioBurn;
		}
	public double getDailyTotalVolume() {
        double volume = 0;
        for (WorkoutLog log : workoutLogs) {
            volume += log.getTrainVolume();
        }
        return volume;
    }
	 public double getStrengthBurn() {
	        double sum = 0;
	        double weight = getUserWeight();
	        for (WorkoutLog log : workoutLogs) {
	            sum += log.getTotalBurn(weight);
	        }
	        return sum;
	    }
	 public double getCardioBurn() {
	        double total = 0.0;
	        double weight = getUserWeight();
	        for (CardioRecord record : cardioList) {
	            total += record.calcBurn(weight);
	        }
	        return total;
	    }
	 public LocalDate getDate() {
	        return date;
	 }

	 public String getRemark() {
	        return remark;
	 }

	 public void setRemark(String remark) {
	        this.remark = remark;
	 }
	 
	 public Person getPerson() {
	        return person;
	    }

	 public void setPerson(Person person) {
	        this.person = person;
	    }

	 public ArrayList<WorkoutLog> getWorkoutLogs() {
	        return workoutLogs;
	 }
	 public ArrayList<CardioRecord> getCardioList() {
		    return cardioList;
		}
	
	 public String showAllDetail() {
		    String content = "====今日训练详情====\n";
		    content = content + "训练日期：" + this.getDate() + "\n";
		    if(remark != null && !remark.isBlank()){
		        content = content + "训练备注：" + remark + "\n";
		    }

		    content = content + "【力量训练】\n";
		    for (WorkoutLog log : workoutLogs) {
		        content = content + log.toString() + "\n";
		    }
		    content += "今日总训练容量：";
		    content += getDailyTotalVolume();

		    content = content + "【有氧训练】\n";
		    for (CardioRecord cardio : cardioList) {
		        String line = "有氧：" + cardio.getCardioName()
		                + "，时长：" + cardio.getMinute() + "分钟"
		                + "，预估消耗：" + (int)(cardio.calcBurn(person.getWeight()) + 0.5) + "kcal";
		        content = content + line + "\n";
		    }
		    double totalBurn = getStrengthBurn() + getCardioBurn();
		    content += "今日训练额外消耗：";
		    content += totalBurn;
		    return content;
		}
}
