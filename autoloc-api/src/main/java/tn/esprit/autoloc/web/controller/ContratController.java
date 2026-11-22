package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.web.dto.ContratDTO;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/contrat")
public class ContratController {

    private final IContratService contratService;

    @Operation(summary = "Affecter un contrat à une réservation",
            description = "Crée le contrat de la réservation sans contrat correspondant à la période donnée ; le montant est calculé (jours × tarif journalier).")
    @PostMapping("/affecter-contrat-reservation")
    public ContratDTO affecterContratAReservation(
            @RequestParam("dateSignature") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateSignature,
            @RequestParam("dateDebut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam("dateFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return contratService.affecterContratAReservation(dateSignature, dateDebut, dateFin);
    }
}