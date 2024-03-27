package com.app.ridesync.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.ridesync.dto.requests.VehicleInput;
import com.app.ridesync.dto.responses.GetVehicleResponse;
import com.app.ridesync.dto.responses.VehicleResponse;
import com.app.ridesync.entities.Vehicle;
import com.app.ridesync.repositories.VehicleRepository;

import java.util.ArrayList;
import java.util.List;

import com.app.ridesync.services.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VehicleService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class VehicleServiceTest {
    @MockBean
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;


    @Test
    void addVehicleSuccessTest() {
        Vehicle vehicle = new Vehicle();
        vehicle.setDocumentId(1);
        vehicle.setMake("Make");
        vehicle.setModel("Model");
        vehicle.setRegNo("Reg No");
        vehicle.setType("Type");
        vehicle.setUserId(1);
        vehicle.setVehicleId(1);
        when(vehicleRepository.save(Mockito.<Vehicle>any())).thenReturn(vehicle);
        VehicleResponse expectedVehicleResponse=VehicleResponse.builder()
                                                .message("Vehicle inserted Successfully")
                                                .success(true)
                                                .vehicle(vehicle)
                                                .build();
        VehicleResponse actualAddVehicleResult = vehicleService.addVehicle(new VehicleInput());

        verify(vehicleRepository).save(Mockito.<Vehicle>any());
        assertEquals(expectedVehicleResponse, actualAddVehicleResult);
      
    }


    @Test
    void addVehicleFailedTest() {
        VehicleResponse actualAddVehicleResult = vehicleService.addVehicle(null);

        assertEquals(
                "java.lang.NullPointerException: Cannot invoke \"com.app.ridesync.dto.requests.VehicleInput.getRegNo()\""+ " because \"input\" is null",
                actualAddVehicleResult.getMessage());
    }


    @Test
    void getVehiclesByUserIdTest() {
        Vehicle vehicle=new Vehicle();
        List<Vehicle> vehicles=new ArrayList<>();
        vehicles.add(vehicle);
        when(vehicleRepository.findByUserId(Mockito.<Integer>any())).thenReturn(vehicles);
        GetVehicleResponse expectedReponse=GetVehicleResponse
                                        .builder()
                                        .message("Vehicles Fetched Successfully")
                                        .vehicles(vehicles)
                                        .success(true)
                                        .build();
        GetVehicleResponse actualVehiclesByUserId = vehicleService.getVehiclesByUserId(1);

        verify(vehicleRepository).findByUserId(Mockito.<Integer>any());
        assertEquals(expectedReponse, actualVehiclesByUserId);
    }


    @Test
    void updateVehicleByIdSuccessTest() {
        Vehicle vehicle = new Vehicle();
        vehicle.setDocumentId(1);
        vehicle.setMake("Make");
        vehicle.setModel("Model");
        vehicle.setRegNo("Reg No");
        vehicle.setType("Type");
        vehicle.setUserId(1);
        vehicle.setVehicleId(1);

        when(vehicleRepository.save(Mockito.<Vehicle>any())).thenReturn(vehicle);
        when(vehicleRepository.findByVehicleId(Mockito.<Integer>any())).thenReturn(vehicle);
        VehicleResponse expectedVehicleResponse=VehicleResponse.builder()
                                                .message("Updated Selected Vehicle Successfully")
                                                .vehicle(vehicle)
                                                .success(true)
                                                .build();
        VehicleResponse actualUpdateVehicleByIdResult = vehicleService.updateVehicleById(new VehicleInput());

        verify(vehicleRepository).findByVehicleId(Mockito.<Integer>any());
        verify(vehicleRepository).save(Mockito.<Vehicle>any());
        assertEquals(expectedVehicleResponse, actualUpdateVehicleByIdResult);
    }


    @Test
    void updateVehicleByIdFailedTest() {
        VehicleResponse actualUpdateVehicleByIdResult = vehicleService.updateVehicleById(null);
        assertEquals(
                "java.lang.NullPointerException: Cannot invoke \"com.app.ridesync.dto.requests.VehicleInput.getVehicleId()\""+ " because \"input\" is null",
                actualUpdateVehicleByIdResult.getMessage());
    }


    @Test
    void deleteVehicleTest() {
        Vehicle vehicle = new Vehicle();
        vehicle.setDocumentId(1);
        vehicle.setMake("Make");
        vehicle.setModel("Model");
        vehicle.setRegNo("Reg No");
        vehicle.setType("Type");
        vehicle.setUserId(1);
        vehicle.setVehicleId(1);
        when(vehicleRepository.findByVehicleId(Mockito.<Integer>any())).thenReturn(vehicle);
        doNothing().when(vehicleRepository).delete(Mockito.<Vehicle>any());
        VehicleResponse expectedVehicleResponse=VehicleResponse.builder()
                                            .message("Deleted Selected Vehicle Successfully")
                                            .vehicle(vehicle)
                                            .success(true)
                                            .build();
        VehicleResponse actualDeleteVehicleResult = vehicleService.deleteVehicle(1);

        verify(vehicleRepository).findByVehicleId(Mockito.<Integer>any());
        verify(vehicleRepository).delete(Mockito.<Vehicle>any());
       assertEquals(expectedVehicleResponse, actualDeleteVehicleResult);
    }
}
