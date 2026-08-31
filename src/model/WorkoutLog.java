package model;
import java.util.ArrayList;
import java.io.Serializable;
public class WorkoutLog implements Serializable  {
	private static final long serialVersionUID = 1L;
	private Exercise exercise;
	private ArrayList<SetRecord> setRecords;
	

	 public static class SetRecord implements Serializable{
	    private static final long serialVersionUID = 1L;
		private double trainWeight;
		private int reps;
		private int minute;
		public SetRecord(double trainWeight, int reps, int minute) {
			this.trainWeight = trainWeight;
			this.reps = reps;
			this.minute= minute;
			
		}
		public double getTrainWeight() {
			return trainWeight;
			
		}
		public int getReps() {
			return reps;
			
		}
		public int getMinute() {
			return minute;
			
		}
	}
	public WorkoutLog(Exercise exercise) {
		if (exercise == null) {
			throw new IllegalArgumentException("训练动作不能为null");
		}
		this.exercise = exercise;
		setRecords = new ArrayList<>();
		
		
	}
	
	public boolean hasSetRecord() {
	    return setRecords != null && setRecords.isEmpty() == false;
	}
	
	public void addSet(double trainWeight, int reps, int minute){
		if (trainWeight <= 0) {
			throw new IllegalArgumentException("训练重量必须大于0");
		}
		if (reps <= 0) {
			throw new IllegalArgumentException("次数必须大于0");
		}
		if (minute <= 0) {
			throw new IllegalArgumentException("训练时长必须大于0");
		}
		
	    setRecords.add(new SetRecord(trainWeight,reps,minute));
	    
	}
	public double getTrainVolume() {
		double total = 0.0;
		for(SetRecord record : setRecords) {
			total += record.getTrainWeight()*record.getReps();
			
			
		}
		return total;
		
	}
	
	public double getTotalBurn(double userWeight) {
		if (exercise == null || setRecords.isEmpty()) {
			return 0.0;
		}
		double total = 0;
		for(int i = 0 ; i < setRecords.size() ; i++) {
			SetRecord sr = setRecords.get(i);
			int time = sr.getMinute();
			total+=exercise.calculateBurnCalorie(userWeight, time);
			
		}
		return total;
		
	}
	public Exercise getExercise() {
		return exercise;
		
	}
	public ArrayList<SetRecord> getSetRecords() {
	    return new ArrayList<>(setRecords);
	}
	@Override
	public String toString() {
		if (exercise == null) return "空训练记录";
		String result = "训练动作:"+exercise.getName();
		for(int i = 0 ; i < setRecords.size() ; i++) {
			String tempResult = " 组"+(i+1)+":"+setRecords.get(i).getTrainWeight()+"kg"+"*"+setRecords.get(i).getReps()+" ";
			result += tempResult;
			
		}
		result += " 总训练容量：" + getTrainVolume();
		return result;
		
	}
	
	
}
