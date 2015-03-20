package com.medicapital.common.entities;

/**
 * The persistent class for the City database table.
 * 
 */
public class City implements SerializableEntity {

    private int cityId;

    private String name;
    
    private String regionName;
    
    private int regionId;

    public City() {
    }

    public int getId() {
        return this.cityId;
    }

    public void setId(int cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

    public String getRegionName() {
    	return regionName;
    }

	public void setRegionName(String regionName) {
    	this.regionName = regionName;
    }

	public int getRegionId() {
    	return regionId;
    }

	public void setRegionId(int regionId) {
    	this.regionId = regionId;
    }

	@Override
    public String toString() {
        StringBuilder data = new StringBuilder("[");
        data.append("cityId: " + cityId);
        data.append(",city name: " + name);
        data.append(",regionId: " + regionId);
        data.append(",region: " + regionName);
        data.append("]");
        return data.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        City other = (City) obj;
        return cityId == other.cityId;
    }

}