package org.example.exo3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id_client;
    String name;
    String lastName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    Address address;
    int age;
    String phoneNumber;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    List<Ticket> tickets;
}
