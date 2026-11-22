package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.exception.BusinessException;
import tn.esprit.autoloc.mapper.ContratMapper;
import tn.esprit.autoloc.repository.ContratRepository;
import tn.esprit.autoloc.repository.ReservationRepository;
import tn.esprit.autoloc.web.dto.ContratDTO;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ContratService implements IContratService {

    private final ContratRepository contratRepository;
    private final ReservationRepository reservationRepository;
    private final ContratMapper contratMapper;
    private final ReservationRules reservationRules;

    @Override
    @Transactional
    public ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
        reservationRules.verifierPeriode(dateDebut, dateFin);
        List<Reservation> reservations = reservationRepository.findByDateDebutAndDateFin(dateDebut, dateFin)
                .stream()
                .filter(reservation -> reservation.getContrat() == null)
                .toList();
        if (reservations.isEmpty()) {
            throw new EntityNotFoundException("Aucune réservation sans contrat du " + dateDebut + " au " + dateFin);
        }
        if (reservations.size() > 1) {
            throw new BusinessException("Plusieurs réservations sans contrat correspondent à cette période");
        }
        Reservation reservation = reservations.get(0);

        Contrat contrat = new Contrat();
        contrat.setDateSignature(dateSignature);
        contrat.setMontantTotal(reservationRules.calculerMontant(reservation.getVehicule(), dateDebut, dateFin));
        contrat.setValide(true);
        contrat.setReservation(reservation);
        reservation.setContrat(contrat);
        reservation.setStatut(StatutReservation.CONFIRMEE);
        return contratMapper.toDTO(contratRepository.save(contrat));
    }
}