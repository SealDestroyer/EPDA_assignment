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
            name = "MyAssessmentType.findAll",
            query = "SELECT a FROM MyAssessmentType a ORDER BY a.assessmentID"
    )
    ,
    @NamedQuery(
            name = "MyAssessmentType.findByModule",
            query = "SELECT a FROM MyAssessmentType a "
            + "WHERE a.moduleID = :moduleID "
            + "ORDER BY a.assessmentID"
    )
    ,
    @NamedQuery(
            name = "MyAssessmentType.findByModuleAndName",
            query = "SELECT a FROM MyAssessmentType a "
            + "WHERE a.moduleID = :moduleID "
            + "AND LOWER(a.assessmentName) = :name"
    )
    ,
    @NamedQuery(
            name = "MyAssessmentType.findByAssessmentID",
            query = "SELECT a FROM MyAssessmentType a WHERE a.assessmentID = :assessmentID"
    )
})
public class MyAssessmentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assessmentID;
    private Integer moduleID;
    private String assessmentName;
    private Integer weightage;
    private String createdBy;

    public MyAssessmentType() {
    }

    public MyAssessmentType(Integer moduleID, String assessmentName,
            Integer weightage, String createdBy) {
        this.moduleID = moduleID;
        this.assessmentName = assessmentName;
        this.weightage = weightage;
        this.createdBy = createdBy;
    }

    public Integer getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(Integer assessmentID) {
        this.assessmentID = assessmentID;
    }

    public Integer getModuleID() {
        return moduleID;
    }

    public void setModuleID(Integer moduleID) {
        this.moduleID = moduleID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public Integer getWeightage() {
        return weightage;
    }

    public void setWeightage(Integer weightage) {
        this.weightage = weightage;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assessmentID != null ? assessmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MyAssessmentType)) {
            return false;
        }
        MyAssessmentType other = (MyAssessmentType) object;
        if ((this.assessmentID == null && other.assessmentID != null)
                || (this.assessmentID != null && !this.assessmentID.equals(other.assessmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MyAssessmentType[ assessmentID=" + assessmentID + " ]";
    }
}
