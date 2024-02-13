package com.app.ridesync.entities;

import com.app.ridesync.converter.GeoPointConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class GeoPoint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Convert(converter = GeoPointConverter.class)	
	private GeoPointRecord geoPointRecord;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public GeoPointRecord getGeoPointRecord() {
		return geoPointRecord;
	}

	public void setGeoPointRecord(GeoPointRecord geoPointRecord) {
		this.geoPointRecord = geoPointRecord;
	}
}