package com.fator3.moov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.fator3.moov.clients.ClientPackageScan;
import com.fator3.moov.configuration.ConfigurationPackageScan;
import com.fator3.moov.property.PropertyPackageScan;

@SpringBootApplication(scanBasePackageClasses = { ConfigurationPackageScan.class,
        PropertyPackageScan.class, ClientPackageScan.class })
@RestController
public class MoovApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoovApplication.class, args);
    }
}