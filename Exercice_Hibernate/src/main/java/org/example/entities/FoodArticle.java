package org.example.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class FoodArticle extends Article{
    LocalDate expirationDate;

    @Override
    public String toString() {
        return "FoodArticle{" +
                super.toString() +
                "expirationDate=" + expirationDate +
                "} " ;
    }
}
