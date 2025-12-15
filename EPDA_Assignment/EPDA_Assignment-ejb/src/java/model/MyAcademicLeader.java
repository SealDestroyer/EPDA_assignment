package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MyAcademicLeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String userID;
    private String leaderRole;
    private String startDate;
    private String endDate;

    public MyAcademicLeader() {
    }

    public MyAcademicLeader(String userID, String leaderRole, String startDate, String endDate) {
        this.userID = userID;
        this.leaderRole = leaderRole;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLeaderRole() {
        return leaderRole;
    }

    public void setLeaderRole(String leaderRole) {
        this.leaderRole = leaderRole;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MyAcademicLeader)) {
            return false;
        }
        MyAcademicLeader other = (MyAcademicLeader) object;
        if ((this.userID == null && other.userID != null)
                || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MyAcademicLeader[ userID=" + userID + " ]";
    }
}
