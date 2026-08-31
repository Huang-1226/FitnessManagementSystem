package model;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
public enum MuscleRecoverRule {
	CHEST("胸部", 2),
    BACK("背部", 3),
    LEG("腿部", 3);
	public final String groupName;
    public final int recoverDays;

    MuscleRecoverRule(String groupName, int recoverDays) {
        this.groupName = groupName;
        this.recoverDays = recoverDays;
    }
    public static MuscleRecoverRule findByGroupName(String trainGroup) {
        for (MuscleRecoverRule rule : values()) {
            if (rule.groupName.equals(trainGroup)) {
                return rule;
            }
        }
        return null;
    }  
    public boolean needWarn(LocalDate lastTrainDate, LocalDate today) {
        long daysGap = ChronoUnit.DAYS.between(lastTrainDate, today);
        return daysGap < recoverDays;
    }
}
