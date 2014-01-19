package com.amsoftgroup.geospatial.dc.business;

public class EntityType {
	
	private int EntityId;
	private String EntityDescription;
	
	public void setEntityId(int entityId) {
		EntityId = entityId;
	}
	public int getEntityId() {
		return EntityId;
	}
	public void setEntityDescription(String entityDescription) {
		EntityDescription = entityDescription;
	}
	public String getEntityDescription() {
		return EntityDescription;
	}
}