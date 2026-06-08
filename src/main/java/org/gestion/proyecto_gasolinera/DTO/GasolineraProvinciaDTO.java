package org.gestion.proyecto_gasolinera.DTO;

public class GasolineraProvinciaDTO {
    final int gasStationId;
    final String name;
    final String provincia;
    final String active;

    public GasolineraProvinciaDTO(int gasStationId, String name, String provincia, String active){
        this.gasStationId = gasStationId;
        this.name = name;
        this.provincia = provincia;
        this.active = active;
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
