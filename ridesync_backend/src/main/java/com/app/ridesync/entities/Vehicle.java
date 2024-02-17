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
	
	public long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserrId() {
		return userId;
	}
	public void setUserId(String driverId) {
		this.userId = driverId;
	}
	
}
