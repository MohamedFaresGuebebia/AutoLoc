package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.web.dto.AgenceDTO;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/agence")
public class AgenceController {

    private final IAgenceService agenceService;

    @Operation(summary = "Lister les agences",
            description = "Retourne toutes les agences enregistrées en base.",
            responses = @ApiResponse(responseCode = "200", description = "Liste retournée avec succès"))
    @GetMapping("/retrieve-all-agences")
    public List<AgenceDTO> retrieveAllAgences() {
        return agenceService.retrieveAllAgences();
    }

    @Operation(summary = "Consulter une agence",
            description = "Retourne l'agence correspondant à l'identifiant fourni.",
            responses = @ApiResponse(responseCode = "200",
                    description = "Agence retournée (réponse vide si l'identifiant n'existe pas)"))
    @GetMapping("/retrieve-agence/{idAgence}")
    public AgenceDTO retrieveAgence(@PathVariable("idAgence") Long idAgence) {
        return agenceService.retrieveAgence(idAgence);
    }

    @Operation(summary = "Ajouter une agence",
            description = "Enregistre une nouvelle agence (nom, ville, adresse et téléphone obligatoires).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Agence créée, avec son identifiant généré"),
                    @ApiResponse(responseCode = "400", description = "Corps de la requête invalide")
            })
    @PostMapping("/add-agence")
    public AgenceDTO addAgence(@Valid @RequestBody AgenceDTO agence) {
        return agenceService.addAgence(agence);
    }

    @Operation(summary = "Ajouter plusieurs agences",
            description = "Enregistre une liste d'agences en une seule requête.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Agences créées"),
                    @ApiResponse(responseCode = "400", description = "Corps de la requête invalide")
            })
    @PostMapping("/add-agences")
    public List<AgenceDTO> addAgences(@Valid @RequestBody List<AgenceDTO> agences) {
        return agenceService.addAgences(agences);
    }

    @Operation(summary = "Modifier une agence",
            description = "Met à jour l'agence dont l'identifiant (idAgence) est fourni dans le corps.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Agence modifiée"),
                    @ApiResponse(responseCode = "400", description = "Corps de la requête invalide")
            })
    @PutMapping("/update-agence")
    public AgenceDTO updateAgence(@Valid @RequestBody AgenceDTO agence) {
        return agenceService.updateAgence(agence);
    }

    @Operation(summary = "Supprimer une agence",
            description = "Supprime l'agence correspondant à l'identifiant fourni.",
            responses = @ApiResponse(responseCode = "200", description = "Agence supprimée"))
    @DeleteMapping("/remove-agence/{idAgence}")
    public void removeAgence(@PathVariable("idAgence") Long idAgence) {
        agenceService.removeAgence(idAgence);
    }
}