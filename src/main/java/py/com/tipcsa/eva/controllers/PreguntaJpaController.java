/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.tipcsa.eva.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import py.com.tipcsa.eva.entities.CargoPregunta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.tipcsa.eva.controllers.exceptions.IllegalOrphanException;
import py.com.tipcsa.eva.controllers.exceptions.NonexistentEntityException;
import py.com.tipcsa.eva.entities.GrupoPregunta;
import py.com.tipcsa.eva.entities.EvaluacionDetalle;
import py.com.tipcsa.eva.entities.Pregunta;

/**
 *
 * @author santi
 */
public class PreguntaJpaController implements Serializable {

    public PreguntaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pregunta pregunta) {
        if (pregunta.getCargoPreguntaList() == null) {
            pregunta.setCargoPreguntaList(new ArrayList<CargoPregunta>());
        }
        if (pregunta.getGrupoPreguntaList() == null) {
            pregunta.setGrupoPreguntaList(new ArrayList<GrupoPregunta>());
        }
        if (pregunta.getEvaluacionDetalleList() == null) {
            pregunta.setEvaluacionDetalleList(new ArrayList<EvaluacionDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CargoPregunta> attachedCargoPreguntaList = new ArrayList<CargoPregunta>();
            for (CargoPregunta cargoPreguntaListCargoPreguntaToAttach : pregunta.getCargoPreguntaList()) {
                cargoPreguntaListCargoPreguntaToAttach = em.getReference(cargoPreguntaListCargoPreguntaToAttach.getClass(), cargoPreguntaListCargoPreguntaToAttach.getCargoPregunta());
                attachedCargoPreguntaList.add(cargoPreguntaListCargoPreguntaToAttach);
            }
            pregunta.setCargoPreguntaList(attachedCargoPreguntaList);
            List<GrupoPregunta> attachedGrupoPreguntaList = new ArrayList<GrupoPregunta>();
            for (GrupoPregunta grupoPreguntaListGrupoPreguntaToAttach : pregunta.getGrupoPreguntaList()) {
                grupoPreguntaListGrupoPreguntaToAttach = em.getReference(grupoPreguntaListGrupoPreguntaToAttach.getClass(), grupoPreguntaListGrupoPreguntaToAttach.getGrupoPregunta());
                attachedGrupoPreguntaList.add(grupoPreguntaListGrupoPreguntaToAttach);
            }
            pregunta.setGrupoPreguntaList(attachedGrupoPreguntaList);
            List<EvaluacionDetalle> attachedEvaluacionDetalleList = new ArrayList<EvaluacionDetalle>();
            for (EvaluacionDetalle evaluacionDetalleListEvaluacionDetalleToAttach : pregunta.getEvaluacionDetalleList()) {
                evaluacionDetalleListEvaluacionDetalleToAttach = em.getReference(evaluacionDetalleListEvaluacionDetalleToAttach.getClass(), evaluacionDetalleListEvaluacionDetalleToAttach.getEvaluacionDetalle());
                attachedEvaluacionDetalleList.add(evaluacionDetalleListEvaluacionDetalleToAttach);
            }
            pregunta.setEvaluacionDetalleList(attachedEvaluacionDetalleList);
            em.persist(pregunta);
            for (CargoPregunta cargoPreguntaListCargoPregunta : pregunta.getCargoPreguntaList()) {
                Pregunta oldPreguntaOfCargoPreguntaListCargoPregunta = cargoPreguntaListCargoPregunta.getPregunta();
                cargoPreguntaListCargoPregunta.setPregunta(pregunta);
                cargoPreguntaListCargoPregunta = em.merge(cargoPreguntaListCargoPregunta);
                if (oldPreguntaOfCargoPreguntaListCargoPregunta != null) {
                    oldPreguntaOfCargoPreguntaListCargoPregunta.getCargoPreguntaList().remove(cargoPreguntaListCargoPregunta);
                    oldPreguntaOfCargoPreguntaListCargoPregunta = em.merge(oldPreguntaOfCargoPreguntaListCargoPregunta);
                }
            }
            for (GrupoPregunta grupoPreguntaListGrupoPregunta : pregunta.getGrupoPreguntaList()) {
                Pregunta oldPreguntaOfGrupoPreguntaListGrupoPregunta = grupoPreguntaListGrupoPregunta.getPregunta();
                grupoPreguntaListGrupoPregunta.setPregunta(pregunta);
                grupoPreguntaListGrupoPregunta = em.merge(grupoPreguntaListGrupoPregunta);
                if (oldPreguntaOfGrupoPreguntaListGrupoPregunta != null) {
                    oldPreguntaOfGrupoPreguntaListGrupoPregunta.getGrupoPreguntaList().remove(grupoPreguntaListGrupoPregunta);
                    oldPreguntaOfGrupoPreguntaListGrupoPregunta = em.merge(oldPreguntaOfGrupoPreguntaListGrupoPregunta);
                }
            }
            for (EvaluacionDetalle evaluacionDetalleListEvaluacionDetalle : pregunta.getEvaluacionDetalleList()) {
                Pregunta oldPreguntaOfEvaluacionDetalleListEvaluacionDetalle = evaluacionDetalleListEvaluacionDetalle.getPregunta();
                evaluacionDetalleListEvaluacionDetalle.setPregunta(pregunta);
                evaluacionDetalleListEvaluacionDetalle = em.merge(evaluacionDetalleListEvaluacionDetalle);
                if (oldPreguntaOfEvaluacionDetalleListEvaluacionDetalle != null) {
                    oldPreguntaOfEvaluacionDetalleListEvaluacionDetalle.getEvaluacionDetalleList().remove(evaluacionDetalleListEvaluacionDetalle);
                    oldPreguntaOfEvaluacionDetalleListEvaluacionDetalle = em.merge(oldPreguntaOfEvaluacionDetalleListEvaluacionDetalle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pregunta pregunta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pregunta persistentPregunta = em.find(Pregunta.class, pregunta.getPregunta());
            List<CargoPregunta> cargoPreguntaListOld = persistentPregunta.getCargoPreguntaList();
            List<CargoPregunta> cargoPreguntaListNew = pregunta.getCargoPreguntaList();
            List<GrupoPregunta> grupoPreguntaListOld = persistentPregunta.getGrupoPreguntaList();
            List<GrupoPregunta> grupoPreguntaListNew = pregunta.getGrupoPreguntaList();
            List<EvaluacionDetalle> evaluacionDetalleListOld = persistentPregunta.getEvaluacionDetalleList();
            List<EvaluacionDetalle> evaluacionDetalleListNew = pregunta.getEvaluacionDetalleList();
            List<String> illegalOrphanMessages = null;
            for (CargoPregunta cargoPreguntaListOldCargoPregunta : cargoPreguntaListOld) {
                if (!cargoPreguntaListNew.contains(cargoPreguntaListOldCargoPregunta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CargoPregunta " + cargoPreguntaListOldCargoPregunta + " since its pregunta field is not nullable.");
                }
            }
            for (GrupoPregunta grupoPreguntaListOldGrupoPregunta : grupoPreguntaListOld) {
                if (!grupoPreguntaListNew.contains(grupoPreguntaListOldGrupoPregunta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GrupoPregunta " + grupoPreguntaListOldGrupoPregunta + " since its pregunta field is not nullable.");
                }
            }
            for (EvaluacionDetalle evaluacionDetalleListOldEvaluacionDetalle : evaluacionDetalleListOld) {
                if (!evaluacionDetalleListNew.contains(evaluacionDetalleListOldEvaluacionDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvaluacionDetalle " + evaluacionDetalleListOldEvaluacionDetalle + " since its pregunta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CargoPregunta> attachedCargoPreguntaListNew = new ArrayList<CargoPregunta>();
            for (CargoPregunta cargoPreguntaListNewCargoPreguntaToAttach : cargoPreguntaListNew) {
                cargoPreguntaListNewCargoPreguntaToAttach = em.getReference(cargoPreguntaListNewCargoPreguntaToAttach.getClass(), cargoPreguntaListNewCargoPreguntaToAttach.getCargoPregunta());
                attachedCargoPreguntaListNew.add(cargoPreguntaListNewCargoPreguntaToAttach);
            }
            cargoPreguntaListNew = attachedCargoPreguntaListNew;
            pregunta.setCargoPreguntaList(cargoPreguntaListNew);
            List<GrupoPregunta> attachedGrupoPreguntaListNew = new ArrayList<GrupoPregunta>();
            for (GrupoPregunta grupoPreguntaListNewGrupoPreguntaToAttach : grupoPreguntaListNew) {
                grupoPreguntaListNewGrupoPreguntaToAttach = em.getReference(grupoPreguntaListNewGrupoPreguntaToAttach.getClass(), grupoPreguntaListNewGrupoPreguntaToAttach.getGrupoPregunta());
                attachedGrupoPreguntaListNew.add(grupoPreguntaListNewGrupoPreguntaToAttach);
            }
            grupoPreguntaListNew = attachedGrupoPreguntaListNew;
            pregunta.setGrupoPreguntaList(grupoPreguntaListNew);
            List<EvaluacionDetalle> attachedEvaluacionDetalleListNew = new ArrayList<EvaluacionDetalle>();
            for (EvaluacionDetalle evaluacionDetalleListNewEvaluacionDetalleToAttach : evaluacionDetalleListNew) {
                evaluacionDetalleListNewEvaluacionDetalleToAttach = em.getReference(evaluacionDetalleListNewEvaluacionDetalleToAttach.getClass(), evaluacionDetalleListNewEvaluacionDetalleToAttach.getEvaluacionDetalle());
                attachedEvaluacionDetalleListNew.add(evaluacionDetalleListNewEvaluacionDetalleToAttach);
            }
            evaluacionDetalleListNew = attachedEvaluacionDetalleListNew;
            pregunta.setEvaluacionDetalleList(evaluacionDetalleListNew);
            pregunta = em.merge(pregunta);
            for (CargoPregunta cargoPreguntaListNewCargoPregunta : cargoPreguntaListNew) {
                if (!cargoPreguntaListOld.contains(cargoPreguntaListNewCargoPregunta)) {
                    Pregunta oldPreguntaOfCargoPreguntaListNewCargoPregunta = cargoPreguntaListNewCargoPregunta.getPregunta();
                    cargoPreguntaListNewCargoPregunta.setPregunta(pregunta);
                    cargoPreguntaListNewCargoPregunta = em.merge(cargoPreguntaListNewCargoPregunta);
                    if (oldPreguntaOfCargoPreguntaListNewCargoPregunta != null && !oldPreguntaOfCargoPreguntaListNewCargoPregunta.equals(pregunta)) {
                        oldPreguntaOfCargoPreguntaListNewCargoPregunta.getCargoPreguntaList().remove(cargoPreguntaListNewCargoPregunta);
                        oldPreguntaOfCargoPreguntaListNewCargoPregunta = em.merge(oldPreguntaOfCargoPreguntaListNewCargoPregunta);
                    }
                }
            }
            for (GrupoPregunta grupoPreguntaListNewGrupoPregunta : grupoPreguntaListNew) {
                if (!grupoPreguntaListOld.contains(grupoPreguntaListNewGrupoPregunta)) {
                    Pregunta oldPreguntaOfGrupoPreguntaListNewGrupoPregunta = grupoPreguntaListNewGrupoPregunta.getPregunta();
                    grupoPreguntaListNewGrupoPregunta.setPregunta(pregunta);
                    grupoPreguntaListNewGrupoPregunta = em.merge(grupoPreguntaListNewGrupoPregunta);
                    if (oldPreguntaOfGrupoPreguntaListNewGrupoPregunta != null && !oldPreguntaOfGrupoPreguntaListNewGrupoPregunta.equals(pregunta)) {
                        oldPreguntaOfGrupoPreguntaListNewGrupoPregunta.getGrupoPreguntaList().remove(grupoPreguntaListNewGrupoPregunta);
                        oldPreguntaOfGrupoPreguntaListNewGrupoPregunta = em.merge(oldPreguntaOfGrupoPreguntaListNewGrupoPregunta);
                    }
                }
            }
            for (EvaluacionDetalle evaluacionDetalleListNewEvaluacionDetalle : evaluacionDetalleListNew) {
                if (!evaluacionDetalleListOld.contains(evaluacionDetalleListNewEvaluacionDetalle)) {
                    Pregunta oldPreguntaOfEvaluacionDetalleListNewEvaluacionDetalle = evaluacionDetalleListNewEvaluacionDetalle.getPregunta();
                    evaluacionDetalleListNewEvaluacionDetalle.setPregunta(pregunta);
                    evaluacionDetalleListNewEvaluacionDetalle = em.merge(evaluacionDetalleListNewEvaluacionDetalle);
                    if (oldPreguntaOfEvaluacionDetalleListNewEvaluacionDetalle != null && !oldPreguntaOfEvaluacionDetalleListNewEvaluacionDetalle.equals(pregunta)) {
                        oldPreguntaOfEvaluacionDetalleListNewEvaluacionDetalle.getEvaluacionDetalleList().remove(evaluacionDetalleListNewEvaluacionDetalle);
                        oldPreguntaOfEvaluacionDetalleListNewEvaluacionDetalle = em.merge(oldPreguntaOfEvaluacionDetalleListNewEvaluacionDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pregunta.getPregunta();
                if (findPregunta(id) == null) {
                    throw new NonexistentEntityException("The pregunta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pregunta pregunta;
            try {
                pregunta = em.getReference(Pregunta.class, id);
                pregunta.getPregunta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pregunta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CargoPregunta> cargoPreguntaListOrphanCheck = pregunta.getCargoPreguntaList();
            for (CargoPregunta cargoPreguntaListOrphanCheckCargoPregunta : cargoPreguntaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pregunta (" + pregunta + ") cannot be destroyed since the CargoPregunta " + cargoPreguntaListOrphanCheckCargoPregunta + " in its cargoPreguntaList field has a non-nullable pregunta field.");
            }
            List<GrupoPregunta> grupoPreguntaListOrphanCheck = pregunta.getGrupoPreguntaList();
            for (GrupoPregunta grupoPreguntaListOrphanCheckGrupoPregunta : grupoPreguntaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pregunta (" + pregunta + ") cannot be destroyed since the GrupoPregunta " + grupoPreguntaListOrphanCheckGrupoPregunta + " in its grupoPreguntaList field has a non-nullable pregunta field.");
            }
            List<EvaluacionDetalle> evaluacionDetalleListOrphanCheck = pregunta.getEvaluacionDetalleList();
            for (EvaluacionDetalle evaluacionDetalleListOrphanCheckEvaluacionDetalle : evaluacionDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pregunta (" + pregunta + ") cannot be destroyed since the EvaluacionDetalle " + evaluacionDetalleListOrphanCheckEvaluacionDetalle + " in its evaluacionDetalleList field has a non-nullable pregunta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pregunta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pregunta> findPreguntaEntities() {
        return findPreguntaEntities(true, -1, -1);
    }

    public List<Pregunta> findPreguntaEntities(int maxResults, int firstResult) {
        return findPreguntaEntities(false, maxResults, firstResult);
    }

    private List<Pregunta> findPreguntaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pregunta.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Pregunta findPregunta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pregunta.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreguntaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pregunta> rt = cq.from(Pregunta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
