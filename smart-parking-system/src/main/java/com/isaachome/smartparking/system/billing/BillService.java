package com.isaachome.smartparking.system.billing;

import com.isaachome.smartparking.system.event.VehicleExitedEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Log4j2
@Service
public class BillService {

    private final  BillingRecordRepository billingRecordRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    public BillService(BillingRecordRepository billingRecordRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.billingRecordRepository = billingRecordRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener
    public void handleVehicleExit(VehicleExitedEvent event) {
        // calculate billing amount based on entry and exit time
        var duration = Duration.between(event.entryTime(), event.exitTime());
        double hourlyRate = 50.0; // Example hourly rate
        double amount = Math.max(20,(duration.toMillis()/60.0) * hourlyRate);
        var billingRecord = new BillingRecord();
        billingRecord.setVehicleNumber(event.vehicleNumber());
        billingRecord.setAmount(amount);
        billingRecord.setBillingTime(event.exitTime());
        billingRecordRepository.save(billingRecord);
        // publish billing event if needed
        log.info("Generated bill for vehicle {}: Amount = {}", event.vehicleNumber(), amount);
    }
}
