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
            name = "MyStudentClassEnrollment.findGradingListByAssessment",
            query
            = "SELECT u.userID, u.fullName, "
            + "       sa.studentAssessmentID, "
            + "       sa.mark, sa.dateAssessed, sa.feedbackText, sa.grade, "
            + "       sa.assessedBy, lec.fullName "
            + "FROM MyStudentClassEnrollment e "
            + "JOIN MyUsers u ON u.userID = e.studentID "
            + "JOIN MyStudentClass c ON c.classID = e.classID "
            + "LEFT JOIN MyStudentAssessment sa "
            + "   ON sa.studentID = e.studentID "
            + "  AND sa.assessmentID = :assessmentID "
            + "LEFT JOIN MyUsers lec ON lec.userID = sa.assessedBy "
            + "WHERE c.moduleID = ("
            + "   SELECT a.moduleID FROM MyAssessmentType a "
            + "   WHERE a.assessmentID = :assessmentID"
            + ") "
            + "ORDER BY u.userID"
    ),
    @NamedQuery(
            name = "MyStudentClassEnrollment.findByClassID",
            query = "SELECT e FROM MyStudentClassEnrollment e WHERE e.classID = :classID"
    )
})

public class MyStudentClassEnrollment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enrollmentID;
    private String studentID;
    private Integer classID;
    private String enrollmentDate;

    public MyStudentClassEnrollment() {
    }

    // constructor without enrollmentID (DB generates it)
    public MyStudentClassEnrollment(String studentID, Integer classID, String enrollmentDate) {
        this.studentID = studentID;
        this.classID = classID;
        this.enrollmentDate = enrollmentDate;
    }

    public Integer getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(Integer enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public Integer getClassID() {
        return classID;
    }

    public void setClassID(Integer classID) {
        this.classID = classID;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enrollmentID != null ? enrollmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MyStudentClassEnrollment)) {
            return false;
        }
        MyStudentClassEnrollment other = (MyStudentClassEnrollment) object;
        if ((this.enrollmentID == null && other.enrollmentID != null)
                || (this.enrollmentID != null && !this.enrollmentID.equals(other.enrollmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MyStudentClassEnrollment[ enrollmentID=" + enrollmentID + " ]";
    }
}
