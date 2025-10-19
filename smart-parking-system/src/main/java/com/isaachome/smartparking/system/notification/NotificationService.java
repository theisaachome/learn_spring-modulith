package com.isaachome.smartparking.system.notification;

import com.isaachome.smartparking.system.event.VehicleEnteredEvent;
import com.isaachome.smartparking.system.event.VehicleExitedEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
@Log4j2
@Service
public class NotificationService {

    @EventListener
    public void notifyOnVehicleEntry(VehicleEnteredEvent event) {
        // In a real system, this could send an email or SMS notification
        log.info("Notification: Vehicle {} entered at {}", event.vehicleNumber(), event.entryTime());
    }
    @EventListener
    public void notifyOnVehicleExit(VehicleExitedEvent event) {
        // In a real system, this could send an email or SMS notification
        log.info("Notification: Vehicle {} has exited the parking lot.", event.vehicleNumber());
    }
}
