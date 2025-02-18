package com.globalsoftwaresupport.service;

import com.globalsoftwaresupport.model.Status;
import com.globalsoftwaresupport.model.Vehicle;
import com.globalsoftwaresupport.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;

    public VehicleServiceImpl(VehicleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        vehicle.setStatus(Status.AVAILABLE);
        vehicle.setOwner(null);
        vehicle.setAssociationDate(null);

        return repository.save(vehicle);
    }

    @Override
    public void validateVehicle(String vehicleId) {
        repository.findById(Long.valueOf(vehicleId)).orElseThrow();
    }

    @Override
    public void associate(String vehicleId, String userId) {
        var vehicle = repository.findById(Long.valueOf(vehicleId))
                .filter(v -> v.getStatus() == Status.AVAILABLE)
                .orElseThrow();

        vehicle.setOwner(userId);
        vehicle.setAssociationDate(new Date());
        vehicle.setStatus(Status.ASSOCIATED);

        repository.save(vehicle);
    }

    @Override
    public void delete(String vehicleId, String userId) {
        var vehicle = repository.findById(Long.valueOf(vehicleId))
                .filter(v -> v.getStatus() == Status.ASSOCIATED)
                .filter(v -> userId.equals(v.getOwner()))
                .orElseThrow();

        vehicle.setOwner(null);
        vehicle.setAssociationDate(null);
        vehicle.setStatus(Status.AVAILABLE);

        repository.save(vehicle);
    }
}
