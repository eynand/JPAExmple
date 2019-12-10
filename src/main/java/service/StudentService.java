package main.java.service;

import main.java.model.Course;
import main.java.model.Student;
import main.java.repository.StudentRepository;

import javax.persistence.EntityManagerFactory;

public class StudentService {

    StudentRepository repository;

    public StudentService(EntityManagerFactory entityManagersFactory) {
        repository = new StudentRepository(entityManagersFactory);
    }

    public void addStudent(Student student) {
        if (repository.findById(student.getId()) == null) {
            repository.newStudent(student);
        }
    }

    public Student createNewStudent(String id, String studentId, String firstName, String lastName, int age) {
        Student student = new Student();
        student.setAge(age);
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setStudentId(studentId);
        return student;
    }

    public void associateStudentToCourse(Student student, Course course) {
        student.getCourses().add(course);
        course.getStudents().add(student);
        repository.merge(student);
    }

    public void close() {
        repository.close();
    }
}
