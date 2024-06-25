package org.example.exo1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Etudiant {
    int id;
    String nom;
    String prenom;
    int numeroDeClasse;
    LocalDate dateDiplome;
}
