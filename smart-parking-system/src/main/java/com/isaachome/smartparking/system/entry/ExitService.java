package com.isaachome.smartparking.system.entry;

import com.isaachome.smartparking.system.event.VehicleExitedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ExitService {

    private final ParkingEntryRepository parkingEntryRepository;
    private final ApplicationEventPublisher eventPublisher;
    public ExitService(ParkingEntryRepository parkingEntryRepository, ParkingEntryRepository parkingEntryRepository1, ApplicationEventPublisher eventPublisher) {
        this.parkingEntryRepository = parkingEntryRepository1;
        this.eventPublisher = eventPublisher;
    }

    public void vehicleExit(String vehicleNumber) {
        //  get vehicle from the database;
        // update exit time and status
        // save to db
        var parkingEntry = parkingEntryRepository.findByVehicleNumberAndActiveTrue(vehicleNumber)
                .orElseThrow(()-> new IllegalArgumentException("Vehicle with number "+vehicleNumber+" not found or already exited"));
        parkingEntry.setExitTime(java.time.LocalDateTime.now());
        parkingEntry.setActive(false);
        parkingEntryRepository.save(parkingEntry);
        // publish event
        eventPublisher.publishEvent(new VehicleExitedEvent(vehicleNumber, parkingEntry.getEntryTime(),parkingEntry.getExitTime()));
    }
}
