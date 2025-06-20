package de.ait.javalessons;

import de.ait.javalessons.model.CompanyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CompanyProperties.class)
public class SpringBootGr61EApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootGr61EApplication.class, args);
    }

}

