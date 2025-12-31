package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(
    name = "MyStudentClass.deleteModuleAssociations",
    query = "DELETE FROM MYSTUDENTCLASS_MYMODULE WHERE CLASS_ID = ?1"
)
@NamedQueries({
    @NamedQuery(
            name = "MyStudentClass.findAll",
            query = "SELECT c FROM MyStudentClass c ORDER BY c.classID"
    )
    ,
    
    @NamedQuery(
            name = "MyStudentClass.deleteByClassId",
            query = "DELETE FROM MyStudentClass c WHERE c.classID = :classID"
    )
    ,

    @NamedQuery(
            name = "MyStudentClass.findByAssignedAcademicLeaderID",
            query = "SELECT c FROM MyStudentClass c "
            + "WHERE c.assignedAcademicLeaderID = :alID "
            + "ORDER BY c.classID"
    )
    ,

    @NamedQuery(
            name = "MyStudentClass.searchByClassNameForAL",
            query = "SELECT c FROM MyStudentClass c "
            + "WHERE c.assignedAcademicLeaderID = :alID "
            + "AND LOWER(c.className) LIKE :kw "
            + "ORDER BY c.classID"
    )
    ,
    @NamedQuery(
            name = "MyStudentClass.searchModulesByLecturerWithClass",
            query = "SELECT m, c "
            + "FROM MyStudentClass c "
            + "JOIN c.modules m "
            + "WHERE m.assignedLecturerID = :lecturerId "
            + "AND (LOWER(m.moduleName) LIKE :kw OR LOWER(m.moduleCode) LIKE :kw) "
            + "ORDER BY m.moduleID, c.classID"
    )
    ,
    @NamedQuery(
            name = "MyStudentClass.unassignAcademicLeader",
            query = "UPDATE MyStudentClass c "
            + "SET c.assignedAcademicLeaderID = NULL "
            + "WHERE c.assignedAcademicLeaderID = :alID"
    )
    ,
    @NamedQuery(
            name = "MyStudentClass.findModuleIdsByClassId",
            query = "SELECT m.moduleID FROM MyStudentClass c "
            + "JOIN c.modules m "
            + "WHERE c.classID = :classID "
            + "ORDER BY m.moduleID"
    )
})

@Table(name = "MYSTUDENTCLASS")
public class MyStudentClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classID;

    @ManyToMany
    @JoinTable(
            name = "MYSTUDENTCLASS_MYMODULE",
            joinColumns = @JoinColumn(name = "CLASS_ID"),
            inverseJoinColumns = @JoinColumn(name = "MODULE_ID")
    )
    private List<MyModule> modules = new ArrayList<>();
    private String assignedAcademicLeaderID;
    private String className;
    private String semester;
    private String academicYear;

    public MyStudentClass() {
    }

    public MyStudentClass(String assignedAcademicLeaderID,
            String className,
            String semester,
            String academicYear) {
        this.assignedAcademicLeaderID = assignedAcademicLeaderID;
        this.className = className;
        this.semester = semester;
        this.academicYear = academicYear;
    }

    public Integer getClassID() {
        return classID;
    }

    public void setClassID(Integer classID) {
        this.classID = classID;
    }

    public List<MyModule> getModules() {
        return modules;
    }

    public void setModules(List<MyModule> modules) {
        this.modules = modules;
    }

    public String getAssignedAcademicLeaderID() {
        return assignedAcademicLeaderID;
    }

    public void setAssignedAcademicLeaderID(String assignedAcademicLeaderID) {
        this.assignedAcademicLeaderID = assignedAcademicLeaderID;
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

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    // ===== equals & hashCode =====
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
