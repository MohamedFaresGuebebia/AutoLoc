package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.AgenceDTO;
import java.util.List;

public interface IAgenceService {
    List<AgenceDTO> retrieveAllAgences();
    AgenceDTO addAgence(AgenceDTO a);
    AgenceDTO updateAgence(AgenceDTO a);
    AgenceDTO retrieveAgence(Long idAgence);
    void removeAgence(Long idAgence);
    List<AgenceDTO> addAgences(List<AgenceDTO> agences);
}
