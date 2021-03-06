package com.amsoftgroup.geospatial.dc.hibernate;


// Generated Jan 8, 2012 5:37:14 PM by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;

/**
 * Postoffice generated by hbm2java
 */
public class Postoffice implements java.io.Serializable {

	private int gid;
	private String name;
	private String address;
	private String webUrl;
	private String ssl;
	private String gisId;
	private Long aid;
	private String type;
	private Serializable theGeom;

	public Postoffice() {
	}

	public Postoffice(int gid) {
		this.gid = gid;
	}

	public Postoffice(int gid, String name, String address, String webUrl,
			String ssl, String gisId, Long aid, String type,
			Serializable theGeom) {
		this.gid = gid;
		this.name = name;
		this.address = address;
		this.webUrl = webUrl;
		this.ssl = ssl;
		this.gisId = gisId;
		this.aid = aid;
		this.type = type;
		this.theGeom = theGeom;
	}

	public int getGid() {
		return this.gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebUrl() {
		return this.webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getSsl() {
		return this.ssl;
	}

	public void setSsl(String ssl) {
		this.ssl = ssl;
	}

	public String getGisId() {
		return this.gisId;
	}

	public void setGisId(String gisId) {
		this.gisId = gisId;
	}

	public Long getAid() {
		return this.aid;
	}

	public void setAid(Long aid) {
		this.aid = aid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Serializable getTheGeom() {
		return this.theGeom;
	}

	public void setTheGeom(Serializable theGeom) {
		this.theGeom = theGeom;
	}

}
