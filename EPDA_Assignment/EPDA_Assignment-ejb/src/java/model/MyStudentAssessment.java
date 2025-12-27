package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
public class MyStudentAssessment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentAssessmentID;

    private String studentID;
    private Integer assessmentID;
    private Integer mark;
    private String dateAssessed;
    private String feedbackText;
    private String assessedBy;
    private String grade;

    public MyStudentAssessment() {
    }

    public MyStudentAssessment(String studentID, Integer assessmentID, Integer mark,
            String dateAssessed, String feedbackText, String assessedBy, String grade) {
        this.studentID = studentID;
        this.assessmentID = assessmentID;
        this.mark = mark;
        this.dateAssessed = dateAssessed;
        this.feedbackText = feedbackText;
        this.assessedBy = assessedBy;
        this.grade = grade;
    }

    public Integer getStudentAssessmentID() {
        return studentAssessmentID;
    }

    public void setStudentAssessmentID(Integer studentAssessmentID) {
        this.studentAssessmentID = studentAssessmentID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public Integer getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(Integer assessmentID) {
        this.assessmentID = assessmentID;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getDateAssessed() {
        return dateAssessed;
    }

    public void setDateAssessed(String dateAssessed) {
        this.dateAssessed = dateAssessed;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public String getAssessedBy() {
        return assessedBy;
    }

    public void setAssessedBy(String assessedBy) {
        this.assessedBy = assessedBy;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentAssessmentID != null ? studentAssessmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MyStudentAssessment)) {
            return false;
        }
        MyStudentAssessment other = (MyStudentAssessment) object;
        if ((this.studentAssessmentID == null && other.studentAssessmentID != null)
                || (this.studentAssessmentID != null && !this.studentAssessmentID.equals(other.studentAssessmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MyStudentAssessment[ studentAssessmentID=" + studentAssessmentID + " ]";
    }
}
