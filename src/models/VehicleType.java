package models;

public enum VehicleType {
    CAR,
    BIKE,
    EV_CAR,
    TRUCK;
    public static VehicleType getTypeFromStr(String vehicleType){
        for (VehicleType value : VehicleType.values()) {
            if(vehicleType.equalsIgnoreCase(value.toString())){
                return value;
            }
        }
        throw new IllegalArgumentException("Unsupported vehicle type");
    }
}
