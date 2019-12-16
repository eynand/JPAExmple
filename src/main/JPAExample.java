package main;

import main.java.model.Course;
import main.java.model.Student;
import main.java.service.CourseService;
import main.java.service.StudentService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class JPAExample {

    // Create an Entity Manager Factory for MongoDB
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY_MongoDB = Persistence
            .createEntityManagerFactory("ogm-mongodb");

    // Create an Entity Manager Factory for MySQL
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY_MySQL = Persistence
            .createEntityManagerFactory("mysql");

    public static void main(String[] args) {

        //----------------creating data in MongoDb
        System.out.println("THIS DATA IS FROM MongoDB!!");
        createClasses(ENTITY_MANAGER_FACTORY_MongoDB);

        //--------------creating data in MySQL
        System.out.println("-------------------------------------");
        System.out.println("THIS DATA IS FROM MySQL!!");
        createClasses(ENTITY_MANAGER_FACTORY_MySQL);

        StudentService studentService = new StudentService(ENTITY_MANAGER_FACTORY_MongoDB);
        //using Named Query's to retrieve data
        List<Student> allStudents = studentService.getAllStudents();
        Student eynanFromDB = studentService.getStudentById("StudentId:987654");
        studentService.close();
        ENTITY_MANAGER_FACTORY_MySQL.close();
        ENTITY_MANAGER_FACTORY_MongoDB.close();
    }

    private static void createClasses(EntityManagerFactory entityManagerFactory) {

        //creating services with Entity Manager Factory
        CourseService courseService = new CourseService(entityManagerFactory);
        StudentService studentService = new StudentService(entityManagerFactory);

        //create 2 students
        Student eynan = studentService.createNewStudent("ID:12345678", "StudentId:987654", "Eynan", "Drori", 34);
        Student moshe = studentService.createNewStudent("ID:12345677", "StudentId:987653", "Moshe", "Levi", 21);
        Student ali = studentService.createNewStudent("ID:12345676", "StudentId:987655", "Ali", "Cohen", 45);
        studentService.addStudent(eynan);
        studentService.addStudent(moshe);
        studentService.addStudent(ali);

        //create java advanced course
        Course course = courseService.createNewCourse("CourseId:1234", "this is Java Advanced!", 4);
        courseService.addCourse(course);
        studentService.associateStudentToCourse(courseService,moshe, course);

        //create perl advanced course
        Course perlCourse = courseService.createNewCourse("CourseId:1235", "this is Perl Advanced!", 4);
        courseService.addCourse(perlCourse);
        studentService.associateStudentToCourse(courseService,eynan, perlCourse);
        studentService.associateStudentToCourse(courseService,ali, perlCourse);

        //create a new student and associate to the 2 courses
        Student newStudent =  studentService.createNewStudent("ID:42345671", "StudentId:987350", "Miri", "Cohen", 26);
        studentService.addStudent(newStudent);
        studentService.associateStudentToCourse(courseService,newStudent,courseService.getAllCourses().stream().filter(courseA -> courseA.getCourseId().equals("CourseId:1235")).findFirst().get());
        studentService.associateStudentToCourse(courseService,newStudent,courseService.getAllCourses().stream().filter(courseA -> courseA.getCourseId().equals("CourseId:1234")).findFirst().get());

        courseService.printAllCourses();

        courseService.close();
        studentService.close();
    }

}
