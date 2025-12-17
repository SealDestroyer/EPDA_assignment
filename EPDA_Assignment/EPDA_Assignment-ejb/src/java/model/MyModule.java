package model;

import java.io.Serializable;
import javax.persistence.Entity;
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
            + "WHERE LOWER(m.moduleID) LIKE :kw "
            + "   OR LOWER(m.moduleCode) LIKE :kw "
            + "   OR LOWER(m.moduleName) LIKE :kw "
            + "ORDER BY m.moduleID"
    )
})
public class MyModule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String moduleID;             // PK

    private String moduleName;
    private String moduleCode;
    private String description;

    private String createdBy;            // FK → AcademicLeader(UserID)
    private String assignedLecturerID;   // FK → Lecturer(UserID), nullable

    public MyModule() {
    }

    public MyModule(String moduleID, String moduleName, String moduleCode,
            String description, String createdBy, String assignedLecturerID) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.description = description;
        this.createdBy = createdBy;
        this.assignedLecturerID = assignedLecturerID;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
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
