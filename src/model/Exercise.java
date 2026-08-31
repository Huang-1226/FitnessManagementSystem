package model;
import java.util.ArrayList;
import java.io.Serializable;
public class Exercise implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;         // 动作名称
	private String trainGroup;  // 目标肌群 
	private int sets;            // 新手推荐组数
	private int reps;            // 新手推荐次数
	private ArrayList<MuscleRatio> muscleRatios;  //各肌肉名称及发力占比
	private double metValue;
	public Exercise(String name, String trainGroup, int sets, int reps, double metValue) {
		this.name = name;
		this.trainGroup = trainGroup;
		this.sets = sets;
		this.reps = reps;
		this.muscleRatios = new ArrayList<>();
		this.metValue = metValue;
		
	}
	public static class MuscleRatio implements Serializable{
		private static final long serialVersionUID = 1L;
		private String muscleName;
		private double ratio;
		public MuscleRatio(String muscleName, double ratio) {
			this.muscleName = muscleName;
			this.ratio = ratio;
			
		}
		public String getMuscleName() {
			return muscleName;
			
		}
		public double getRatio() {
			return ratio;
			
		}
		
	}
	public String getName() {
		return name;
		
	}
	public String getTrainGroup() {
		return trainGroup;
		
	}
	public int getSets() {
		return sets;
		
	}
	public int getReps() {
		return reps;
		
	}
	public ArrayList<MuscleRatio> getMuscleRatios(){
		return muscleRatios;
		
	}
	public double getMuscleRatio(String muscleName) {
		for(MuscleRatio mr : muscleRatios) {
			if(mr.getMuscleName().equals(muscleName)) {
				return mr.getRatio();
				
			}
			
		}
		return 0;
		
	}
	public double getMetValue() {
		return metValue;
		
	}
	public double calculateBurnCalorie(double weightKg, int trainMinute) {
		double hour = trainMinute/60.0;
		return weightKg*this.metValue*hour;
		
	}
	@Override
	public String toString() {
		StringBuilder muscleInfo = new StringBuilder();
		for(MuscleRatio mr : muscleRatios) {
			 muscleInfo.append(mr.getMuscleName()).append("(").append(mr.getRatio()).append("%) ");
			
		}
		 return "动作：" + name + " | 目标肌群：" + trainGroup + " | 锻炼肌肉及其占比：" + muscleInfo + " | 新手推荐：" + sets + "组 × " + reps + "次";
		
	}
	 @Override
	 public boolean equals(Object o) {
	     if (this == o) return true;
	     if (o == null || getClass() != o.getClass()) return false;
	     Exercise exercise = (Exercise) o;
	     return name.equals(exercise.name);
	 }

	 @Override
	 public int hashCode() {
	     return name.hashCode();
	 }
	
	
}
