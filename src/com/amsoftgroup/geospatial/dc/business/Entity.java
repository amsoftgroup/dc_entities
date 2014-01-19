package com.amsoftgroup.geospatial.dc.business;

public class Entity {

	double lat;

	double lon;
	double distance;
	int entityTypeId;
	int gid;
	String description;
	
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public int getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(int embassy) {
		this.entityTypeId = embassy;
	}	
}
