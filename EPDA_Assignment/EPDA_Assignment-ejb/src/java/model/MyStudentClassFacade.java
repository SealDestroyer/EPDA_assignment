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
public class MyStudentClassFacade extends AbstractFacade<MyStudentClass> {

    @PersistenceContext(unitName = "EPDA_Assignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyStudentClassFacade() {
        super(MyStudentClass.class);
    }

    public List<MyStudentClass> findAll() {
        return em.createNamedQuery("MyStudentClass.findAll", MyStudentClass.class)
                .getResultList();
    }

    public void deleteByClassId(Integer classID) {
        em.createNamedQuery("MyStudentClass.deleteByClassId")
                .setParameter("classID", classID)
                .executeUpdate();
    }

    public List<MyStudentClass> findByAssignedAcademicLeaderID(String alID) {
        return em.createNamedQuery("MyStudentClass.findByAssignedAcademicLeaderID", MyStudentClass.class)
                .setParameter("alID", alID)
                .getResultList();
    }

    public List<MyStudentClass> searchByClassNameForAL(String keyword, String alID) {
        keyword = (keyword == null) ? "" : keyword.trim().toLowerCase();

        return em.createNamedQuery("MyStudentClass.searchByClassNameForAL", MyStudentClass.class)
                .setParameter("alID", alID)
                .setParameter("kw", "%" + keyword + "%")
                .getResultList();
    }

}
