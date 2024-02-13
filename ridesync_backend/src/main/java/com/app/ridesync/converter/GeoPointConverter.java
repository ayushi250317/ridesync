package com.app.ridesync.converter;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.ridesync.config.ObjectMapperConfig;
import com.app.ridesync.entities.GeoPointRecord;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.lang.RuntimeException;

@Converter
public class GeoPointConverter implements AttributeConverter<GeoPointRecord,String>{
	private final ObjectMapperConfig objectMapperConfig;

	@Autowired
	public GeoPointConverter(ObjectMapperConfig objectMapperConfig) {
		this.objectMapperConfig = objectMapperConfig;
	}

	@Override
	public String convertToDatabaseColumn(GeoPointRecord geoPoints) {		
		try {
			return objectMapperConfig.objectMapper().writeValueAsString(geoPoints);
		} catch (Exception e) {
			throw new RuntimeException("Error serializing the route coordinates to JSON.", e);
		}
	}

	@Override
	public GeoPointRecord convertToEntityAttribute(String geoPoints) {
		try {
			return objectMapperConfig.objectMapper().readValue(geoPoints,GeoPointRecord.class);
		} catch (Exception e) {
			throw new RuntimeException("Error de serializing the route coordinates to class object.", e);
		}	
	}	
}
