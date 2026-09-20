# AutoLoc

Plateforme de gestion de location de véhicules multi-agences — projet réalisé dans le cadre de l'UP ASI (Architecture des Systèmes d'Information) à ESPRIT.

## Objectifs du projet

Développer une application Spring Boot permettant de gérer la location de véhicules à travers plusieurs agences : gestion du parc automobile, des réservations, des contrats de location et des utilisateurs selon leurs rôles.

## Acteurs

- **Client** : consulte les véhicules disponibles, effectue une réservation, gère son contrat de location.
- **Agent d'agence** : traite les réservations, gère les entrées/sorties de véhicules, établit les contrats.
- **Responsable d'agence** : supervise l'activité de son agence, gère le parc de véhicules et les agents.
- **Administrateur** : gère l'ensemble des agences, des comptes utilisateurs et des paramètres globaux du système.

## Stack technique

Java 17, Spring Boot, Spring Data JPA, Spring MVC, MySQL, Maven, Lombok
