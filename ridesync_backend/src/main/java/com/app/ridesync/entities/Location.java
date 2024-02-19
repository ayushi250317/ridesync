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
	private Integer locationId;
	private double lattitude;
	private double longitude;
	private String landmark;
	private String address;
	
	public Location(double d, double e, String landmark, String address) {
		super();
		this.lattitude = d;
		this.longitude = e;
		this.landmark = landmark;
		this.address = address;
	}	 
}
