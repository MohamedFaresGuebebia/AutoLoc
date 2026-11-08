package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClientDTO(
        Long idClient,
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
        String nom,
        @NotBlank(message = "Le prénom est obligatoire")
        @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
        String prenom,
        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "L'email n'est pas valide")
        @Size(max = 100, message = "L'email ne doit pas dépasser 100 caractères")
        String email,
        @NotBlank(message = "Le téléphone est obligatoire")
        @Pattern(regexp = "\\+?[0-9]{8,15}", message = "Le téléphone doit contenir de 8 à 15 chiffres")
        String telephone,
        @NotBlank(message = "Le numéro de permis est obligatoire")
        @Size(max = 30, message = "Le numéro de permis ne doit pas dépasser 30 caractères")
        String numPermis,
        @NotNull(message = "La date d'inscription est obligatoire")
        @PastOrPresent(message = "La date d'inscription ne peut pas être dans le futur")
        LocalDate dateInscription) {
}