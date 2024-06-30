package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_customer;
    private String name;
    private String email;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Sale> purchasingHistory;

    @Override
    public String toString() {
        return "Customer{" +
                "id_customer=" + id_customer +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
