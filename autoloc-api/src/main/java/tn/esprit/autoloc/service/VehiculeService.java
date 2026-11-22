package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.web.dto.VehiculeDTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.MaintenanceRepository;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VehiculeService implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final VehiculeMapper vehiculeMapper;
    private final EquipementRepository equipementRepository;
    private final MaintenanceRepository maintenanceRepository;

    @Override
    public List<VehiculeDTO> retrieveAllVehicules() {
        return vehiculeRepository.findAll().stream().map(vehiculeMapper::toDTO).toList();
    }

    @Override
    public VehiculeDTO addVehicule(VehiculeDTO dto) {
        return vehiculeMapper.toDTO(vehiculeRepository.save(vehiculeMapper.toEntity(dto)));
    }

    @Override
    public VehiculeDTO updateVehicule(VehiculeDTO dto) {
        Vehicule vehicule = findOrThrow(dto.idVehicule());
        vehiculeMapper.updateEntity(vehicule, dto);
        return vehiculeMapper.toDTO(vehiculeRepository.save(vehicule));
    }

    @Override
    public VehiculeDTO retrieveVehicule(Long idVehicule) {
        return vehiculeMapper.toDTO(findOrThrow(idVehicule));
    }

    @Override
    public void removeVehicule(Long idVehicule) {
        vehiculeRepository.deleteById(idVehicule);
    }

    @Override
    public List<VehiculeDTO> addVehicules(List<VehiculeDTO> vehicules) {
        List<Vehicule> entities = vehicules.stream().map(vehiculeMapper::toEntity).toList();
        return vehiculeRepository.saveAll(entities).stream().map(vehiculeMapper::toDTO).toList();
    }

    private Vehicule findOrThrow(Long idVehicule) {
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Vehicule introuvable : " + idVehicule));
    }

    @Override
    @Transactional
    public VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = findOrThrow(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new EntityNotFoundException("Équipement introuvable : " + idEquipement));
        equipement.setVehicule(vehicule);
        equipementRepository.saveAndFlush(equipement);
        return vehiculeMapper.toDTO(vehicule);
    }

    @Override
    @Transactional
    public VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation) {
        Vehicule vehicule = vehiculeRepository.findByImmatriculation(immatriculation)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule introuvable : " + immatriculation));
        LocalDate aujourdhui = LocalDate.now();
        Maintenance maintenance = new Maintenance();
        maintenance.setDescription(description);
        maintenance.setDateDebut(aujourdhui);
        maintenance.setDateFin(aujourdhui);
        maintenance.setVehicule(vehicule);
        maintenanceRepository.saveAndFlush(maintenance);
        return vehiculeMapper.toDTO(vehicule);
    }
}