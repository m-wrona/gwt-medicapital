package com.medicapital.common.entities;

public class Localization implements SerializableEntity {

    private int localizationId;
    private String address;
    private String postalCode;
    private int cityId;

    public int getId() {
        return localizationId;
    }

    public void setId(int localizationId) {
        this.localizationId = localizationId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCityId(int cityId) {
	    this.cityId = cityId;
    }
    
    public int getCityId() {
	    return cityId;
    }

    @Override
    public String toString() {
        StringBuilder data = new StringBuilder("[");
        data.append("id: " + localizationId);
        data.append(", address: " + address);
        data.append(", postalCode: " + postalCode);
        data.append(", cityId: " + cityId);
        data.append("]");
        return data.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        } else if (!(obj instanceof Localization)) {
            return false;
        }
        Localization other = (Localization) obj;
        return localizationId == other.localizationId;
    }
}
