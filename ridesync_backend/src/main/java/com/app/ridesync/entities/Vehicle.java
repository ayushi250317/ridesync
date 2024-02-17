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
@Table(name = "vehicle")
public class Vehicle{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long vehicleId;
	private String regNo;
	private long documentId; //Insurance Document. must link to the document table.
	private String model;
	private String make;
	private String type;
	private String userId;
	
	public Vehicle(String regNo, long documentId, String model, String make, String type, String userId) {
		this.regNo = regNo;
		this.documentId = documentId;
		this.model = model;
		this.make = make;
		this.type = type;
		this.userId = userId;
	}
	
}
