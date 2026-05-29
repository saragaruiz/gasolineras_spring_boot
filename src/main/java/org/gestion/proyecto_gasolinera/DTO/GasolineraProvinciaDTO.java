package org.gestion.proyecto_gasolinera.DTO;

import org.gestion.proyecto_gasolinera.repositories.GasolineraRepository;

public class GasolineraProvinciaDTO {
    private int gasStationId;
    private String name;
    private String provincia;
    private String active;

    public GasolineraProvinciaDTO(int gasStationId, String name, String provincia, String active){
        this.gasStationId = gasStationId;
        this.name = name;
        this.provincia = provincia;
        this.active = active;
    }

    public int getGasStationId() {
        return gasStationId;
    }

    public String getName() {
        return name;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getActive() {
        return active;
    }
}
