package models;

public class Spot extends BaseModel{
    private String name;
    private SpotStatus spotStatus;
    private VehicleType vehicleType;

    public Spot(String name, SpotStatus spotStatus, VehicleType vehicleType) {
        this.name = name;
        this.spotStatus = spotStatus;
        this.vehicleType = vehicleType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpotStatus getSpotStatus() {
        return spotStatus;
    }

    public void setSpotStatus(SpotStatus spotStatus) {
        this.spotStatus = spotStatus;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}
