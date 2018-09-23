//package com.example.recipesapp.configuration;
//
//import org.hibernate.HibernateException;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//public class HibernateUtil {
//
//    private static SessionFactory sessionFactory;
//
//    public static SessionFactory getSessionFactory() {
//        if (null != sessionFactory)
//            return sessionFactory;
//
//        Configuration configuration = new Configuration();
//
//        String jdbcUrl = "jdbc:postgresql://localhost:5432/dictionary";
//
//        configuration.setProperty("hibernate.connection.url", jdbcUrl);
//        configuration.setProperty("hibernate.connection.username", "agalkiewicz");
////        configuration.setProperty("hibernate.connection.password", System.getenv("RDS_PASSWORD"));
//        configuration.setProperty("hibernate.id.new_generator_mappings", "false");
//        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
//        configuration.setProperty("hibernate.dialect", "com.example.recipesapp.fts.PgFullTextDialect");
//        configuration.setProperty("show_sql", "true");
//
//        try {
//
//            sessionFactory = configuration.buildSessionFactory();
//        } catch (HibernateException e) {
//            System.err.println("Initial SessionFactory creation failed." + e);
//            throw new ExceptionInInitializerError(e);
//        }
//        return sessionFactory;
//    }
//}