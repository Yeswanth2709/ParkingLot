package strategies.pricing_strategy;

import models.VehicleType;
import utils.DateTimeUtils;

import java.util.Date;

public class WeekdayStrategy implements CalculateFeesStrategy{
    @Override
    public double calculateFees(Date entryTime, Date exitTime, VehicleType vehicleType) {
        return DateTimeUtils.calcHours(entryTime,exitTime)*10;

    }
}
