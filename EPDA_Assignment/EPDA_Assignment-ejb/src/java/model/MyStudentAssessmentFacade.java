/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class MyStudentAssessmentFacade extends AbstractFacade<MyStudentAssessment> {

    @PersistenceContext(unitName = "EPDA_Assignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyStudentAssessmentFacade() {
        super(MyStudentAssessment.class);
    }

    public List<Object[]> avgMarkByAssessmentName(Integer moduleID) {
        if (moduleID == null) {
            return java.util.Collections.emptyList();
        }

        return em.createQuery(
                "SELECT a.assessmentName, AVG(sa.mark) "
                + "FROM MyAssessmentType a "
                + "LEFT JOIN MyStudentAssessment sa ON sa.assessmentID = a.assessmentID "
                + "WHERE a.moduleID = :moduleID "
                + "GROUP BY a.assessmentName "
                + "ORDER BY a.assessmentName",
                Object[].class
        ).setParameter("moduleID", moduleID)
                .getResultList();
    }

    public int deleteByStudentID(String studentID) {
        return em.createNamedQuery("MyStudentAssessment.deleteByStudentID")
                .setParameter("studentID", studentID)
                .executeUpdate();
    }

    public List<MyStudentAssessment> findByStudentID(String studentID) {
        return em.createNamedQuery("MyStudentAssessment.findByStudentID", MyStudentAssessment.class)
                .setParameter("studentID", studentID)
                .getResultList();
    }

}
