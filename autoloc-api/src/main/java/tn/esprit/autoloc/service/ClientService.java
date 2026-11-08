package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.mapper.ClientMapper;
import tn.esprit.autoloc.repository.ClientRepository;
import tn.esprit.autoloc.web.dto.ClientDTO;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public List<ClientDTO> retrieveAllClients() {
        return clientRepository.findAll().stream().map(clientMapper::toDTO).toList();
    }

    @Override
    public ClientDTO addClient(ClientDTO dto) {
        return clientMapper.toDTO(clientRepository.save(clientMapper.toEntity(dto)));
    }

    @Override
    public ClientDTO updateClient(ClientDTO dto) {
        Client client = findOrThrow(dto.idClient());
        clientMapper.updateEntity(client, dto);
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Override
    public ClientDTO retrieveClient(Long idClient) {
        return clientMapper.toDTO(findOrThrow(idClient));
    }

    @Override
    public void removeClient(Long idClient) {
        clientRepository.deleteById(idClient);
    }

    @Override
    public List<ClientDTO> addClients(List<ClientDTO> clients) {
        List<Client> entities = clients.stream().map(clientMapper::toEntity).toList();
        return clientRepository.saveAll(entities).stream().map(clientMapper::toDTO).toList();
    }

    private Client findOrThrow(Long idClient) {
        return clientRepository.findById(idClient)
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable : " + idClient));
    }
}