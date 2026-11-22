package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/vehicule")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Lister les véhicules",
            description = "Retourne tous les véhicules enregistrés en base.",
            responses = @ApiResponse(responseCode = "200", description = "Liste retournée avec succès"))
    @GetMapping("/retrieve-all-vehicules")
    public List<VehiculeDTO> retrieveAllVehicules() {
        return vehiculeService.retrieveAllVehicules();
    }

    @Operation(summary = "Consulter un véhicule",
            description = "Retourne le véhicule correspondant à l'identifiant fourni.",
            responses = @ApiResponse(responseCode = "200",
                    description = "Véhicule retourné (réponse vide si l'identifiant n'existe pas)"))
    @GetMapping("/retrieve-vehicule/{idVehicule}")
    public VehiculeDTO retrieveVehicule(@PathVariable("idVehicule") Long idVehicule) {
        return vehiculeService.retrieveVehicule(idVehicule);
    }

    @Operation(summary = "Ajouter un véhicule",
            description = "Enregistre un nouveau véhicule (immatriculation, marque, modèle, catégorie, tarif journalier, statut et agence obligatoires).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Véhicule créé, avec son identifiant généré"),
                    @ApiResponse(responseCode = "400", description = "Corps de la requête invalide")
            })
    @PostMapping("/add-vehicule")
    public VehiculeDTO addVehicule(@Valid @RequestBody VehiculeDTO vehicule) {
        return vehiculeService.addVehicule(vehicule);
    }

    @Operation(summary = "Ajouter plusieurs véhicules",
            description = "Enregistre une liste de véhicules en une seule requête.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Véhicules créés"),
                    @ApiResponse(responseCode = "400", description = "Corps de la requête invalide")
            })
    @PostMapping("/add-vehicules")
    public List<VehiculeDTO> addVehicules(@Valid @RequestBody List<VehiculeDTO> vehicules) {
        return vehiculeService.addVehicules(vehicules);
    }

    @Operation(summary = "Modifier un véhicule",
            description = "Met à jour le véhicule dont l'identifiant (idVehicule) est fourni dans le corps.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Véhicule modifié"),
                    @ApiResponse(responseCode = "400", description = "Corps de la requête invalide")
            })
    @PutMapping("/update-vehicule")
    public VehiculeDTO updateVehicule(@Valid @RequestBody VehiculeDTO vehicule) {
        return vehiculeService.updateVehicule(vehicule);
    }

    @Operation(summary = "Supprimer un véhicule",
            description = "Supprime le véhicule correspondant à l'identifiant fourni.",
            responses = @ApiResponse(responseCode = "200", description = "Véhicule supprimé"))
    @DeleteMapping("/remove-vehicule/{idVehicule}")
    public void removeVehicule(@PathVariable("idVehicule") Long idVehicule) {
        vehiculeService.removeVehicule(idVehicule);
    }

    @Operation(summary = "Affecter un équipement à un véhicule",
            description = "Rattache l'équipement au véhicule indiqué.")
    @PutMapping("/affecter-equipement/{idVehicule}/{idEquipement}")
    public VehiculeDTO affecterVehiculeAEquipement(@PathVariable("idVehicule") Long idVehicule,
                                                   @PathVariable("idEquipement") Long idEquipement) {
        return vehiculeService.affecterVehiculeAEquipement(idVehicule, idEquipement);
    }

    @Operation(summary = "Affecter une maintenance à un véhicule",
            description = "Crée une maintenance du jour pour le véhicule identifié par son immatriculation.")
    @PostMapping("/affecter-maintenance")
    public VehiculeDTO affecterMaintenanceAvehicule(
            @RequestParam("description") @NotBlank(message = "La description est obligatoire") String description,
            @RequestParam("immatriculation") @NotBlank(message = "L'immatriculation est obligatoire") String immatriculation) {
        return vehiculeService.affecterMaintenanceAvehicule(description, immatriculation);
    }
}