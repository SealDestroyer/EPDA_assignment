/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
public class MyStudentClassEnrollmentFacade extends AbstractFacade<MyStudentClassEnrollment> {

    @PersistenceContext(unitName = "EPDA_Assignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyStudentClassEnrollmentFacade() {
        super(MyStudentClassEnrollment.class);
    }

    public List<Object[]> findGradingListByAssessment(Integer assessmentID) {
        return em.createNamedQuery("MyStudentClassEnrollment.findGradingListByAssessment")
                .setParameter("assessmentID", assessmentID)
                .getResultList();
    }
    
    public List<MyStudentClassEnrollment> findClassStudent(Integer classID) {
        Query q = em.createNamedQuery("MyStudentClassEnrollment.findByClassID");
        q.setParameter("classID", classID);
        return q.getResultList();
    }
    
    public int deleteByStudentID(String studentID) {
        Query q = em.createNamedQuery("MyStudentClassEnrollment.deleteByStudentID");
        q.setParameter("studentID", studentID);
        return q.executeUpdate();
    }
    
    public int deleteByClassID(Integer classID) {
        Query q = em.createNamedQuery("MyStudentClassEnrollment.deleteByClassID");
        q.setParameter("classID", classID);
        return q.executeUpdate();
    }
    
    public int deleteByEnrollmentID(Integer enrollmentID) {
        Query q = em.createNamedQuery("MyStudentClassEnrollment.deleteByEnrollmentID");
        q.setParameter("enrollmentID", enrollmentID);
        return q.executeUpdate();
    }
    
    public List<MyUsers> findStudentsNotInClass(Integer classID) {
        Query q = em.createNamedQuery("MyStudentClassEnrollment.findStudentsNotInClass");
        q.setParameter("classID", classID);
        return q.getResultList();
    }

}
