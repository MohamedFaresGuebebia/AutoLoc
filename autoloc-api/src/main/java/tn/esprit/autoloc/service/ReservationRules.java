package tn.esprit.autoloc.service;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.BusinessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class ReservationRules {

    public void verifierPeriode(LocalDate dateDebut, LocalDate dateFin) {
        if (!dateFin.isAfter(dateDebut)) {
            throw new BusinessException("La date de fin doit être postérieure à la date de début");
        }
    }

    public BigDecimal calculerMontant(Vehicule vehicule, LocalDate dateDebut, LocalDate dateFin) {
        long jours = ChronoUnit.DAYS.between(dateDebut, dateFin);
        return vehicule.getTarifJournalier().multiply(BigDecimal.valueOf(jours));
    }
}