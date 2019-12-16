package main.java.repository;

import main.java.model.Course;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Closeable;
import java.util.List;

public class CourseRepository implements Closeable {

    EntityManagerFactory entityManagersFactory;
    EntityManager manager;

    public CourseRepository(EntityManagerFactory entityManagersFactory) {
        this.entityManagersFactory = entityManagersFactory;
        manager = entityManagersFactory.createEntityManager();
    }

    public Course findById(String id) {
        return manager.find(Course.class, id);
    }

    public void newCourse(Course course) {
        manager = entityManagersFactory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(course);
        manager.getTransaction().commit();
    }

    public List<Course> getAllCourses() {
        return manager.createQuery("SELECT a FROM Course a", Course.class).getResultList();
    }

    public void merge(Course course) {
        manager.getTransaction().begin();
        manager.merge(course);
        manager.getTransaction().commit();
    }

    @Override
    public void close() {
        manager.close();
    }
}
