package org.example.exo2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.exo2.Constants.RegimeType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;
    int age;
    @Enumerated
    RegimeType regimeType;
    @Temporal(TemporalType.DATE)
    LocalDate arrivedDate;

    @Override
    public String toString() {
        return this.id + " - " +
                this.name + ", " +
                this.age + " ans, " +
                this.regimeType + ", " +
                this.arrivedDate;
    }
}
