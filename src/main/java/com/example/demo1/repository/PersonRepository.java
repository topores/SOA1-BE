package com.example.demo1.repository;

import com.example.demo1.exceptions.EntityIsNotValidException;
import com.example.demo1.model.Person;
import com.example.demo1.utils.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PersonRepository {
    private Session session;
    private EntityManager em;

    public PersonRepository() {
        session = HibernateUtil.getSessionFactory().openSession();
        em = session.getEntityManagerFactory().createEntityManager();
    }

    public Person findById(Integer id) {
        if (existsById(id)){
            Query query = em.createQuery("SELECT c FROM Person c WHERE c.id = ?1", Person.class);
            return (Person) query.setParameter(1, id).getSingleResult();
        }else
            throw new NoResultException("person with id = " + id + " does not exist");

    }

    private boolean existsById(Integer id) {
        org.hibernate.query.Query query = session.createQuery("SELECT 1 FROM Person l WHERE l.id = ?1");
        query.setParameter(1, id);
        return (query.uniqueResult() != null);
    }

    public List<Person> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> from = criteriaQuery.from(Person.class);
        CriteriaQuery<Person> select = criteriaQuery.select(from);
        TypedQuery<Person> typedQuery = em.createQuery(select);
        return typedQuery.getResultList();
    }

    public void create(Person person) {
        em.getTransaction().begin();
        try{
            em.persist(person);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }

        em.clear();
    }

    public void update(Person person) {
        em.getTransaction().begin();
        try{
            em.merge(person);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
        em.clear();
    }

}
