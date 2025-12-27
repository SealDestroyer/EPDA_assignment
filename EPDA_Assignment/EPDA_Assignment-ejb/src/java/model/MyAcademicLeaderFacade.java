/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class MyAcademicLeaderFacade extends AbstractFacade<MyAcademicLeader> {

    @PersistenceContext(unitName = "EPDA_Assignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyAcademicLeaderFacade() {
        super(MyAcademicLeader.class);
    }
    
    public void deleteByUserID(String userID) {
        em.createNamedQuery("MyAcademicLeader.deleteByUserID")
          .setParameter("userID", userID)
          .executeUpdate();
    }
    
}
