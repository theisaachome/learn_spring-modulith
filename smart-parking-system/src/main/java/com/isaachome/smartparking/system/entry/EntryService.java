package com.isaachome.smartparking.system.entry;

import com.isaachome.smartparking.system.event.VehicleEnteredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EntryService {

    private final ParkingEntryRepository parkingEntryRepository;
    private final ApplicationEventPublisher eventPublisher;

    public EntryService(ParkingEntryRepository parkingEntryRepository, ApplicationEventPublisher eventPublisher) {
        this.parkingEntryRepository = parkingEntryRepository;
        this.eventPublisher = eventPublisher;
    }
    // save vehicle entry details
    // allocate a parking slot
    // send notification to the user

    public void vehicleEntry(String vehicleNumber) {
        // implementation goes here
        ParkingEntry parkingEntry = new ParkingEntry(null,vehicleNumber, LocalDateTime.now(),null,true);
        parkingEntryRepository.save(parkingEntry);
        // publish an event
        eventPublisher.publishEvent(new VehicleEnteredEvent(vehicleNumber,parkingEntry.getEntryTime()));
    }
}
