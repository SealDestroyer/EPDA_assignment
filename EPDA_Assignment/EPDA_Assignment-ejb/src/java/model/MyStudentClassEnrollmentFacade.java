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

}
