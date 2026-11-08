package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.VehiculeDTO;
import java.util.List;

public interface IVehiculeService {
    List<VehiculeDTO> retrieveAllVehicules();
    VehiculeDTO addVehicule(VehiculeDTO v);
    VehiculeDTO updateVehicule(VehiculeDTO v);
    VehiculeDTO retrieveVehicule(Long idVehicule);
    void removeVehicule(Long idVehicule);
    List<VehiculeDTO> addVehicules(List<VehiculeDTO> vehicules);
}