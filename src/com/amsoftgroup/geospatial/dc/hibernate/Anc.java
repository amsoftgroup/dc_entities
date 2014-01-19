package com.amsoftgroup.geospatial.dc.hibernate;


// Generated Jan 8, 2012 5:37:14 PM by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Anc generated by hbm2java
 */
public class Anc implements java.io.Serializable {

	private int gid;
	private BigDecimal objectid;
	private String gisId;
	private String ancId;
	private String webUrl;
	private String name;
	private Serializable theGeom;

	public Anc() {
	}

	public Anc(int gid) {
		this.gid = gid;
	}

	public Anc(int gid, BigDecimal objectid, String gisId, String ancId,
			String webUrl, String name, Serializable theGeom) {
		this.gid = gid;
		this.objectid = objectid;
		this.gisId = gisId;
		this.ancId = ancId;
		this.webUrl = webUrl;
		this.name = name;
		this.theGeom = theGeom;
	}

	public int getGid() {
		return this.gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public BigDecimal getObjectid() {
		return this.objectid;
	}

	public void setObjectid(BigDecimal objectid) {
		this.objectid = objectid;
	}

	public String getGisId() {
		return this.gisId;
	}

	public void setGisId(String gisId) {
		this.gisId = gisId;
	}

	public String getAncId() {
		return this.ancId;
	}

	public void setAncId(String ancId) {
		this.ancId = ancId;
	}

	public String getWebUrl() {
		return this.webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Serializable getTheGeom() {
		return this.theGeom;
	}

	public void setTheGeom(Serializable theGeom) {
		this.theGeom = theGeom;
	}

}
