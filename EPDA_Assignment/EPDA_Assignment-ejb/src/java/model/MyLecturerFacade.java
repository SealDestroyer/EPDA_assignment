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
public class MyLecturerFacade extends AbstractFacade<MyLecturer> {

    @PersistenceContext(unitName = "EPDA_Assignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyLecturerFacade() {
        super(MyLecturer.class);
    }
    
    public int updateAcademicLeaderIDToNull(String academicLeaderID) {
        return em.createNamedQuery("MyLecturer.updateAcademicLeaderIDToNull")
                .setParameter("academicLeaderID", academicLeaderID)
                .executeUpdate();
    }
    
    public int deleteByUserID(String userID) {
        return em.createNamedQuery("MyLecturer.deleteByUserID")
                .setParameter("userID", userID)
                .executeUpdate();
    }
    
    public List<Object[]> countByAcademicLeaderID() {
        return em.createNamedQuery("MyLecturer.countByAcademicLeaderID")
                .getResultList();
    }
    
    public MyLecturer findByUserID(String userID) {
        List<MyLecturer> results = em.createNamedQuery("MyLecturer.findByUserID")
                .setParameter("userID", userID)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    
}
