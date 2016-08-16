package com.fuel.mileage.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sunil Kumar on 7/22/2016.
 */
public class MileageItemModel implements Serializable {
    private int mileageId;
    public int getMileageId() { return this.mileageId; }
    public void setMileageId(int mileageId) { this.mileageId = mileageId; }

    private Date fromDate;
    public Date getFromDate() { return this.fromDate; }
    public void setFromDate(Date fromDate) { this.fromDate = fromDate; }

    private Date toDate;
    public Date getToDate() { return this.toDate; }
    public void setToDate(Date toDate) { this.toDate = toDate; }

    private float mileage;
    public float getMileage() { return this.mileage; }
    public void setMileage(float mileage) { this.mileage = mileage; }

    private float petrolFilled;
    public float getPetrolFilled() { return this.petrolFilled; }
    public void setPetrolFilled(float petrolFilled) { this.petrolFilled = petrolFilled; }

    private float amountOfPetrolFilled;
    public float getAmountOfPetrolFilled() { return this.amountOfPetrolFilled; }
    public void setAmountOfPetrolFilled(float amountOfPetrolFilled) { this.amountOfPetrolFilled = amountOfPetrolFilled; }

    private float distanceTravelled;
    public float getDistanceTravelled() { return this.distanceTravelled; }
    public void setDistanceTravelled(float distanceTravelled) { this.distanceTravelled = distanceTravelled; }

    private String petroleumBrand;
    public String getPetroleumBrand() { return this.petroleumBrand; }
    public void setPetroleumBrand(String petroleumBrand) { this.petroleumBrand = petroleumBrand; }

    private float lastmeterReading;
    public float getLastmeterReading() { return this.lastmeterReading; }
    public void setLastmeterReading(float lastmeterReading) { this.lastmeterReading = lastmeterReading; }

    private float currentmeterReading;
    public float getCurrentmeterReading() { return this.currentmeterReading; }
    public void setCurrentmeterReading(float currentmeterReading) { this.currentmeterReading = currentmeterReading; }

    private float petrolPrice;
    public float getPetrolPrice() { return this.petrolPrice; }
    public void setPetrolPrice(float petrolPrice) { this.petrolPrice = petrolPrice; }

    private int isMileageChecked;
    public int getIsMileageChecked() { return this.isMileageChecked; }
    public void setIsMileageChecked(int isMileageChecked) { this.isMileageChecked = isMileageChecked; }

    private String vehicleId;
    public String getVehicleId() { return this.vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    private String vehicleType;
    public String getVehicleType() { return this.vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    private String vehicleName;
    public String getVehicleName() { return this.vehicleName; }
    public void setVehicleName(String vehicleName) { this.vehicleName = vehicleName; }
}
