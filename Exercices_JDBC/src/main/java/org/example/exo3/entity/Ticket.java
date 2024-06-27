package org.example.exo3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.exo3.constants.TypePlace;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int placeNum;
    @ManyToOne
    @JoinColumn(name = "id_client")
    Client client;
    @ManyToOne
    @JoinColumn(name = "id_event")
    Event event;
    TypePlace typePlace;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", placeNum=" + placeNum +
                ", typePlace=" + typePlace +
                ", client=" + client.getName() +
                ", client=" + client.getLastName() +
                ", event=" + event.getName() +
                ", event=" + event.getDate() +
                ", event=" + event.getTime() +
                '}';
    }
}
