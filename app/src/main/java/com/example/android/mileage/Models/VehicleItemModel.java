package com.example.android.mileage.Models;

import java.io.Serializable;

/**
 * Created by Sunil Kumar on 8/2/2016.
 */
public class VehicleItemModel implements Serializable
{
    private String vehicleId;
    public String getVehicleId() { return this.vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    private String vehicleName;
    public String getVehicleName() { return this.vehicleName; }
    public void setVehicleName(String vehicleName) { this.vehicleName = vehicleName; }

    private String vehicleType;
    public String getVehicleType() { return this.vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
}
