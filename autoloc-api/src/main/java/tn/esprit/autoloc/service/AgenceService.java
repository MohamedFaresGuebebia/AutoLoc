package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;

import tn.esprit.autoloc.mapper.AgenceMapper;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.web.dto.AgenceDTO;



import java.util.List;

@Service
@AllArgsConstructor
public class AgenceService implements IAgenceService {

    private final AgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    public List<AgenceDTO> retrieveAllAgences() {
        return agenceRepository.findAll().stream().map(agenceMapper::toDTO).toList();
    }

    @Override
    public AgenceDTO addAgence(AgenceDTO dto) {
        return agenceMapper.toDTO(agenceRepository.save(agenceMapper.toEntity(dto)));
    }

    @Override
    public AgenceDTO updateAgence(AgenceDTO dto) {
        Agence agence = findOrThrow(dto.idAgence());
        agenceMapper.updateEntity(agence, dto);
        return agenceMapper.toDTO(agenceRepository.save(agence));
    }


    @Override
    public AgenceDTO retrieveAgence(Long idAgence) {
        return agenceMapper.toDTO(findOrThrow(idAgence));
    }

    @Override
    public void removeAgence(Long idAgence) {
        agenceRepository.deleteById(idAgence);
    }

    @Override
    public List<AgenceDTO> addAgences(List<AgenceDTO> agences) {
        List<Agence> entities = agences.stream().map(agenceMapper::toEntity).toList();
        return agenceRepository.saveAll(entities).stream().map(agenceMapper::toDTO).toList();
    }

    private Agence findOrThrow(Long idAgence) {
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable : " + idAgence));
    }
}