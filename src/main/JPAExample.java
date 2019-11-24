package main;

import main.java.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import java.util.UUID;

public class JPAExample {

    // Create an EntityManagerFactory when you start the application.
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY_MongoDB = Persistence
            .createEntityManagerFactory("ogm-mongodb");

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY_MySQL = Persistence
            .createEntityManagerFactory("mysql");

    public static void main(String[] args) throws SystemException, NotSupportedException {

        EntityManager managerMongoDB = ENTITY_MANAGER_FACTORY_MongoDB.createEntityManager();
        EntityManager managerMySQL = ENTITY_MANAGER_FACTORY_MySQL.createEntityManager();

        //creating the person
        Person person = new Person();
        person.setAge(34);
        person.setId(UUID.randomUUID().toString());
        person.setFirstName("Eynan");
        person.setLastName("Drori");

        //persist to MySQL
        managerMySQL.getTransaction().begin();
        managerMySQL.persist(person);
        managerMySQL.getTransaction().commit();


        //persist to mongoDB
        managerMongoDB.getTransaction().begin();
        managerMongoDB.persist(person);
        managerMongoDB.getTransaction().commit();


        managerMySQL.close();
        ENTITY_MANAGER_FACTORY_MySQL.close();

        managerMongoDB.close();
        ENTITY_MANAGER_FACTORY_MongoDB.close();
    }




}
