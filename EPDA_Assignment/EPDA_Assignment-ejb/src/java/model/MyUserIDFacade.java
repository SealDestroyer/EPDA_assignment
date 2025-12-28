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
    
}
