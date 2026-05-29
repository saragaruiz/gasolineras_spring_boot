package org.gestion.proyecto_gasolinera;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
    @Table(name="register")
    public class Registros {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int registerId;
        @ManyToOne
        @JoinColumn(name="clientId")
        private Cliente cliente;
        @ManyToOne
        @JoinColumn(name ="gasStationId")
        private Gasolinera gasolinera;
        private LocalDate creationDate;
        private String tipo;


    public int getRegisterId() {
        return registerId;
    }

    public void setRegisterId(int registerId) {
        this.registerId = registerId;
    }
    public Cliente getCliente(){
        return cliente;
    }
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public Gasolinera getGasolinera() {
        return gasolinera;
    }

    public void setGasolinera(Gasolinera gasolinera) {
        this.gasolinera = gasolinera;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
