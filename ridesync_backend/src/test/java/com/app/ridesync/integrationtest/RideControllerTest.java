package com.app.ridesync.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.app.ridesync.controllers.RideController;
import com.app.ridesync.dto.requests.RideInput;
import com.app.ridesync.dto.requests.RideStatusUpdateRequest;
import com.app.ridesync.dto.responses.ApiResponse;
import com.app.ridesync.dto.responses.RideInfoResponse;
import com.app.ridesync.dto.responses.RideResponse;
import com.app.ridesync.projections.RideDetailProjection;
import com.app.ridesync.repositories.GeoPointRepository;
import com.app.ridesync.repositories.LocationRepository;
import com.app.ridesync.repositories.RideInfoRepository;
import com.app.ridesync.repositories.RideRepository;
import com.app.ridesync.services.GeoPointService;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.LocationService;
import com.app.ridesync.services.RideInfoService;
import com.app.ridesync.services.RideService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RideController.class, JwtService.class, RideInfoService.class, RideService.class,
    GeoPointService.class, LocationService.class})
@ExtendWith(SpringExtension.class)
class RideControllerTest {
  @MockBean
  private GeoPointRepository geoPointRepository;

  @MockBean
  private LocationRepository locationRepository;

  @Autowired
  private RideController rideController;

  @MockBean
  private RideInfoRepository rideInfoRepository;

  @MockBean
  private RideRepository rideRepository;

  @Test
  void testUpdateRide() {

    // Arrange
    RideController rideController = new RideController();

    // Act
    ResponseEntity<ApiResponse<RideResponse>> actualUpdateRideResult = rideController.updateRide("ABC123",
        new RideInput());

    // Assert
    ApiResponse<RideResponse> body = actualUpdateRideResult.getBody();
    assertEquals("ERROR: Range [7, 6) out of bounds for length 6", body.message());
    assertNull(body.responseObject());
    assertEquals(500, actualUpdateRideResult.getStatusCodeValue());
    assertFalse(body.success());
    assertTrue(actualUpdateRideResult.hasBody());
    assertTrue(actualUpdateRideResult.getHeaders().isEmpty());
  }


  @Test
  void testGetRideDetail() {
    // Arrange and Act
    ResponseEntity<ApiResponse<RideDetailProjection>> actualRideDetail = (new RideController()).getRideDetail(1);

    // Assert
    ApiResponse<RideDetailProjection> body = actualRideDetail.getBody();
    assertEquals(
        "Result set retrieval failed with the following error Cannot invoke \"com.app.ridesync.services.RideService"
            + ".getRideDetailProjection(java.lang.Integer)\" because \"this.rideService\" is null",
        body.message());
    assertNull(body.responseObject());
    assertEquals(500, actualRideDetail.getStatusCodeValue());
    assertFalse(body.success());
    assertTrue(actualRideDetail.hasBody());
    assertTrue(actualRideDetail.getHeaders().isEmpty());
  }

  @Test
  void testUpdateRideStatus() {
    // Arrange
    RideController rideController = new RideController();

    // Act
    ResponseEntity<ApiResponse<Object>> actualUpdateRideStatusResult = rideController.updateRideStatus("ABC123",
        new RideStatusUpdateRequest());

    // Assert
    ApiResponse<Object> body = actualUpdateRideStatusResult.getBody();
    assertEquals("Update failed with the following error: Range [7, 6) out of bounds for length 6", body.message());
    assertNull(body.responseObject());
    assertEquals(500, actualUpdateRideStatusResult.getStatusCodeValue());
    assertFalse(body.success());
    assertTrue(actualUpdateRideStatusResult.hasBody());
    assertTrue(actualUpdateRideStatusResult.getHeaders().isEmpty());
  }


  @Test
  void testGetRiderLocation() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ResponseEntity<ApiResponse<RideInfoResponse>> actualRiderLocation = (new RideController()).getRiderLocation(1);

    // Assert
    ApiResponse<RideInfoResponse> body = actualRiderLocation.getBody();
    assertEquals(
        "Result set retrieval failed with the following error Cannot invoke \"com.app.ridesync.services"
            + ".RideInfoService.getDriverLocation(java.lang.Integer)\" because \"this.rideInfoService\" is null",
        body.message());
    assertNull(body.responseObject());
    assertEquals(500, actualRiderLocation.getStatusCodeValue());
    assertFalse(body.success());
    assertTrue(actualRiderLocation.hasBody());
    assertTrue(actualRiderLocation.getHeaders().isEmpty());
  }

  @Test
  void testGetRide() throws Exception {

    // Arrange
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/ride/getRide/{userId}", 1);

    // Act
    MockMvcBuilders.standaloneSetup(rideController).build().perform(requestBuilder);
  }
}
