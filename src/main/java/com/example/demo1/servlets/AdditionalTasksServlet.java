package com.example.demo1.servlets;


import com.example.demo1.converters.ClassToJsonConverter;
import com.example.demo1.dto.CountDTO;
import com.example.demo1.dto.dtoList.PersonDTOList;
import com.example.demo1.mapper.PersonMapper;
import com.example.demo1.model.Movie;
import com.example.demo1.model.Person;
import com.example.demo1.model.enums.MovieGenre;
import com.example.demo1.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/additional")
public class AdditionalTasksServlet extends HttpServlet {
    private Session session;
    private EntityManager em;
    private ClassToJsonConverter classToJson;
    private PersonMapper personMapper;

    @Override
    public void init() throws ServletException {
        session = HibernateUtil.getSessionFactory().openSession();
        em = session.getEntityManagerFactory().createEntityManager();
        classToJson = new ClassToJsonConverter();
        personMapper = new PersonMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String genre = request.getParameter("genre");
        String director = request.getParameter("director");
        String oscarsCount = request.getParameter("oscarsCount");

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> from = criteriaQuery.from(Movie.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Movie.class)));
        em.createQuery(countQuery);

        if (genre != null) {
            countQuery.where(criteriaBuilder.equal(from.get("genre"), MovieGenre.valueOf(genre)));
            Long countResult = em.createQuery(countQuery).getSingleResult();
            CountDTO countDTO = new CountDTO();
            countDTO.setCount(countResult);
            response.getWriter().write(classToJson.countToJSON(countDTO));
            return;
        }
        if (director != null) {
//            javax.persistence.Query singleQuery = em.createQuery("SELECT c FROM Person c WHERE c.id = ?1", Person.class);
//            Person person = (Person) singleQuery.setParameter(1, Integer.parseInt(director)).getSingleResult();
            CriteriaQuery<Person> criteriaQueryPerson = criteriaBuilder.createQuery(Person.class);
            Root<Person> root = criteriaQueryPerson.from(Person.class);
//            criteriaQueryPerson.select(root).where(criteriaBuilder.lessThan(root.get("name"), person.getName()));
            criteriaQueryPerson.select(root).where(criteriaBuilder.lessThan(root.get("name"), director));

            Query<Person> query = session.createQuery(criteriaQueryPerson);
            List<Person> personList = query.getResultList();
            PersonDTOList dto = new PersonDTOList(new ArrayList<>());
            dto.setPersonsList(personMapper.mapPersonListToPersonDTOList(personList));
            response.getWriter().write(classToJson.personsToJSON(dto));
            return;
        }

        if (oscarsCount != null) {
           countQuery.where(criteriaBuilder.greaterThanOrEqualTo(from.get("oscarsCount"), Integer.parseInt(oscarsCount)));
            Long countResult = em.createQuery(countQuery).getSingleResult();
            CountDTO countDTO = new CountDTO();
            countDTO.setCount(countResult);
            response.getWriter().write(classToJson.countToJSON(countDTO));
            return;
        }
    }
}
