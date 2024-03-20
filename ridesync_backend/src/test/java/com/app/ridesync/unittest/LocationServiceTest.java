package com.app.ridesync.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.ridesync.entities.Location;
import com.app.ridesync.repositories.LocationRepository;
import com.app.ridesync.services.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LocationService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class LocationServiceTest {
    @MockBean
    private LocationRepository locationRepository;

    @Autowired
    private LocationService locationService;

    @Test
    void testAddLocation() {
        Location location = new Location();
        location.setAddress("42 Main St");
        location.setLandmark("Landmark");
        location.setLattitude(10.0d);
        location.setLocationId(1);
        location.setLongitude(10.0d);
        when(locationRepository.save(Mockito.<Location>any())).thenReturn(location);

        Location location2 = new Location();
        location2.setAddress("42 Main St");
        location2.setLandmark("Landmark");
        location2.setLattitude(10.0d);
        location2.setLocationId(1);
        location2.setLongitude(10.0d);

        Location actualAddLocationResult = locationService.addLocation(location2);

        verify(locationRepository).save(Mockito.<Location>any());
        assertSame(location, actualAddLocationResult);
    }

    @Test
    void testUpdateLocation() {
        Location location = new Location();
        location.setAddress("42 Main St");
        location.setLandmark("Landmark");
        location.setLattitude(10.0d);
        location.setLocationId(1);
        location.setLongitude(10.0d);

        Location location2 = new Location();
        location2.setAddress("42 Main St");
        location2.setLandmark("Landmark");
        location2.setLattitude(10.0d);
        location2.setLocationId(1);
        location2.setLongitude(10.0d);
        when(locationRepository.save(Mockito.<Location>any())).thenReturn(location2);
        when(locationRepository.findByLocationId(Mockito.<Integer>any())).thenReturn(location);

        Location location3 = new Location();
        location3.setAddress("42 Main St");
        location3.setLandmark("Landmark");
        location3.setLattitude(10.0d);
        location3.setLocationId(1);
        location3.setLongitude(10.0d);

        Location actualUpdateLocationResult = locationService.updateLocation(location3);

        verify(locationRepository).findByLocationId(Mockito.<Integer>any());
        verify(locationRepository).save(Mockito.<Location>any());
        assertEquals("42 Main St", actualUpdateLocationResult.getAddress());
        assertEquals("Landmark", actualUpdateLocationResult.getLandmark());
        assertEquals(10.0d, actualUpdateLocationResult.getLattitude());
        assertEquals(10.0d, actualUpdateLocationResult.getLongitude());
        assertSame(location, actualUpdateLocationResult);
    }

    @Test
    void testFindLocationById() {
        Location location = new Location();
        location.setAddress("42 Main St");
        location.setLandmark("Landmark");
        location.setLattitude(10.0d);
        location.setLocationId(1);
        location.setLongitude(10.0d);
        when(locationRepository.findByLocationId(Mockito.<Integer>any())).thenReturn(location);

        Location actualFindLocationByIdResult = locationService.findLocationById(1);

        verify(locationRepository).findByLocationId(Mockito.<Integer>any());
        assertSame(location, actualFindLocationByIdResult);
    }
}
