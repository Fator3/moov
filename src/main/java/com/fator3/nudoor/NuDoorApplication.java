package com.fator3.nudoor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.fator3.nudoor.clients.ClientPackageScan;
import com.fator3.nudoor.configuration.ConfigurationPackageScan;
import com.fator3.nudoor.property.PropertyPackageScan;

@SpringBootApplication(scanBasePackageClasses = { ConfigurationPackageScan.class,
        PropertyPackageScan.class, ClientPackageScan.class })
@RestController
public class NuDoorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NuDoorApplication.class, args);
    }
}