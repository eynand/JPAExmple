package main.java.repository;

import main.java.model.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Closeable;
import java.util.List;

public class StudentRepository implements Closeable {

    EntityManagerFactory entityManagersFactory;
    EntityManager manager;

    public StudentRepository(EntityManagerFactory entityManagersFactory) {
        this.entityManagersFactory = entityManagersFactory;
        manager = entityManagersFactory.createEntityManager();
    }

    public Student findById(String id) {
        return manager.find(Student.class, id);
    }

    public void newStudent(Student student) {
        manager = entityManagersFactory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(student);
        manager.getTransaction().commit();
    }

    public void merge(Student student) {
        manager.getTransaction().begin();
        manager.merge(student);
        manager.getTransaction().commit();
    }

    public List<Student> getAllStudents() {
        return manager.createNamedQuery("getAllStudents").getResultList();
    }

    public Student getStudentById(String studentId) {
        return (Student) manager.createNamedQuery("getStudentById").setParameter("studentId",studentId).getSingleResult();
    }

    @Override
    public void close() {
        manager.close();
    }
}
