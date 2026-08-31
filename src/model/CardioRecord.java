package model;
import java.io.Serializable;
public class CardioRecord implements Serializable{
	private static final long serialVersionUID = 1L;
	private String cardioName;
    private double metValue;
    private int minute;
    public CardioRecord(String cardioName, double metValue, int minute) {
        this.cardioName = cardioName;
        this.metValue = metValue;
        this.minute = minute;
    }
    public double calcBurn(double weightKg) {
        double hour = minute / 60.0;
        return weightKg * metValue * hour;
    }
    public String getCardioName() {
        return cardioName;
    }
    public int getMinute() {
    	return minute;
    	
    }
    public double getMetValue() {
    	return metValue;
    	
    }
}
