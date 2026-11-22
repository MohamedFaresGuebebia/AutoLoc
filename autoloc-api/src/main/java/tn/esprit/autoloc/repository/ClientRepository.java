package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.autoloc.domain.Client;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {
    List<Client> findByNomAndPrenom(String nom, String prenom);
}
