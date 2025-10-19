package com.isaachome.smartparking.system.event;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

public record VehicleExitedEvent(String vehicleNumber, LocalDateTime entryTime,LocalDateTime exitTime) {
}
