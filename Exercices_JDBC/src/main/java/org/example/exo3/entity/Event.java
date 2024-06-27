package org.example.exo3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id_event;
    String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    Address address;
    LocalDate date;
    LocalTime time;
    int numberOfPlace;
    int remainingPlace;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    List<Ticket> tickets;

    public Event(String name, Address address, LocalDate date, LocalTime time, int numberOfPlace) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.time = time;
        this.numberOfPlace = numberOfPlace;
        this.remainingPlace = numberOfPlace;
    }
}
