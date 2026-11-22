package tn.esprit.autoloc.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;

@Component
@AllArgsConstructor
public class ReservationMapper {

    private final ContratMapper contratMapper;

    public ReservationResponseDTO toResponseDTO(Reservation reservation) {
        Client client = reservation.getClient();
        Contrat contrat = reservation.getContrat();
        return new ReservationResponseDTO(
                reservation.getIdReservation(),
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getStatut(),
                client.getIdClient(),
                client.getNom(),
                client.getPrenom(),
                reservation.getVehicule().getIdVehicule(),
                contrat == null ? null : contratMapper.toDTO(contrat));
    }
}