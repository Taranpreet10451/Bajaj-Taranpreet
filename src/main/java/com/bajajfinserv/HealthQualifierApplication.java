package com.bajajfinserv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import com.bajajfinserv.service.QualifierService;

@SpringBootApplication
public class HealthQualifierApplication {

    @Autowired
    private QualifierService qualifierService;

    public static void main(String[] args) {
        SpringApplication.run(HealthQualifierApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runQualifier() {
        System.out.println("Starting Bajaj Finserv Health Qualifier...");
        qualifierService.executeQualifier();
    }
}
