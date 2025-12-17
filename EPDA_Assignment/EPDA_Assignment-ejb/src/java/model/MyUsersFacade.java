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
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
public class MyUsersFacade extends AbstractFacade<MyUsers> {

    @PersistenceContext(unitName = "EPDA_Assignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyUsersFacade() {
        super(MyUsers.class);
    }

    public List<MyUsers> findLecturers() {
        Query q = em.createNamedQuery("MyUsers.findLecturers");
        return q.getResultList();
    }

}
