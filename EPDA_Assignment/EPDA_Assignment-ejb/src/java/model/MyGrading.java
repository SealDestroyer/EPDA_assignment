package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
public class MyGrading implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gradingID;
    private String gradeLetter;
    private Integer minPercentage;
    private Integer maxPercentage;

    public MyGrading() {
    }

    // constructor without gradingID (DB generates it)
    public MyGrading(String gradeLetter, Integer minPercentage, Integer maxPercentage) {
        this.gradeLetter = gradeLetter;
        this.minPercentage = minPercentage;
        this.maxPercentage = maxPercentage;
    }

    public Integer getGradingID() {
        return gradingID;
    }

    public void setGradingID(Integer gradingID) {
        this.gradingID = gradingID;
    }

    public String getGradeLetter() {
        return gradeLetter;
    }

    public void setGradeLetter(String gradeLetter) {
        this.gradeLetter = gradeLetter;
    }

    public Integer getMinPercentage() {
        return minPercentage;
    }

    public void setMinPercentage(Integer minPercentage) {
        this.minPercentage = minPercentage;
    }

    public Integer getMaxPercentage() {
        return maxPercentage;
    }

    public void setMaxPercentage(Integer maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gradingID != null ? gradingID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MyGrading)) {
            return false;
        }
        MyGrading other = (MyGrading) object;
        if ((this.gradingID == null && other.gradingID != null)
                || (this.gradingID != null && !this.gradingID.equals(other.gradingID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MyGrading[ gradingID=" + gradingID + " ]";
    }
}
