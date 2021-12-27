package com.example.demo1.repository;

import com.example.demo1.dto.PagedMovieList;
import com.example.demo1.exceptions.EntityIsNotValidException;
import com.example.demo1.exceptions.IncorrectParametersException;
import com.example.demo1.model.Movie;
import com.example.demo1.model.enums.MovieGenre;
import com.example.demo1.model.enums.MpaaRating;
import com.example.demo1.utils.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo1.utils.UrlParamsUtil.parseInteger;


public class MovieRepository {
    private Session session;
    private EntityManager em;

    public MovieRepository() {
        session = HibernateUtil.getSessionFactory().openSession();
        em = session.getEntityManagerFactory().createEntityManager();
    }

    private boolean existsById(Integer id) {
        org.hibernate.query.Query query = session.createQuery("SELECT 1 FROM Movie l WHERE l.id = ?1");
        query.setParameter(1, id);
        return (query.uniqueResult() != null);
    }


    public Movie findById(Integer id) {
        if (existsById(id)) {
            Query query = em.createQuery("SELECT c FROM Movie c WHERE c.id = ?1", Movie.class);
            return (Movie) query.setParameter(1, id).getSingleResult();
        } else
            throw new NoResultException("movie with id = " + id + " does not exist");
    }

    public PagedMovieList findAll(String perPage, String curPage, String sortBy, String filterBy) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> from = criteriaQuery.from(Movie.class);
        CriteriaQuery<Movie> select = criteriaQuery.select(from);

        List<Order> orderList = getOrderList(sortBy, criteriaBuilder, from);
        if (!orderList.isEmpty())
            criteriaQuery.orderBy(orderList);

        ArrayList<Predicate> predicates = getPredicatesList(filterBy, criteriaBuilder, from);
        if (!predicates.isEmpty())
            select.where(predicates.toArray(new Predicate[]{}));

        PagedMovieList pagedMovieList = new PagedMovieList();

        pagedMovieList.setCount(getOverallCount(criteriaBuilder, predicates));
        pagedMovieList.setMovieList(findAll(perPage, curPage, select));

        return pagedMovieList;

    }

    public void clearEntityManager() {
        em.clear();
    }

    private List<Movie> findAll(String perPage, String curPage, CriteriaQuery<Movie> select) {
        if (perPage != null && curPage != null) {
            int pageNumber = parseInteger(curPage);
            int pageSize = parseInteger(perPage);
            TypedQuery<Movie> typedQuery = em.createQuery(select);
            typedQuery.setFirstResult((pageNumber - 1) * pageSize);
            typedQuery.setMaxResults(pageSize);
            return typedQuery.getResultList();
        } else
            return findAll(select);
    }

    private List<Movie> findAll(CriteriaQuery<Movie> select) {
        TypedQuery<Movie> typedQuery = em.createQuery(select);
        return typedQuery.getResultList();
    }

    private Long getOverallCount(CriteriaBuilder criteriaBuilder, ArrayList<Predicate> predicates) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Movie.class)));
        em.createQuery(countQuery);
        if (predicates.size() > 0)
            countQuery.where(predicates.toArray(new Predicate[]{}));
        return em.createQuery(countQuery).getSingleResult();

    }

    private ArrayList<Predicate> getPredicatesList(String filterBy, CriteriaBuilder criteriaBuilder, Root<Movie> from) {
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (filterBy != null) {
            List<String> notParsedFilters = new ArrayList<>(Arrays.asList(filterBy.split(";")));
            for (String filterString : notParsedFilters) {
                List<String> filter = new ArrayList<>(Arrays.asList(filterString.split(",")));
                switch (filter.get(0)) {
                    case ("id"):
                        if (filter.size() < 3)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("id"), Integer.parseInt(filter.get(1))));
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("id"), Integer.parseInt(filter.get(2))));
                        break;
                    case ("name"):
                        if (filter.size() < 2)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(from.get("name")),
                                filter.get(1).toUpperCase() + "%"));
                        break;
                    case ("oscar"):
                        if (filter.size() < 3)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("oscarsCount"), Integer.parseInt(filter.get(1))));
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("oscarsCount"), Integer.parseInt(filter.get(2))));
                        break;
                    case ("genre"):
                        if (filter.size() < 2)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.equal(from.get("genre"), MovieGenre.valueOf(filter.get(1))));
                        break;
                    case ("rating"):
                        if (filter.size() < 2)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.equal(from.get("mpaaRating"), MpaaRating.valueOf(filter.get(1))));
                        break;
                    case ("date"):
                        if (filter.size() < 3)
                            throw new EntityIsNotValidException("number of arguments less than required");

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate date = LocalDate.parse(filter.get(1), formatter);
                        ZonedDateTime dateTime = date.atStartOfDay(ZoneId.systemDefault());

                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("creationDate"), dateTime));

                        LocalDate date1 = LocalDate.parse(filter.get(2), formatter);
                        ZonedDateTime dateTime1 = date1.atStartOfDay(ZoneId.systemDefault());

                        predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("creationDate"), dateTime1));
                        break;
                    case ("coordinate"):
                        if (filter.size() < 5)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        Predicate x = criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(from.get("coordinates").get("x"), Double.parseDouble(filter.get(1))),
                                criteriaBuilder.lessThanOrEqualTo(from.get("coordinates").get("x"), Double.parseDouble(filter.get(2))));
                        Predicate y = criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(from.get("coordinates").get("y"), Double.parseDouble(filter.get(3))),
                                criteriaBuilder.lessThanOrEqualTo(from.get("coordinates").get("y"), Double.parseDouble(filter.get(4))));
                        predicates.add(criteriaBuilder.and(x, y));
                        break;
                    case ("director"):
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(from.get("screenWriter").get("name")),
                                filter.get(1).toUpperCase() + "%"));
                        break;

                    default:
                        throw new IncorrectParametersException("Incorrect sort parameter");

                }
            }
        }

        return predicates;
    }

    private List<Order> getOrderList(String sortBy, CriteriaBuilder criteriaBuilder, Root<Movie> from) {
        List<Order> orderList = new ArrayList();
        if (sortBy != null) {
            List<String> criteria = new ArrayList<>(Arrays.asList(sortBy.split(";")));
            for (String criterion : criteria) {
                switch (criterion) {
                    case ("id"):
                        orderList.add(criteriaBuilder.asc(from.get("id")));
                        break;
                    case ("name"):
                        orderList.add(criteriaBuilder.asc(from.get("name")));
                        break;

                    case ("oscar"):
                        orderList.add(criteriaBuilder.asc(from.get("oscarsCount")));
                        break;
                    case ("genre"):
                        orderList.add(criteriaBuilder.asc(from.get("genre")));
                        break;
                    case ("rating"):
                        orderList.add(criteriaBuilder.asc(from.get("mpaaRating")));
                        break;
                    case ("date"):
                        orderList.add(criteriaBuilder.asc(from.get("creationDate")));
                        break;
                    case ("coordinate"):
                        orderList.add(criteriaBuilder.asc(from.get("coordinates").get("x")));
                        break;
                    case ("director"):
                        orderList.add(criteriaBuilder.asc(from.get("director").get("name")));
                        break;
                    default:
                        throw new IncorrectParametersException("Incorrect sort parameter");
                }
            }
        }
        return orderList;
    }

    public void create(Movie movie) {
        em.getTransaction().begin();
        try {
            em.persist(movie);
            em.getTransaction().commit();
            em.clear();
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.clear();
            throw new EntityIsNotValidException("Incorrect create request");
        }


    }

    public void update(Movie movie) {
        em.getTransaction().begin();
        em.merge(movie);
        em.getTransaction().commit();
        em.clear();
    }

    public void delete(Movie movie) {
        em.getTransaction().begin();
        em.remove(movie);
        em.getTransaction().commit();
        em.clear();
    }
}
