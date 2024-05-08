package strategies.spot_assignment;

import exceptions.NoSpotAvailableException;
import models.*;

public class NearestSpotAssignmentStrategy implements AssignSpotStrategy{
    @Override
    public Spot assignSpot(VehicleType vehicleType, ParkingLot parkingLot) throws NoSpotAvailableException {
        for (Floor floor : parkingLot.getFloors()) {
            if(floor.getFloorStatus().equals(FloorStatus.OPERATIONAL)) {
                for (Section section : floor.getSections()) {
                    for (Spot spot : section.getSpots()) {
                        if(spot.getVehicleType().equals(vehicleType) && spot.getSpotStatus().equals(SpotStatus.UNOCCUPIED)){
                            spot.setSpotStatus(SpotStatus.OCCUPIED);
                            return spot;
                        }
                    }
                }
            }
        }
        throw new NoSpotAvailableException("No spot Available for this "+ vehicleType);
    }
}
