package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MYSTUDENTCLASS")
public class MyStudentClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classID;

    private Integer moduleID;
    private String createdBy;
    private String className;
    private String semester;
    private String academicYear;

    public MyStudentClass() {
    }

public MyStudentClass(Integer moduleID, String createdBy,
        String className, String semester, String academicYear) {
    this.moduleID = moduleID;
    this.createdBy = createdBy;
    this.className = className;
    this.semester = semester;
    this.academicYear = academicYear;
}

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }


    public Integer getClassID() {
        return classID;
    }

    public void setClassID(Integer classID) {
        this.classID = classID;
    }

    public Integer getModuleID() {
        return moduleID;
    }

    public void setModuleID(Integer moduleID) {
        this.moduleID = moduleID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public int hashCode() {
        return (classID != null ? classID.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MyStudentClass)) {
            return false;
        }
        MyStudentClass other = (MyStudentClass) object;
        return this.classID != null && this.classID.equals(other.classID);
    }

    @Override
    public String toString() {
        return "model.MyStudentClass[classID=" + classID + "]";
    }
}
