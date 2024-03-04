package com.app.ridesync.entities;

import java.util.List;

import com.google.maps.model.LatLng;


public record GeoPointRecord (List<LatLng> geoPoints){
	public List<LatLng> getGeoPoints() {
		return geoPoints;
	}
}
