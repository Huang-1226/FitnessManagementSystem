package util;
import model.Person;
public class NutritionCalculator {
	private NutritionCalculator() {}
	public enum Mode{
		LOSE_FAT, MAINTAIN, GAIN_MUSCLE
		
	}
	public static double getTargetCalories(Person person, Mode mode, int activityCode, double trainTotalBurn) {
		if(activityCode > 4 || activityCode < 1) {
			throw new IllegalArgumentException("活动系数只能填入1~4");
			
		}
		return switch (mode) {
        case LOSE_FAT -> person.getPreciseTDEE(activityCode,trainTotalBurn) - 450;
        case GAIN_MUSCLE -> person.getPreciseTDEE(activityCode,trainTotalBurn) + 250;
        case MAINTAIN -> person.getPreciseTDEE(activityCode,trainTotalBurn);
        };
		
	}
	public static double getTargetCalories(Person person, Mode mode, int activityCode) {
	        return getTargetCalories(person, mode, activityCode, 0);
	    }
	public static class NutritionResult {
	        public final double targetCal;
	        public final double proteinG;
	        public final double fatG;
	        public final double carbG;

	        public NutritionResult(double targetCal, double proteinG, double fatG, double carbG) {
	            this.targetCal = targetCal;
	            this.proteinG = proteinG;
	            this.fatG = fatG;
	            this.carbG = carbG;
	        }

	        @Override
	        public String toString() {
	        	 String targetCalStr = "" + Math.round(targetCal);
	        	    
	        	    long proteinInt = Math.round(proteinG * 10);
	        	    double proteinShow = proteinInt / 10.0;
	        	    String proteinStr = "" + proteinShow;

	        	    long fatInt = Math.round(fatG * 10);
	        	    double fatShow = fatInt / 10.0;
	        	    String fatStr = "" + fatShow;

	        	    long carbInt = Math.round(carbG * 10);
	        	    double carbShow = carbInt / 10.0;
	        	    String carbStr = "" + carbShow;

	        	    String line1 = "===每日饮食方案===";
	        	    String line2 = "目标摄入热量：" + targetCalStr + " kcal";
	        	    String line3 = "蛋白质：" + proteinStr + " g";
	        	    String line4 = "脂肪：" + fatStr + " g";
	        	    String line5 = "碳水：" + carbStr + " g";

	        	    return line1 + "\n" + line2 + "\n" + line3 + "\n" + line4 + "\n" + line5;
	        }
	    }
	public static NutritionResult calculateFullNutrition(Person person, Mode mode, int activityCode, double trainTotalBurn) {
		 if (person == null) {
		        throw new IllegalArgumentException("Person 对象不能为 null");
		    }
		 double targetCal = getTargetCalories(person, mode, activityCode,trainTotalBurn);
		 double proteinFactor = switch (mode) {
         case LOSE_FAT -> 2.2;
         case GAIN_MUSCLE -> 1.8;
         case MAINTAIN -> 1.6;
		 };
		 double tde = person.getPreciseTDEE(activityCode, trainTotalBurn);
		 if (mode == Mode.LOSE_FAT && (tde - targetCal) > 500) {
		     System.out.println(" 警告：热量缺口超过500大卡，力量训练期间容易流失肌肉，建议缩小减脂缺口");
		 }
		 double protein = person.getWeight()*proteinFactor;
		 double proteinCal = protein*4;
		 double fatCal = targetCal*0.25;
		 double fat = fatCal/9;
		 double carbCal = targetCal - proteinCal - fatCal;
		 double carb = carbCal/4;
		 return new NutritionResult(targetCal, protein, fat, carb);
	 }
	public static NutritionResult calculateFullNutrition(Person person, Mode mode, int activityCode) {
	        return calculateFullNutrition(person, mode, activityCode, 0);
	    }
}
