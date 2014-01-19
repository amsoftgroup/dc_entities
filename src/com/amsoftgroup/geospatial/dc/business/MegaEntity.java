package com.amsoftgroup.geospatial.dc.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.amsoftgroup.geospatial.dc.hibernate.*;

public class MegaEntity{
	
	private int gid;
	private int entityType;
	
	private ArrayList<Anc> anc;
	private ArrayList<Barsandliquor> barsandliquor;
	private ArrayList<Bicyclelanes> bicyclelanes;
	private ArrayList<Embassy> embassy;
	private ArrayList<Gasstations> gasstations;
	private ArrayList<Grocerystores> grocerystores;
	private ArrayList<Hikingtrails> hikingtrails;
	private ArrayList<Historicdistricts> historicdistricts;
	private ArrayList<Historicstructures> historicstructures;
	private ArrayList<Hospitals> hospitals;
	private ArrayList<Hotels> hotels;
	private ArrayList<Libraries> libraries;
	private ArrayList<Metrostations> metrostations;
	private ArrayList<Nationalparks> nationalparks;
	private ArrayList<Neighborhoodcomposition> neighborhoodcomposition;
	private ArrayList<Placesofworship> placesofworship;
	private ArrayList<Policestations> policestations;
	private ArrayList<Postoffice> postoffice;
	private ArrayList<Tenniscourts> tenniscourts;
	private ArrayList<Wards> wards;
	
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}
	public int getEntityType() {
		return entityType;
	}
	public ArrayList<Anc> getAnc() {
		return anc;
	}
	public void setAnc(ArrayList<Anc> anc) {
		this.anc = anc;
	}
	public ArrayList<Barsandliquor> getBarsandliquor() {
		return barsandliquor;
	}
	public void setBarsandliquor(ArrayList<Barsandliquor> barsandliquor) {
		this.barsandliquor = barsandliquor;
	}
	public ArrayList<Bicyclelanes> getBicyclelanes() {
		return bicyclelanes;
	}
	public void setBicyclelanes(ArrayList<Bicyclelanes> bicyclelanes) {
		this.bicyclelanes = bicyclelanes;
	}
	public ArrayList<Embassy> getEmbassy() {
		return embassy;
	}
	public void setEmbassy(ArrayList<Embassy> embassy) {
		this.embassy = embassy;
	}
	public ArrayList<Gasstations> getGasstations() {
		return gasstations;
	}
	public void setGasstations(ArrayList<Gasstations> gasstations) {
		this.gasstations = gasstations;
	}
	public ArrayList<Grocerystores> getGrocerystores() {
		return grocerystores;
	}
	public void setGrocerystores(ArrayList<Grocerystores> grocerystores) {
		this.grocerystores = grocerystores;
	}
	public ArrayList<Hikingtrails> getHikingtrails() {
		return hikingtrails;
	}
	public void setHikingtrails(ArrayList<Hikingtrails> hikingtrails) {
		this.hikingtrails = hikingtrails;
	}
	public ArrayList<Historicdistricts> getHistoricdistricts() {
		return historicdistricts;
	}
	public void setHistoricdistricts(ArrayList<Historicdistricts> historicdistricts) {
		this.historicdistricts = historicdistricts;
	}
	public ArrayList<Historicstructures> getHistoricstructures() {
		return historicstructures;
	}
	public void setHistoricstructures(
			ArrayList<Historicstructures> historicstructures) {
		this.historicstructures = historicstructures;
	}
	public ArrayList<Hospitals> getHospitals() {
		return hospitals;
	}
	public void setHospitals(ArrayList<Hospitals> hospitals) {
		this.hospitals = hospitals;
	}
	public ArrayList<Hotels> getHotels() {
		return hotels;
	}
	public void setHotels(ArrayList<Hotels> hotels) {
		this.hotels = hotels;
	}
	public ArrayList<Libraries> getLibraries() {
		return libraries;
	}
	public void setLibraries(ArrayList<Libraries> libraries) {
		this.libraries = libraries;
	}
	public ArrayList<Metrostations> getMetrostations() {
		return metrostations;
	}
	public void setMetrostations(ArrayList<Metrostations> metrostations) {
		this.metrostations = metrostations;
	}
	public ArrayList<Nationalparks> getNationalparks() {
		return nationalparks;
	}
	public void setNationalparks(ArrayList<Nationalparks> nationalparks) {
		this.nationalparks = nationalparks;
	}
	public ArrayList<Neighborhoodcomposition> getNeighborhoodcomposition() {
		return neighborhoodcomposition;
	}
	public void setNeighborhoodcomposition(
			ArrayList<Neighborhoodcomposition> neighborhoodcomposition) {
		this.neighborhoodcomposition = neighborhoodcomposition;
	}
	public ArrayList<Placesofworship> getPlacesofworship() {
		return placesofworship;
	}
	public void setPlacesofworship(ArrayList<Placesofworship> placesofworship) {
		this.placesofworship = placesofworship;
	}
	public ArrayList<Policestations> getPolicestations() {
		return policestations;
	}
	public void setPolicestations(ArrayList<Policestations> policestations) {
		this.policestations = policestations;
	}
	public ArrayList<Postoffice> getPostoffice() {
		return postoffice;
	}
	public void setPostoffice(ArrayList<Postoffice> postoffice) {
		this.postoffice = postoffice;
	}
	public ArrayList<Tenniscourts> getTenniscourts() {
		return tenniscourts;
	}
	public void setTenniscourts(ArrayList<Tenniscourts> tenniscourts) {
		this.tenniscourts = tenniscourts;
	}
	public ArrayList<Wards> getWards() {
		return wards;
	}
	public void setWards(ArrayList<Wards> wards) {
		this.wards = wards;
	}
	
	

}
