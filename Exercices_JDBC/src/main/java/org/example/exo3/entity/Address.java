package org.example.exo3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id_address;
    String road;
    String town;
    @OneToOne(mappedBy = "address")
    Client client;
    @OneToOne(mappedBy = "address")
    Event event;

    @Override
    public String toString() {
        return "Address{" +
                "id_address=" + id_address +
                ", rue='" + road + '\'' +
                ", ville='" + town + '\'' +
                '}';
    }
}
