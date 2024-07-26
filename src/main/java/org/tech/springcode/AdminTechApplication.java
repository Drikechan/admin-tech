package org.tech.springcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AdminTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminTechApplication.class, args);
    }

}
