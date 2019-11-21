package com.example.campogrande.Model;

public class PropertyFacilityModel {

    private String facilityName;
    private String facilityValue;

    public PropertyFacilityModel(String facilityName, String facilityValue) {
        this.facilityName = facilityName;
        this.facilityValue = facilityValue;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityValue() {
        return facilityValue;
    }

    public void setFacilityValue(String facilityValue) {
        this.facilityValue = facilityValue;
    }
}
