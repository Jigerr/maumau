package de.berlin.htw.kba.maumau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("de.berlin.htw.kba.maumau")
public class SpringBootWebApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootWebApplication.class, args);

    }
}
