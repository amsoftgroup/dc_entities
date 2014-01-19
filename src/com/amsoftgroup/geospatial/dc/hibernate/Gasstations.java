package com.amsoftgroup.geospatial.dc.hibernate;


// Generated Jan 8, 2012 5:37:14 PM by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Gasstations generated by hbm2java
 */
public class Gasstations implements java.io.Serializable {

	private int gid;
	private String address;
	private String zipCode;
	private Integer fullServi;
	private Long addrid;
	private String name;
	private String phone;
	private String gisId;
	private BigDecimal x;
	private BigDecimal y;
	private Serializable theGeom;

	public Gasstations() {
	}

	public Gasstations(int gid) {
		this.gid = gid;
	}

	public Gasstations(int gid, String address, String zipCode,
			Integer fullServi, Long addrid, String name, String phone,
			String gisId, BigDecimal x, BigDecimal y, Serializable theGeom) {
		this.gid = gid;
		this.address = address;
		this.zipCode = zipCode;
		this.fullServi = fullServi;
		this.addrid = addrid;
		this.name = name;
		this.phone = phone;
		this.gisId = gisId;
		this.x = x;
		this.y = y;
		this.theGeom = theGeom;
	}

	public int getGid() {
		return this.gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Integer getFullServi() {
		return this.fullServi;
	}

	public void setFullServi(Integer fullServi) {
		this.fullServi = fullServi;
	}

	public Long getAddrid() {
		return this.addrid;
	}

	public void setAddrid(Long addrid) {
		this.addrid = addrid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGisId() {
		return this.gisId;
	}

	public void setGisId(String gisId) {
		this.gisId = gisId;
	}

	public BigDecimal getX() {
		return this.x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public BigDecimal getY() {
		return this.y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}

	public Serializable getTheGeom() {
		return this.theGeom;
	}

	public void setTheGeom(Serializable theGeom) {
		this.theGeom = theGeom;
	}

}