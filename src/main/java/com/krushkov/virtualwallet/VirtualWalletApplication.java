package com.krushkov.virtualwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VirtualWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualWalletApplication.class, args);
    }

}
