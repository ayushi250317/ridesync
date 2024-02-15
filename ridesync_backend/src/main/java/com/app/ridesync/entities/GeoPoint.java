package com.app.ridesync.entities;

import com.app.ridesync.converter.GeoPointConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class GeoPoint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Convert(converter = GeoPointConverter.class)	
	@Lob
	@Column(length = (int)2e24-1)
	private GeoPointRecord geoPointRecord;			// a length of 2^24 is treated as mediumBlob in mysql and can store ~16MB of data.
	
	@OneToOne
	@JoinColumn(name = "ride_id")
	//private Ride rideId;				TODO: changes will be made once the post a ride functionality is pushed.

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
