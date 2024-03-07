package com.app.ridesync;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.app.ridesync.config.ObjectMapperConfig;
import com.app.ridesync.converter.GeoPointConverter;
import com.app.ridesync.entities.GeoPointRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.LatLng;

public class GeoPointTest {

	private static GeoPointConverter geoPointConverter;
	private static ObjectMapperConfig objectMapperConfig;

	@BeforeAll
	public static void setUp() {
		objectMapperConfig = new ObjectMapperConfig();
		geoPointConverter = new GeoPointConverter(objectMapperConfig);
	}

	@Test
	public void covertToDataBasetColumnTest() throws JsonProcessingException {
		List<LatLng>latLngs = Arrays.asList(new LatLng(44.635580000000004,-63.595130000000005));
		GeoPointRecord geopoints = new GeoPointRecord(latLngs);
		ObjectMapper objectMapper = new ObjectMapper();

		byte[] expectedBytes = objectMapper.writeValueAsBytes(geopoints);		
		byte[] actualBytes = geoPointConverter.convertToDatabaseColumn(geopoints);

		assertArrayEquals(expectedBytes, actualBytes);
	}  
}
