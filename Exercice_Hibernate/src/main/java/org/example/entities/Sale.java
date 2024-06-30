package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.utils.TypeSale;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_sale;
    private LocalDate saleDate;
    private TypeSale typeSale;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER)
    private List<SaleLine> saleLines;

    @Override
    public String toString() {
        StringBuilder saleString = new StringBuilder("Sale n°" + id_sale +
                " - saleDate=" + saleDate +
                ", typeSale=" + typeSale +
                ", customer=" + customer.getName() +
                ", articles :" + "\n");

        for (SaleLine saleLine : saleLines){
            saleString.append(saleLine).append("\n");
        }

        return saleString.toString();
    }
}
