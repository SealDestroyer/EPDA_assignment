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
public class MyModuleFacade extends AbstractFacade<MyModule> {

    @PersistenceContext(unitName = "EPDA_Assignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyModuleFacade() {
        super(MyModule.class);
    }

    public List<MyModule> getAllModules() {
        Query q = em.createNamedQuery("MyModule.findAll");
        return q.getResultList(); // returns empty list if no records (good!)
    }

}
