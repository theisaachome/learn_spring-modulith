package com.isaachome.smartparking.system;

import com.isaachome.smartparking.system.allocation.Slot;
import com.isaachome.smartparking.system.allocation.SlotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SmartParkingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartParkingSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(SlotRepository repo){
        return args -> {
            if(repo.count()==0){
                repo.save(new Slot(null, "A1", true, null));
                repo.save(new Slot(null, "A2", true, null));
                repo.save(new Slot(null, "B1", true, null));
                repo.save(new Slot(null, "B2", true, null));
            }
        };
    }
}
