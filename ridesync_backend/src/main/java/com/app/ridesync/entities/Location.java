package com.app.ridesync.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location")
public class Location {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long locationId;
	private int lattitude;
	private int longitude;
	private String landmark;
	private String address;
	
	public Location(int lattitude, int longitude, String landmark, String address) {
		super();
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.landmark = landmark;
		this.address = address;
	}
	
	public long getLocationId() {
		return locationId;
	}
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	public int getLattitude() {
		return lattitude;
	}
	public void setLattitude(int lattitude) {
		this.lattitude = lattitude;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
