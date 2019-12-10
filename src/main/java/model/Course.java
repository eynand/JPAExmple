package main.java.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course implements Serializable {

    @Id
    private String courseId;
    @Column
    private String title;
    @Column
    private int points;
    @ManyToMany(mappedBy = "courses", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        Course c = (Course) o;
        return ((Course) o).courseId.equals(this.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.courseId);
    }
}
