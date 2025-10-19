package com.isaachome.smartparking.system.allocation;

import com.isaachome.smartparking.system.event.VehicleEnteredEvent;
import com.isaachome.smartparking.system.event.VehicleExitedEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
@Log4j2
@Service
public class SlotAllocationService {

    private final  SlotRepository slotRepository;

    public SlotAllocationService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @EventListener(VehicleEnteredEvent.class)
    public void handleVehicleEntry(VehicleEnteredEvent event) {
        // find an available parking slot
        var slot = slotRepository.findFirstByAvailableTrue()
                .orElseThrow(()-> new RuntimeException("No available slots"));
        // allocate the slot to the vehicle
        slot.setAvailable(false);
        slot.setVehicleNumber(event.vehicleNumber());
        slotRepository.save(slot);
        log.info("Allocated slot {} to vehicle {}", slot.getSlotNumber(), event.vehicleNumber());

    }

    @EventListener
    public void handleVehicleExit(VehicleExitedEvent event) {
        // find the slot allocated to the vehicle
        var slot = slotRepository.findByVehicleNumber(event.vehicleNumber())
                .orElseThrow(()-> new RuntimeException("No slot found for vehicle "+event.vehicleNumber()));
        // free up the slot
        slot.setAvailable(true);
        slot.setVehicleNumber(null);
        slotRepository.save(slot);
        log.info("Freed up slot {} from vehicle {}", slot.getSlotNumber(), event.vehicleNumber());
    }
}
