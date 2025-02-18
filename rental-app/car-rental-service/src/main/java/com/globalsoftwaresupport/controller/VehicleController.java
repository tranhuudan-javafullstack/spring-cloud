package com.globalsoftwaresupport.controller;

import com.globalsoftwaresupport.model.Vehicle;
import com.globalsoftwaresupport.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle create(@RequestBody Vehicle vehicle) {
        return service.create(vehicle);
    }

    @PostMapping("{vehicleId}/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void associate(@PathVariable(name = "vehicleId") String vehicleId,
                          @PathVariable(name = "userId") String userId) {
        service.validateVehicle(vehicleId);
        service.associate(vehicleId, userId);
    }

    @DeleteMapping("{vehicleId}/users/{userId}")
    public void delete(@PathVariable(name = "vehicleId") String vehicleId,
                       @PathVariable(name = "userId") String userId) {
        service.validateVehicle(vehicleId);
        service.delete(vehicleId, userId);
    }
}