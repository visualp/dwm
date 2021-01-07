package com.visualp.dwm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.visualp")
@EnableScheduling
public class DwmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DwmApplication.class, args);
    }

}
