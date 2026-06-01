package org.gestion.proyecto_gasolinera.DTO;

import org.gestion.proyecto_gasolinera.Gasolinera;

public class ClienteDTO {
    public int clientId;
    public String name;
    public String surname;
    public String username;
    public boolean isAdmin;
    private boolean active;
    private Gasolinera gasStation;



}
