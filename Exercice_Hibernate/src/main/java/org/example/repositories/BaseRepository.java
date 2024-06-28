package org.example.repositories;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.example.utils.SessionFactorySingleton;

import java.util.List;


public abstract class BaseRepository<T> {
    protected SessionFactory sessionFactory;
    protected Session session;

    public BaseRepository() {
        sessionFactory = SessionFactorySingleton.getSessionFactory();
    }

    public boolean save (T element){
        try{
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.saveOrUpdate(element);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            return false;
        }finally {
            session.close();
        }
    }

    public boolean delete (T element){
        try{
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(element);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            session.getTransaction().rollback();
            return false;
        }finally {
            session.close();
        }
    }

    public T findById (Class<T> classe, int id){
        session = sessionFactory.openSession();
        T element = session.get(classe,id);
        session.close();
        return element;
    }

    public List<T> findAll(Class<T> classe){
        String query = "FROM " + classe.getSimpleName();
        session = sessionFactory.openSession();
        Query typedQuery = session.createQuery(query, classe);
        List<T> list = typedQuery.getResultList();
        session.close();
        return list;
    }
}