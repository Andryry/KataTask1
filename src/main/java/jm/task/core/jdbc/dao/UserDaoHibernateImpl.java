package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.*;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory factory;

    static {
        try {
            Properties properties = new Properties();
            properties.put(Environment.URL, "jdbc:mysql://localhost:3306/test_db_one");
            properties.put(Environment.USER, "jpauser");
            properties.put(Environment.PASS, "jpapwd");
            properties.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");

            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.FORMAT_SQL, "true");
            properties.put(Environment.HBM2DDL_AUTO, "update");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            Configuration configuration = new Configuration();
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            factory = configuration.buildSessionFactory(serviceRegistry);
        }
        catch (Throwable e){
            throw new ExceptionInInitializerError(e);
        }
    }


    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255), " +
                "lastName VARCHAR(255), " +
                "age TINYINT)";
        try (Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        }
        catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String sqlQuery = "Drop TABLE IF EXISTS Users";
        try (Session session = factory.openSession()){
            transaction = session.beginTransaction();

            session.createSQLQuery(sqlQuery).executeUpdate();

            transaction.commit();
        }
        catch (Exception e) {
            if (transaction!=null&&transaction.isActive()){
                transaction.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name,lastName,age);

        try (Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }
        catch (Exception e){
            if (transaction!= null&& transaction.isActive()) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
Transaction transaction = null;
User user = null;

try (Session session = factory.openSession()) {
    transaction = session.beginTransaction();

    user = session.get(User.class,id);
    if (user!=null) {
        session.remove(user);
    }
    transaction.commit();
}
catch (Exception e){
    if (transaction!= null && transaction.isActive()) {
        transaction.rollback();
        e.printStackTrace();
    }
}
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            List <User> userList = session.createQuery("from jm.task.core.jdbc.model.User", User.class).getResultList();
            transaction.commit();
            return userList;
        }
        catch (Exception e) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        e.printStackTrace();}

        return Collections.emptyList();
    }

    @Override
    public void cleanUsersTable() {

        Transaction transaction = null;
        try (Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();
        }
        catch (Exception e){
            if (transaction!=null&& transaction.isActive()){
                transaction.rollback();
                e.printStackTrace();
            }
        }



    }
}
