package main.java.service;

import main.java.model.Course;
import main.java.model.Student;
import main.java.repository.CourseRepository;

import javax.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.List;

public class CourseService {

    CourseRepository repository;

    public CourseService(EntityManagerFactory entityManagersFactory) {
        repository = new CourseRepository(entityManagersFactory);
    }

    public void addCourse(Course course) {
        if (repository.findById(course.getCourseId()) == null) {
            repository.newCourse(course);
        }
    }

    public List<Course> getAllCourses() {
        return repository.getAllCourses();
    }

    public Course createNewCourse(String CourseId, String title, int points) {
        Course course = new Course();
        course.setCourseId(CourseId);
        course.setPoints(points);
        course.setTitle(title);
        course.setStudents(new HashSet<>());
        return course;
    }

    public void printAllCourses() {
        List<Course> courses = getAllCourses();
        System.out.println("Courses:");
        for (Course courseins : courses) {
            System.out.println("Title:" + courseins.getTitle() + " Points:" + courseins.getPoints());
            System.out.println("Students:");
            for (Student studentins : courseins.getStudents()) {
                System.out.println("    " + studentins.getFirstName() + " " + studentins.getLastName());
            }
        }
    }

    public void merge(Course course) {
        repository.merge(course);
    }

    public void close() {
        repository.close();
    }
}
