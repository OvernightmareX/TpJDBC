package org.example.repositories;

import jakarta.persistence.Query;
import org.example.entities.Sale;
import org.hibernate.Session;

import java.util.List;

public class SaleRepository extends BaseRepository<Sale> {
    public List<Sale> findAllSaleOrderByDate(){
        String queryString = "SELECT s FROM Sale s ORDER BY s.saleDate";

        try(Session session = sessionFactory.openSession()){
            Query query = session.createQuery(queryString, Sale.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
