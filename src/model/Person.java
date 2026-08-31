package model;
import java.io.Serializable;
import java.util.ArrayList;
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private String gender; //male&female
	private double height; //centimeter
	private double weight; //kilogram
	private int age;
	private double BMR;
	private int activityCode;
	private ArrayList<WeightRecord> weightRecordList = new ArrayList<>();
	private Person(String gender, double height, double weight, int age) {
		if (gender == null) {
	        throw new IllegalArgumentException("性别不能为空");
	    }
		if(height <= 0) {
			throw new IllegalArgumentException("身高不能为负数");
			
		}
		if(weight <= 0) {
			throw new IllegalArgumentException("体重不能为负数");
			
		}
		if(age <= 0) {
			throw new IllegalArgumentException("年龄不能为负数");
			
		}
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.age = age;
		this.BMR = 0.0;
		calculateBMR();
		
	}
	public static Person createPerson(String gender, double height, double weight, int age) {
		String inputGender = gender.trim().toLowerCase();
		if (!inputGender.equals("male") && !inputGender.equals("female")) {
		        throw new IllegalArgumentException("性别非法，仅支持 male / female");
		    }
		
			return new Person(inputGender, height, weight, age);
	}
	public String getGender() {
		return gender;
		
	}
	public double getWeight() {
		return weight;
		
	}
	public double getHeight() {
		return height;
		
	}
	public int getAge() {
		return age;
		
	}
	public double getBMR() {
		return BMR;
		
	}
	public int getActivityCode() {
	    return activityCode;
	}
	public void addWeightRecord(WeightRecord record) {
	    weightRecordList.add(record);
	}
	public ArrayList<WeightRecord> getWeightRecordList() {
	    return weightRecordList;
	}
	public void setWeight(double newWeight) {
		this.weight = newWeight;
		calculateBMR();
		
	}
	public void setActivityCode(int activityCode) {
	    this.activityCode = activityCode;
	}
	public void calculateBMR() {
		if(gender.equals("male")) {
			BMR = 10*weight + 6.25*height - 5*age + 5;
			
		}
		else {
			BMR = 10*weight + 6.25*height - 5*age - 161;
			
		}
		
	}
	 public double getBMI() {
	        if (height <= 0) {
	            return 0.0;
	        }
	        double heightM = height / 100.0;
	        return weight / (heightM * heightM);
	 }
	 public String getBMILevel() {
	        double bmi = getBMI();
	        if (bmi <= 0) return "暂无身体数据";
	        if (bmi < 18.5) return "偏瘦，建议力量训练+适度热量盈余增肌";
	        else if (bmi <= 23.9) return "身材标准健康，维持当前作息即可";
	        else if (bmi <= 27.9) return "超重，可配合力量训练+温和热量缺口减脂";
	        else return "肥胖，建议有氧搭配力量训练逐步减脂";
	 }
	public double getTDEE(int activityCode) {
		 double factor;
	        if (activityCode == 1) {
	            factor = 1.2;
	        } else if (activityCode == 2) {
	            factor = 1.375;
	        } else if (activityCode == 3) {
	            factor = 1.55;
	        } else { 
	            factor = 1.725;
	        }
	        return BMR * factor;
		
	}
	public double getPreciseTDEE(int activityCode, double todayTrainBurnCal) {
	    return getTDEE(activityCode) + todayTrainBurnCal;
	}
	
}
