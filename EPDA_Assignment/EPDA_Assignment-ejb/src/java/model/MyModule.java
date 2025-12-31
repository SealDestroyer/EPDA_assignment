package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(
            name = "MyModule.findAll",
            query = "SELECT m FROM MyModule m ORDER BY m.moduleID"
    )
    ,
    @NamedQuery(
            name = "MyModule.search",
            query = "SELECT m FROM MyModule m "
            + "WHERE LOWER(m.moduleCode) LIKE :kw "
            + "   OR LOWER(m.moduleName) LIKE :kw "
            + "ORDER BY m.moduleID"
    )
    ,
    @NamedQuery(
            name = "MyModule.findByModuleCode",
            query = "SELECT m FROM MyModule m WHERE LOWER(m.moduleCode) = :code"
    )
    ,
    @NamedQuery(
            name = "MyModule.findByModuleCodeExceptId",
            query = "SELECT m FROM MyModule m "
            + "WHERE LOWER(m.moduleCode) = :code "
            + "AND m.moduleID <> :id"
    )
    ,
    @NamedQuery(
            name = "MyModule.findByLecturer",
            query = "SELECT m FROM MyModule m "
            + "WHERE m.assignedLecturerID = :lecturerID "
            + "ORDER BY m.moduleID"
    )
    ,
    @NamedQuery(
            name = "MyModule.searchByLecturer",
            query = "SELECT m FROM MyModule m "
            + "WHERE m.assignedLecturerID = :lecturerID "
            + "AND (LOWER(m.moduleCode) LIKE :kw "
            + "     OR LOWER(m.moduleName) LIKE :kw) "
            + "ORDER BY m.moduleID"
    )
    ,
@NamedQuery(
            name = "MyModule.findByCreatedBy",
            query = "SELECT m FROM MyModule m "
            + "WHERE m.createdBy = :createdBy "
            + "ORDER BY m.moduleID"
    )
    ,
@NamedQuery(
            name = "MyModule.searchByCreatedBy",
            query = "SELECT m FROM MyModule m "
            + "WHERE m.createdBy = :createdBy "
            + "AND (LOWER(m.moduleCode) LIKE :kw "
            + "     OR LOWER(m.moduleName) LIKE :kw) "
            + "ORDER BY m.moduleID"
    )
    ,
@NamedQuery(
            name = "MyModule.unassignLecturer",
            query = "UPDATE MyModule m SET m.assignedLecturerID = NULL "
            + "WHERE m.assignedLecturerID = :lecturerID"
    )
    ,
@NamedQuery(
            name = "MyModule.searchByLecturerWithClass",
            query = "SELECT m, c "
            + "FROM MyModule m "
            + "JOIN MyStudentClass c ON c.classID = m.classID "
            + "WHERE m.assignedLecturerID = :lecturerId "
            + "AND (LOWER(m.moduleName) LIKE :kw OR LOWER(m.moduleCode) LIKE :kw) "
            + "ORDER BY m.moduleID"
    )
    ,
@NamedQuery(
            name = "MyModule.findDistinctLecturerIdsByAL",
            query = "SELECT DISTINCT m.assignedLecturerID "
            + "FROM MyModule m "
            + "WHERE m.createdBy = :alID "
            + "AND m.assignedLecturerID IS NOT NULL "
            + "ORDER BY m.assignedLecturerID"
    )
    ,
@NamedQuery(
            name = "MyStudentClass.findModulesByLecturerWithClass",
            query = "SELECT m, c "
            + "FROM MyStudentClass c "
            + "JOIN c.modules m "
            + "WHERE m.assignedLecturerID = :lecturerId "
            + "ORDER BY m.moduleID, c.classID"
    )
    ,
@NamedQuery(
            name = "MyModule.findByALAndLecturer",
            query = "SELECT m FROM MyModule m "
            + "WHERE m.createdBy = :alID "
            + "AND m.assignedLecturerID = :lecturerID "
            + "ORDER BY m.moduleID"
    )
    ,

@NamedQuery(
            name = "MyModule.findByLecturerID",
            query = "SELECT m FROM MyModule m "
            + "WHERE m.assignedLecturerID = :lecturerID "
            + "ORDER BY m.moduleCode"
    )
    ,

@NamedQuery(
            name = "MyModule.countByModuleAndLecturer",
            query = "SELECT COUNT(m) FROM MyModule m "
            + "WHERE m.moduleID = :mid "
            + "AND m.assignedLecturerID = :lid"
    )
    ,
@NamedQuery(
            name = "MyModule.findByModuleID",
            query = "SELECT m FROM MyModule m WHERE m.moduleID = :moduleID"
    )

})
public class MyModule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer moduleID;
    private String moduleName;
    private String moduleCode;
    private String description;
    private String createdBy;
    private String assignedLecturerID;

    public MyModule() {
    }

    public MyModule(String moduleName, String moduleCode, String description,
            String createdBy, String assignedLecturerID) {
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.description = description;
        this.createdBy = createdBy;
        this.assignedLecturerID = assignedLecturerID;
    }

    public Integer getModuleID() {
        return moduleID;
    }

    public void setModuleID(Integer moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignedLecturerID() {
        return assignedLecturerID;
    }

    public void setAssignedLecturerID(String assignedLecturerID) {
        this.assignedLecturerID = assignedLecturerID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (moduleID != null ? moduleID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MyModule)) {
            return false;
        }
        MyModule other = (MyModule) object;
        if ((this.moduleID == null && other.moduleID != null)
                || (this.moduleID != null && !this.moduleID.equals(other.moduleID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MyModule[ moduleID=" + moduleID + " ]";
    }
}
