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

    public List<MyModule> searchModules(String keyword) {

        String kw = "%" + keyword.toLowerCase().trim() + "%";

        Query q = em.createNamedQuery("MyModule.search");
        q.setParameter("kw", kw);

        return q.getResultList();
    }

    public boolean existsModuleCode(String moduleCode) {
        if (moduleCode == null) {
            return false;
        }

        String code = moduleCode.trim().toLowerCase();
        if (code.isEmpty()) {
            return false;
        }

        List<MyModule> list = em.createNamedQuery("MyModule.findByModuleCode", MyModule.class)
                .setParameter("code", code)
                .setMaxResults(1)
                .getResultList();

        return !list.isEmpty();
    }

    public boolean existsModuleCodeExceptId(String moduleCode, Integer moduleID) {
        if (moduleCode == null || moduleID == null) {
            return false;
        }

        String code = moduleCode.trim().toLowerCase();
        if (code.isEmpty()) {
            return false;
        }

        List<MyModule> list = em.createNamedQuery("MyModule.findByModuleCodeExceptId", MyModule.class)
                .setParameter("code", code)
                .setParameter("id", moduleID)
                .setMaxResults(1)
                .getResultList();

        return !list.isEmpty();
    }

    public List<MyModule> findByAssignedLecturer(String lecturerID) {
        if (lecturerID == null || lecturerID.trim().isEmpty()) {
            return java.util.Collections.emptyList();
        }

        return em.createNamedQuery("MyModule.findByLecturer", MyModule.class)
                .setParameter("lecturerID", lecturerID)
                .getResultList();
    }

    public List<MyModule> searchModulesByLecturer(String keyword, String lecturerID) {

        if (lecturerID == null || lecturerID.trim().isEmpty()) {
            return java.util.Collections.emptyList();
        }

        String kw = "%" + keyword.toLowerCase().trim() + "%";

        return em.createNamedQuery("MyModule.searchByLecturer", MyModule.class)
                .setParameter("lecturerID", lecturerID)
                .setParameter("kw", kw)
                .getResultList();
    }

}
