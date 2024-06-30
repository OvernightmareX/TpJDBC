package org.example.repositories;

import jakarta.persistence.Query;
import org.example.entities.Customer;
import org.example.entities.Sale;
import org.hibernate.Session;

import java.util.List;

public class CustomerRepository extends BaseRepository<Customer>{

    public List<Sale> findAllSalesByCustomerID(int customer_id){
        String queryString = "SELECT c.purchasingHistory FROM Customer c WHERE c.id_customer = :customerID";

        try(Session session = sessionFactory.openSession()){
            Query query = session.createQuery(queryString, Sale.class);
            query.setParameter("customerID", customer_id);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
