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
 * @author bohch
 */
@Stateless
public class MyUserIDFacade extends AbstractFacade<MyUserID> {

    @PersistenceContext(unitName = "EPDA_Assignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyUserIDFacade() {
        super(MyUserID.class);
    }
    
    public long countRecord() {
        return em.createNamedQuery("MyUserID.count", Long.class).getSingleResult();
    }
    
    public List<MyUserID> findByAD() {
        return em.createNamedQuery("MyUserID.findByAD", MyUserID.class).getResultList();
    }
    
    public List<MyUserID> findByAL() {
        return em.createNamedQuery("MyUserID.findByAL", MyUserID.class).getResultList();
    }
    
    public List<MyUserID> findByL() {
        return em.createNamedQuery("MyUserID.findByL", MyUserID.class).getResultList();
    }
    
    public List<MyUserID> findByS() {
        return em.createNamedQuery("MyUserID.findByS", MyUserID.class).getResultList();
    }
    
    public void updateCurrentUserId(String currentUserId, String userType) {
        em.createNamedQuery("MyUserID.updateCurrentUserId")
                .setParameter("currentUserId", currentUserId)
                .setParameter("userType", userType)
                .executeUpdate();
    }
    
}
