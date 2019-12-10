package main.java.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends Person implements Serializable {

    @Column
    private String studentId;
    @Column
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "students_courses", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "courseId"))
    private Set<Course> courses = new HashSet<>();


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        Student c = (Student) o;
        return c.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
