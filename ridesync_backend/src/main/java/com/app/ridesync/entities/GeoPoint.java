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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GeoPoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Convert(converter = GeoPointConverter.class)	
	@Lob
	@Column(length = (int)2e24-1)
	private GeoPointRecord geoPointRecord;			// a length of 2^24 is treated as mediumBlob in mysql and can store ~16MB of data.

	@OneToOne()
	@JoinColumn(name = "ride_id")
	private Ride ride;

}
