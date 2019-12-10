package main;

import main.java.model.Course;
import main.java.model.Student;
import main.java.service.CourseService;
import main.java.service.StudentService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class JPAExample {

    // Create an Entity Manager Factory for MongoDB
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY_MongoDB = Persistence
            .createEntityManagerFactory("ogm-mongodb");

    // Create an Entity Manager Factory for MySQL
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY_MySQL = Persistence
            .createEntityManagerFactory("mysql");

    public static void main(String[] args) throws IOException {

        //----------------creating data in MongoDb
        CourseService courseService = new CourseService(ENTITY_MANAGER_FACTORY_MongoDB);
        StudentService studentService = new StudentService(ENTITY_MANAGER_FACTORY_MongoDB);
        createClass(studentService, courseService);

        System.out.println("-------------------------------------");
        System.out.println("THIS DATA IS FROM MongoDB!!");
        courseService.printAllCourses();

        courseService.close();
        studentService.close();

        //--------------creating data in MySQL
        courseService = new CourseService(ENTITY_MANAGER_FACTORY_MySQL);
        studentService = new StudentService(ENTITY_MANAGER_FACTORY_MySQL);

        createClass(studentService, courseService);

        System.out.println("-------------------------------------");
        System.out.println("THIS DATA IS FROM MySQL!!");
        courseService.printAllCourses();

        courseService.close();
        studentService.close();

        ENTITY_MANAGER_FACTORY_MySQL.close();
        ENTITY_MANAGER_FACTORY_MongoDB.close();
    }

    private static void createClass(StudentService studentService, CourseService courseService) {

        //create 2 students
        Student eynan = studentService.createNewStudent("ID:12345678", "StudentId:987654", "Eynan", "Drori", 34);
        Student moshe = studentService.createNewStudent("ID:12345677", "StudentId:987653", "Moshe", "Levi", 21);
        Student ali = studentService.createNewStudent("ID:12345676", "StudentId:987655", "Ali", "Cohen", 45);
        studentService.addStudent(eynan);
        studentService.addStudent(moshe);
        studentService.addStudent(ali);

        //create a course
        Course course = courseService.createNewCourse("CourseId:1234", "this is Java Advanced!", 4);
        courseService.addCourse(course);
        studentService.associateStudentToCourse(moshe, course);
        courseService.merge(course);

        //create a course
        Course perlCourse = courseService.createNewCourse("CourseId:1235", "this is Perl Advanced!", 4);
        courseService.addCourse(perlCourse);
        studentService.associateStudentToCourse(eynan, perlCourse);
        courseService.merge(perlCourse);
        studentService.associateStudentToCourse(ali, perlCourse);
        courseService.merge(perlCourse);

        //create a new student and associate to 2 courses
        Student newStudent =  studentService.createNewStudent("ID:42345671", "StudentId:987350", "Miri", "Cohen", 26);
        studentService.addStudent(newStudent);
        studentService.associateStudentToCourse(newStudent,courseService.getAllCourses().stream().filter(courseA -> courseA.getCourseId().equals("CourseId:1235")).findFirst().get());
        courseService.merge(courseService.getAllCourses().stream().filter(courseA -> courseA.getCourseId().equals("CourseId:1235")).findFirst().get());
        studentService.associateStudentToCourse(newStudent,courseService.getAllCourses().stream().filter(courseA -> courseA.getCourseId().equals("CourseId:1234")).findFirst().get());
        courseService.merge(courseService.getAllCourses().stream().filter(courseA -> courseA.getCourseId().equals("CourseId:1234")).findFirst().get());
    }
}
