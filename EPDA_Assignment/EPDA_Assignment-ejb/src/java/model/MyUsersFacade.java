/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    public List<MyUsers> findAllUsers() {
        Query q = em.createNamedQuery("MyUsers.findAllUsers");
        return q.getResultList();
    }
    
    public List<MyUsers> findAllStudents() {
        Query q = em.createNamedQuery("MyUsers.findAllStudents");
        return q.getResultList();
    }
    
    public List<MyUsers> findAllAcademicLeaders() {
        Query q = em.createNamedQuery("MyUsers.findAcamdemicLeaders");
        return q.getResultList();
    }

    public Map<String, String> findUserNameMapByIds(List<String> ids) {
        Map<String, String> map = new HashMap<>();

        if (ids == null || ids.isEmpty()) {
            return map;
        }

        Query q = em.createNamedQuery("MyUsers.findByUserIds");
        q.setParameter("ids", ids);

        List<MyUsers> users = q.getResultList();
        for (MyUsers u : users) {
            map.put(u.getUserID(), u.getFullName());
        }

        return map;
    }
}
