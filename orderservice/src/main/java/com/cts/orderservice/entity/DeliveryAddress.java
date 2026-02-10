package com.cts.orderservice.entity;

import jakarta.persistence.Embeddable;

@Embeddable

public class DeliveryAddress {

    private String houseNo;
    private String streetName;

    private String town;
    private String district;
    private String state;
    private String pincode;
    private String userMobileNumber;

    // No-args constructor
    public DeliveryAddress() {
    }

    // All-args constructor
    public DeliveryAddress(String houseNo,
                           String streetName,
                           String town,
                           String district,
                           String state,
                           String pincode,
                           String userMobileNumber) {
        this.houseNo = houseNo;
        this.streetName = streetName;
        this.town = town;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
        this.userMobileNumber=userMobileNumber;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
    public String getState() {
            return state;
    }

    public void setState(String state) {
            this.state = state;
    }

    public String getPincode() {
            return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }
}


