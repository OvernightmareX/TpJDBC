package org.example.repositories;

import jakarta.persistence.Query;
import org.example.entities.Sale;
import org.example.entities.SaleLine;
import org.hibernate.Session;

import java.util.List;

public class SaleLineRepository extends BaseRepository<SaleLine> {
    public List<Sale> findAllSaleByArticleID(int article_id){
        String queryString = "SELECT sl.sale FROM SaleLine sl WHERE sl.article.id_article = :articleId";

        try(Session session = sessionFactory.openSession()){
            Query query = session.createQuery(queryString, Sale.class);
            query.setParameter("articleId", article_id);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
