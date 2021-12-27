package com.example.demo1.utils;

import com.example.demo1.model.Coordinates;
import com.example.demo1.model.Location;
import com.example.demo1.model.Movie;
import com.example.demo1.model.Person;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://pg/studs");
                //settings.put(Environment.URL, "jdbc:postgresql://localhost/soa");
//                settings.put(Environment.URL, "jdbc:postgresql://localhost/studs");
                settings.put(Environment.USER, "s264471");
                settings.put(Environment.PASS, "zea739");

//                settings.put(Environment.USER, "user");
//                settings.put(Environment.PASS, "user");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Movie.class);
                configuration.addAnnotatedClass(Coordinates.class);
                configuration.addAnnotatedClass(Person.class);
                configuration.addAnnotatedClass(Location.class);


                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                return sessionFactory;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}