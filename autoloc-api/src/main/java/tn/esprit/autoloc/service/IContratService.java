package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.ContratDTO;

import java.time.LocalDate;

public interface IContratService {
    ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin);
}