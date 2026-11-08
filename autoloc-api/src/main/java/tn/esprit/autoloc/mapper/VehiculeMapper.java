package tn.esprit.autoloc.mapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

@Component
@AllArgsConstructor
public class VehiculeMapper {

    private final AgenceRepository agenceRepository;

    public VehiculeDTO toDTO(Vehicule vehicule) {
        return new VehiculeDTO(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                vehicule.getAgence().getIdAgence());
    }

    public Vehicule toEntity(VehiculeDTO dto) {
        Vehicule vehicule = new Vehicule();
        updateEntity(vehicule, dto);
        return vehicule;
    }

    public void updateEntity(Vehicule vehicule, VehiculeDTO dto) {
        vehicule.setImmatriculation(dto.immatriculation());
        vehicule.setMarque(dto.marque());
        vehicule.setModele(dto.modele());
        vehicule.setCategorie(dto.categorie());
        vehicule.setTarifJournalier(dto.tarifJournalier());
        vehicule.setStatut(dto.statut());
        vehicule.setAgence(agenceRepository.findById(dto.idAgence())
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable : " + dto.idAgence())));
    }
}