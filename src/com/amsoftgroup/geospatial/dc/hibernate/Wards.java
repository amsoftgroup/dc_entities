package com.amsoftgroup.geospatial.dc.hibernate;


// Generated Jan 8, 2012 5:37:14 PM by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Wards generated by hbm2java
 */
public class Wards implements java.io.Serializable {

	private int gid;
	private String name;
	private String gisId;
	private String webUrl;
	private String ward;
	private String repName;
	private String repPhone;
	private String repEmail;
	private String repOffice;
	private BigDecimal shapeArea;
	private BigDecimal shapeLen;
	private Serializable theGeom;

	public Wards() {
	}

	public Wards(int gid) {
		this.gid = gid;
	}

	public Wards(int gid, String name, String gisId, String webUrl,
			String ward, String repName, String repPhone, String repEmail,
			String repOffice, BigDecimal shapeArea, BigDecimal shapeLen,
			Serializable theGeom) {
		this.gid = gid;
		this.name = name;
		this.gisId = gisId;
		this.webUrl = webUrl;
		this.ward = ward;
		this.repName = repName;
		this.repPhone = repPhone;
		this.repEmail = repEmail;
		this.repOffice = repOffice;
		this.shapeArea = shapeArea;
		this.shapeLen = shapeLen;
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

	public String getGisId() {
		return this.gisId;
	}

	public void setGisId(String gisId) {
		this.gisId = gisId;
	}

	public String getWebUrl() {
		return this.webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getWard() {
		return this.ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getRepName() {
		return this.repName;
	}

	public void setRepName(String repName) {
		this.repName = repName;
	}

	public String getRepPhone() {
		return this.repPhone;
	}

	public void setRepPhone(String repPhone) {
		this.repPhone = repPhone;
	}

	public String getRepEmail() {
		return this.repEmail;
	}

	public void setRepEmail(String repEmail) {
		this.repEmail = repEmail;
	}

	public String getRepOffice() {
		return this.repOffice;
	}

	public void setRepOffice(String repOffice) {
		this.repOffice = repOffice;
	}

	public BigDecimal getShapeArea() {
		return this.shapeArea;
	}

	public void setShapeArea(BigDecimal shapeArea) {
		this.shapeArea = shapeArea;
	}

	public BigDecimal getShapeLen() {
		return this.shapeLen;
	}

	public void setShapeLen(BigDecimal shapeLen) {
		this.shapeLen = shapeLen;
	}

	public Serializable getTheGeom() {
		return this.theGeom;
	}

	public void setTheGeom(Serializable theGeom) {
		this.theGeom = theGeom;
	}

}