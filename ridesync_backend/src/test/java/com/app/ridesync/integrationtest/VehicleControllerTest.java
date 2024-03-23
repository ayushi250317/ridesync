package com.app.ridesync.integrationtest;

import com.app.ridesync.controllers.VehicleController;
import com.app.ridesync.dto.requests.VehicleInput;
import com.app.ridesync.dto.responses.GetVehicleResponse;
import com.app.ridesync.dto.responses.VehicleResponse;
import com.app.ridesync.entities.Vehicle;
import com.app.ridesync.services.JwtService;
import com.app.ridesync.services.VehicleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class VehicleControllerTest {
    @Mock
    VehicleService vehicleService;
    @Mock
    JwtService jwtService;
    @InjectMocks
    VehicleController vehicleController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addVehicleTest() throws Exception {
        when(vehicleService.addVehicle(any())).thenReturn(new VehicleResponse(new Vehicle(Integer.valueOf(0), "regNo", Integer.valueOf(0), "model", "make", "type", Integer.valueOf(0)), "message", true));
        when(jwtService.extractUserId(anyString())).thenReturn(Integer.valueOf(0));

        VehicleResponse result = vehicleController.addVehicle("jwtToken", new VehicleInput("regNo", Integer.valueOf(0), "model", "make", "type", Integer.valueOf(0), Integer.valueOf(0)));
        Assert.assertEquals(new VehicleResponse(new Vehicle(Integer.valueOf(0), "regNo", Integer.valueOf(0), "model", "make", "type", Integer.valueOf(0)), "message", true), result);
    }

    @Test
    public void getVehiclesByIdTest() throws Exception {
        when(vehicleService.getVehiclesByUserId(anyInt())).thenReturn(new GetVehicleResponse(List.of(new Vehicle(Integer.valueOf(0), "regNo", Integer.valueOf(0), "model", "make", "type", Integer.valueOf(0))), "message", true));
        when(jwtService.extractUserId(anyString())).thenReturn(Integer.valueOf(0));

        GetVehicleResponse result = vehicleController.getVehiclesById("id", "jwtToken");
        Assert.assertEquals(new GetVehicleResponse(List.of(new Vehicle(Integer.valueOf(0), "regNo", Integer.valueOf(0), "model", "make", "type", Integer.valueOf(0))), "message", true), result);
    }

    @Test
    public void updateVehicleTest() throws Exception {
        when(vehicleService.updateVehicleById(any())).thenReturn(new VehicleResponse(new Vehicle(Integer.valueOf(0), "regNo", Integer.valueOf(0), "model", "make", "type", Integer.valueOf(0)), "message", true));

        VehicleResponse result = vehicleController.updateVehicle(new VehicleInput("regNo", Integer.valueOf(0), "model", "make", "type", Integer.valueOf(0), Integer.valueOf(0)));
        Assert.assertEquals(new VehicleResponse(new Vehicle(Integer.valueOf(0), "regNo", Integer.valueOf(0), "model", "make", "type", Integer.valueOf(0)), "message", true), result);
    }

    @Test
    public void deleteVehicleTest() throws Exception {
        when(vehicleService.deleteVehicle(anyInt())).thenReturn(new VehicleResponse(new Vehicle(Integer.valueOf(0), "regNo", Integer.valueOf(0), "model", "make", "type", Integer.valueOf(0)), "message", true));

        VehicleResponse result = vehicleController.deleteVehicle(Integer.valueOf(0));
        Assert.assertEquals(new VehicleResponse(new Vehicle(Integer.valueOf(0), "regNo", Integer.valueOf(0), "model", "make", "type", Integer.valueOf(0)), "message", true), result);
    }
}