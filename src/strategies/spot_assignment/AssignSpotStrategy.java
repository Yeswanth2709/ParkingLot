package strategies.spot_assignment;

import exceptions.NoSpotAvailableException;
import models.ParkingLot;
import models.Spot;
import models.VehicleType;

public interface AssignSpotStrategy {
    Spot assignSpot(VehicleType vehicleType, ParkingLot parkingLot) throws NoSpotAvailableException;
}
