package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final IClientService clientService;

    @Operation(summary = "Lister les clients",
            description = "Retourne tous les clients enregistrés en base.",
            responses = @ApiResponse(responseCode = "200", description = "Liste retournée avec succès"))
    @GetMapping("/retrieve-all-clients")
    public List<Client> retrieveAllClients() {
        return clientService.retrieveAllClients();
    }

    @Operation(summary = "Consulter un client",
            description = "Retourne le client correspondant à l'identifiant fourni.",
            responses = @ApiResponse(responseCode = "200",
                    description = "Client retourné (réponse vide si l'identifiant n'existe pas)"))
    @GetMapping("/retrieve-client/{idClient}")
    public Client retrieveClient(@PathVariable("idClient") Long idClient) {
        return clientService.retrieveClient(idClient);
    }

    @Operation(summary = "Ajouter un client",
            description = "Enregistre un nouveau client. L'email et le numéro de permis doivent être uniques.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client créé, avec son identifiant généré"),
                    @ApiResponse(responseCode = "400", description = "Corps de la requête invalide")
            })
    @PostMapping("/add-client")
    public Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @Operation(summary = "Ajouter plusieurs clients",
            description = "Enregistre une liste de clients en une seule requête.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Clients créés"),
                    @ApiResponse(responseCode = "400", description = "Corps de la requête invalide")
            })
    @PostMapping("/add-clients")
    public List<Client> addClients(@RequestBody List<Client> clients) {
        return clientService.addClients(clients);
    }

    @Operation(summary = "Modifier un client",
            description = "Met à jour le client dont l'identifiant (idClient) est fourni dans le corps.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client modifié"),
                    @ApiResponse(responseCode = "400", description = "Corps de la requête invalide")
            })
    @PutMapping("/update-client")
    public Client updateClient(@RequestBody Client client) {
        return clientService.updateClient(client);
    }

    @Operation(summary = "Supprimer un client",
            description = "Supprime le client correspondant à l'identifiant fourni.",
            responses = @ApiResponse(responseCode = "200", description = "Client supprimé"))
    @DeleteMapping("/remove-client/{idClient}")
    public void removeClient(@PathVariable("idClient") Long idClient) {
        clientService.removeClient(idClient);
    }
}