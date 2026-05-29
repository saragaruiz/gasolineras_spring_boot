package org.gestion.proyecto_gasolinera;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
    @Table(name="province")
    public class Provincia {
        @Id
        private int provinceId;
        private LocalDate creationDate;
        private String name;

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public LocalDate getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(LocalDate creationDate) {
            this.creationDate = creationDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
