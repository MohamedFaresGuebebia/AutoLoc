package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.web.dto.VehiculeDTO;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeService implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final VehiculeMapper vehiculeMapper;

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
}