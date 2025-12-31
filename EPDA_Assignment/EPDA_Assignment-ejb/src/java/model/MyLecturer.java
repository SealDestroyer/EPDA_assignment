package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(
        name = "MyLecturer.updateAcademicLeaderIDToNull",
        query = "UPDATE MyLecturer l SET l.academicLeaderID = NULL WHERE l.academicLeaderID = :academicLeaderID"
    ),
    
    @NamedQuery(
        name = "MyLecturer.deleteByUserID",
        query = "DELETE FROM MyLecturer l WHERE l.userID = :userID"
    ),
    
    @NamedQuery(
        name = "MyLecturer.countByAcademicLeaderID",
        query = "SELECT l.academicLeaderID, COUNT(l) FROM MyLecturer l GROUP BY l.academicLeaderID"
    ),
    
    @NamedQuery(
        name = "MyLecturer.findByUserID",
        query = "SELECT l FROM MyLecturer l WHERE l.userID = :userID"
    )

})
public class MyLecturer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String userID;
    private String employmentType;
    private String academicRank;
    private String academicLeaderID;

    public MyLecturer() {
    }

    public MyLecturer(String userID, String employmentType, String academicRank, String academicLeaderID) {
        this.userID = userID;
        this.employmentType = employmentType;
        this.academicRank = academicRank;
        this.academicLeaderID = academicLeaderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getAcademicRank() {
        return academicRank;
    }

    public void setAcademicRank(String academicRank) {
        this.academicRank = academicRank;
    }

    public String getAcademicLeaderID() {
        return academicLeaderID;
    }

    public void setAcademicLeaderID(String academicLeaderID) {
        this.academicLeaderID = academicLeaderID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MyLecturer)) {
            return false;
        }
        MyLecturer other = (MyLecturer) object;
        if ((this.userID == null && other.userID != null)
                || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MyLecturer[ userID=" + userID + " ]";
    }
}
