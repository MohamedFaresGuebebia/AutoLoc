package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.web.dto.ClientDTO;

@Component
public class ClientMapper {

    public ClientDTO toDTO(Client client) {
        return new ClientDTO(
                client.getIdClient(),
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone(),
                client.getNumPermis(),
                client.getDateInscription());
    }

    public Client toEntity(ClientDTO dto) {
        Client client = new Client();
        updateEntity(client, dto);
        return client;
    }

    public void updateEntity(Client client, ClientDTO dto) {
        client.setNom(dto.nom());
        client.setPrenom(dto.prenom());
        client.setEmail(dto.email());
        client.setTelephone(dto.telephone());
        client.setNumPermis(dto.numPermis());
        client.setDateInscription(dto.dateInscription());
    }
}