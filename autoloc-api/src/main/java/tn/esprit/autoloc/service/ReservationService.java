package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.BusinessException;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.repository.ClientRepository;
import tn.esprit.autoloc.repository.ReservationRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.web.dto.ReservationWithContratRequestDTO;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final VehiculeRepository vehiculeRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationRules reservationRules;

    @Override
    @Transactional
    public ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new EntityNotFoundException("Réservation introuvable : " + idReservation));
        reservation.setClient(findClientByNomEtPrenom(nom, prenom));
        return reservationMapper.toResponseDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO) {
        reservationRules.verifierPeriode(requestDTO.dateDebut(), requestDTO.dateFin());
        Client client = clientRepository.findById(requestDTO.idClient())
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable : " + requestDTO.idClient()));
        Vehicule vehicule = vehiculeRepository.findById(requestDTO.idVehicule())
                .orElseThrow(() -> new EntityNotFoundException("Véhicule introuvable : " + requestDTO.idVehicule()));

        Reservation reservation = new Reservation();
        reservation.setDateDebut(requestDTO.dateDebut());
        reservation.setDateFin(requestDTO.dateFin());
        reservation.setStatut(StatutReservation.CONFIRMEE);
        reservation.setClient(client);
        reservation.setVehicule(vehicule);

        Contrat contrat = new Contrat();
        contrat.setDateSignature(requestDTO.dateSignature());
        contrat.setMontantTotal(
                reservationRules.calculerMontant(vehicule, requestDTO.dateDebut(), requestDTO.dateFin()));
        contrat.setValide(true);
        contrat.setReservation(reservation);
        reservation.setContrat(contrat);

        return reservationMapper.toResponseDTO(reservationRepository.save(reservation));
    }

    private Client findClientByNomEtPrenom(String nom, String prenom) {
        List<Client> clients = clientRepository.findByNomAndPrenom(nom, prenom);
        if (clients.isEmpty()) {
            throw new EntityNotFoundException("Client introuvable : " + nom + " " + prenom);
        }
        if (clients.size() > 1) {
            throw new BusinessException("Plusieurs clients portent le nom " + nom + " " + prenom);
        }
        return clients.get(0);
    }
}