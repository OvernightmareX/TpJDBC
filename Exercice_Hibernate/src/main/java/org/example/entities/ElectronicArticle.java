package org.example.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class ElectronicArticle extends Article{
    int batteryDuration;

    @Override
    public String toString() {
        return "ElectronicArticle{" +
                super.toString() +
                ", batteryDuration=" + batteryDuration +
                '}';
    }
}
