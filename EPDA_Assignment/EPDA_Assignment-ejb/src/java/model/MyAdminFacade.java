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
public class MyAdminFacade extends AbstractFacade<MyAdmin> {

    @PersistenceContext(unitName = "EPDA_Assignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyAdminFacade() {
        super(MyAdmin.class);
    }
    
    public List<MyAdmin> findAllExcept(String userId) {
        return em.createNamedQuery("MyAdmin.findAll", MyAdmin.class)
                .setParameter("userId", userId)
                .getResultList();
    }
    
    public void deleteByUserId(String userId) {
        em.createNamedQuery("MyAdmin.deleteByUserId")
                .setParameter("userId", userId)
                .executeUpdate();
    }
    
}
