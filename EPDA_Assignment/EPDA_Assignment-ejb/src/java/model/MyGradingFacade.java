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
public class MyGradingFacade extends AbstractFacade<MyGrading> {

    @PersistenceContext(unitName = "EPDA_Assignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyGradingFacade() {
        super(MyGrading.class);
    }

    public MyGrading findByPercentage(int percentage) {
        try {
            return em.createNamedQuery(
                    "MyGrading.findByPercentage",
                    MyGrading.class
            ).setParameter("percentage", percentage)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
