package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.web.dto.ReservationWithContratRequestDTO;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final IReservationService reservationService;

    @Operation(summary = "Affecter une réservation à un client",
            description = "Rattache la réservation au client identifié par son nom et son prénom.")
    @PutMapping("/affecter-reservation-client/{idReservation}")
    public ReservationResponseDTO affecterReservationAClient(
            @PathVariable("idReservation") Long idReservation,
            @RequestParam("nom") @NotBlank(message = "Le nom est obligatoire") String nom,
            @RequestParam("prenom") @NotBlank(message = "Le prénom est obligatoire") String prenom) {
        return reservationService.affecterReservationAClient(idReservation, nom, prenom);
    }

    @Operation(summary = "Créer une réservation et son contrat",
            description = "Crée la réservation et son contrat en une seule opération ; le montant est calculé (jours × tarif journalier).")
    @PostMapping("/creer-reservation-contrat")
    public ReservationResponseDTO creerReservationEtContratAssocie(
            @Valid @RequestBody ReservationWithContratRequestDTO requestDTO) {
        return reservationService.creerReservationEtContratAssocie(requestDTO);
    }
}