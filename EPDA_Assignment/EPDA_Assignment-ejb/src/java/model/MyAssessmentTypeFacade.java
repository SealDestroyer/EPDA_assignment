package model;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MyAssessmentTypeFacade extends AbstractFacade<MyAssessmentType> {

    @PersistenceContext(unitName = "EPDA_Assignment-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyAssessmentTypeFacade() {
        super(MyAssessmentType.class);
    }

    // ===== FIND BY ASSESSMENT ID =====
    public MyAssessmentType findByAssessmentID(Integer assessmentID) {
        List<MyAssessmentType> results = em.createNamedQuery("MyAssessmentType.findByAssessmentID", MyAssessmentType.class)
                .setParameter("assessmentID", assessmentID)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    // ===== LIST BY MODULE =====
    public List<MyAssessmentType> findByModule(Integer moduleID) {
        return em.createNamedQuery("MyAssessmentType.findByModule", MyAssessmentType.class)
                .setParameter("moduleID", moduleID)
                .getResultList();
    }

    // ===== CHECK DUPLICATE NAME IN SAME MODULE =====
    public boolean existsNameInModule(Integer moduleID, String assessmentName) {
        List<MyAssessmentType> list = em.createNamedQuery("MyAssessmentType.findByModuleAndName", MyAssessmentType.class)
                .setParameter("moduleID", moduleID)
                .setParameter("name", assessmentName.toLowerCase().trim())
                .getResultList();
        return !list.isEmpty();
    }

    // ===== SUM WEIGHTAGE FOR A MODULE =====
    public int sumWeightageByModule(Integer moduleID) {
        List<MyAssessmentType> list = findByModule(moduleID);
        int sum = 0;
        for (MyAssessmentType a : list) {
            if (a.getWeightage() != null) {
                sum += a.getWeightage();
            }
        }
        return sum;
    }

    // ===== SUM WEIGHTAGE EXCLUDING ONE ASSESSMENT (for EDIT validation) =====
    public int sumWeightageByModuleExcept(Integer moduleID, Integer excludeAssessmentID) {
        List<MyAssessmentType> list = findByModule(moduleID);
        int sum = 0;
        for (MyAssessmentType a : list) {
            if (a.getAssessmentID() != null && a.getAssessmentID().equals(excludeAssessmentID)) {
                continue;
            }
            if (a.getWeightage() != null) {
                sum += a.getWeightage();
            }
        }
        return sum;
    }
}
