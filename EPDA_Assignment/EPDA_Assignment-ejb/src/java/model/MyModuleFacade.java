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
        return q.getResultList(); // returns empty list if no records
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

    public List<String> findDistinctLecturerIdsByAL(String alID) {
        if (alID == null || alID.trim().isEmpty()) {
            return java.util.Collections.emptyList();
        }

        return em.createQuery(
                "SELECT DISTINCT m.assignedLecturerID "
                + "FROM MyModule m "
                + "WHERE m.createdBy = :alID "
                + "AND m.assignedLecturerID IS NOT NULL "
                + "ORDER BY m.assignedLecturerID",
                String.class
        ).setParameter("alID", alID.trim())
                .getResultList();
    }

    public List<MyModule> findByALAndLecturer(String alID, String lecturerID) {
        if (alID == null || alID.trim().isEmpty() || lecturerID == null || lecturerID.trim().isEmpty()) {
            return java.util.Collections.emptyList();
        }

        return em.createQuery(
                "SELECT m FROM MyModule m "
                + "WHERE m.createdBy = :alID "
                + "AND m.assignedLecturerID = :lecturerID "
                + "ORDER BY m.moduleID",
                MyModule.class
        ).setParameter("alID", alID.trim())
                .setParameter("lecturerID", lecturerID.trim())
                .getResultList();
    }

    public List<MyModule> findByLecturerID(String lecturerID) {
        if (lecturerID == null || lecturerID.trim().isEmpty()) {
            return java.util.Collections.emptyList();
        }

        return em.createQuery(
                "SELECT m FROM MyModule m WHERE m.assignedLecturerID = :lecturerID ORDER BY m.moduleCode",
                MyModule.class
        ).setParameter("lecturerID", lecturerID.trim())
                .getResultList();
    }

    public boolean isModuleOwnedByLecturer(Integer moduleID, String lecturerID) {
        if (moduleID == null || lecturerID == null || lecturerID.trim().isEmpty()) {
            return false;
        }

        Long count = em.createQuery(
                "SELECT COUNT(m) FROM MyModule m WHERE m.moduleID = :mid AND m.assignedLecturerID = :lid",
                Long.class
        ).setParameter("mid", moduleID)
                .setParameter("lid", lecturerID.trim())
                .getSingleResult();

        return count != null && count > 0;
    }

    public List<MyModule> findByCreatedBy(String createdBy) {
        if (createdBy == null || createdBy.trim().isEmpty()) {
            return java.util.Collections.emptyList();
        }

        return em.createNamedQuery("MyModule.findByCreatedBy", MyModule.class)
                .setParameter("createdBy", createdBy.trim())
                .getResultList();
    }

    public List<MyModule> searchModulesByCreatedBy(String keyword, String createdBy) {
        if (createdBy == null || createdBy.trim().isEmpty()) {
            return java.util.Collections.emptyList();
        }

        String kw = "%" + (keyword == null ? "" : keyword.toLowerCase().trim()) + "%";

        return em.createNamedQuery("MyModule.searchByCreatedBy", MyModule.class)
                .setParameter("createdBy", createdBy.trim())
                .setParameter("kw", kw)
                .getResultList();
    }

}
