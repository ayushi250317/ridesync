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

        Location actualAddLocationResult = locationService.addLocation(location);

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

        when(locationRepository.save(Mockito.<Location>any())).thenReturn(location);
        when(locationRepository.findByLocationId(Mockito.<Integer>any())).thenReturn(location);

        Location actualUpdateLocationResult = locationService.updateLocation(location);

        verify(locationRepository).findByLocationId(Mockito.<Integer>any());
        verify(locationRepository).save(Mockito.<Location>any());
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
