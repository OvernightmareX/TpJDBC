package org.example.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.utils.TypeFashionArticle;

import java.time.LocalDate;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class FashionArticle extends Article{
    TypeFashionArticle type;
    int size;

    @Override
    public String toString() {
        return "FashionArticle{" +
                super.toString() +
                "type=" + type +
                ", size=" + size +
                "} " ;
    }
}
