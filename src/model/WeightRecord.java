package model;
import java.io.Serializable;
import java.time.LocalDate;

public class WeightRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDate recordDate;
    private double weight;

    public WeightRecord(LocalDate recordDate, double weight) {
        this.recordDate = recordDate;
        this.weight = weight;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }
    public double getWeight() {
        return weight;
    }
}