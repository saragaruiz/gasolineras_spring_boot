package org.gestion.proyecto_gasolinera;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoGasolineraApplication {
    @PostConstruct
    public void test() {
        System.out.println("APP STARTED OK");
    }
    public static void main(String[] args) {
        SpringApplication.run(ProyectoGasolineraApplication.class, args);

    }
}
