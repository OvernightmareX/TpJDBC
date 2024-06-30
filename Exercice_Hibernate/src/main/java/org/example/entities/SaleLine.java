package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SaleLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_saleLine;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "id_sale")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

    @Override
    public String toString() {
        return article.getId_article() +
                " - " + article.getName() +
                ", " + article.getPrice() +
                ", " + quantity +
                ", total : " + quantity * article.getPrice();
    }
}
