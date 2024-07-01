package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_article;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private LocalDateTime restockDate;

    @OneToMany(mappedBy = "article")
    private List<SaleLine> saleLines;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.restockDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "id=" + this.id_article +
                ", name='" + this.name + '\'' +
                ", description='" + this.description + '\'' +
                ", price=" + this.price +
                ", quantity=" + this.quantity +
                ", restockDate=" + this.restockDate;
    }
}
