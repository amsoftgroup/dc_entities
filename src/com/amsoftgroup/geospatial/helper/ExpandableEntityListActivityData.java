package com.amsoftgroup.geospatial.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.amsoftgroup.geospatial.dc.business.Entity;

/*
 this class represents the data ExpandableEntityListActivity must save on orientation change.
 the objective is to avoid a round trip http call to the server after every phone tilt!

 REF: http://developer.android.com/resources/articles/faster-screen-orientation-change.html
*/
public class ExpandableEntityListActivityData {
	private ArrayList<Entity> m_parentList;
	private LinkedHashMap m_child;
	
	public ArrayList<Entity> getM_parentList() {
		return m_parentList;
	}

	public void setM_parentList(ArrayList<Entity> m_parentList) {
		this.m_parentList = m_parentList;
	}

	public LinkedHashMap getM_child() {
		return m_child;
	}

	public void setM_child(LinkedHashMap m_child) {
		this.m_child = m_child;
	}

}
