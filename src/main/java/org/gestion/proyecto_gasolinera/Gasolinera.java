package org.gestion.proyecto_gasolinera;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
    @Table(name="gasStation")
    public class Gasolinera {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int gasStationId;
        private String name;
        @ManyToOne
        @JoinColumn(name= "provinceId")
        private Provincia province;
        private double petrolPrice;
        private LocalDate creationDate;
        private String active;

        public int getGasStationId() {
            return gasStationId;
        }

        public void setGasStationId(int gasStationId) {
            this.gasStationId = gasStationId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Provincia getProvince() {
            return province;
        }

        public void setProvince(Provincia province) {
            this.province = province;
        }

        public double getPetrolPrice() {
            return petrolPrice;
        }

        public void setPetrolPrice(double petrolPrice) {
            this.petrolPrice = petrolPrice;
        }

        public LocalDate getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(LocalDate creationDate) {
            this.creationDate = creationDate;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }
    }