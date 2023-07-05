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
import py.com.tipcsa.eva.entities.Periodo;
import py.com.tipcsa.eva.entities.Persona;
import py.com.tipcsa.eva.entities.EvaluacionDetalle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.tipcsa.eva.controllers.exceptions.IllegalOrphanException;
import py.com.tipcsa.eva.controllers.exceptions.NonexistentEntityException;
import py.com.tipcsa.eva.entities.Evaluacion;

/**
 *
 * @author santi
 */
public class EvaluacionJpaController implements Serializable {

    public EvaluacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evaluacion evaluacion) {
        if (evaluacion.getEvaluacionDetalleList() == null) {
            evaluacion.setEvaluacionDetalleList(new ArrayList<EvaluacionDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Periodo periodo = evaluacion.getPeriodo();
            if (periodo != null) {
                periodo = em.getReference(periodo.getClass(), periodo.getPeriodo());
                evaluacion.setPeriodo(periodo);
            }
            Persona evaluado = evaluacion.getEvaluado();
            if (evaluado != null) {
                evaluado = em.getReference(evaluado.getClass(), evaluado.getPersona());
                evaluacion.setEvaluado(evaluado);
            }
            Persona evaluador = evaluacion.getEvaluador();
            if (evaluador != null) {
                evaluador = em.getReference(evaluador.getClass(), evaluador.getPersona());
                evaluacion.setEvaluador(evaluador);
            }
            List<EvaluacionDetalle> attachedEvaluacionDetalleList = new ArrayList<EvaluacionDetalle>();
            for (EvaluacionDetalle evaluacionDetalleListEvaluacionDetalleToAttach : evaluacion.getEvaluacionDetalleList()) {
                evaluacionDetalleListEvaluacionDetalleToAttach = em.getReference(evaluacionDetalleListEvaluacionDetalleToAttach.getClass(), evaluacionDetalleListEvaluacionDetalleToAttach.getEvaluacionDetalle());
                attachedEvaluacionDetalleList.add(evaluacionDetalleListEvaluacionDetalleToAttach);
            }
            evaluacion.setEvaluacionDetalleList(attachedEvaluacionDetalleList);
            em.persist(evaluacion);
            if (periodo != null) {
                periodo.getEvaluacionList().add(evaluacion);
                periodo = em.merge(periodo);
            }
            if (evaluado != null) {
                evaluado.getEvaluacionList().add(evaluacion);
                evaluado = em.merge(evaluado);
            }
            if (evaluador != null) {
                evaluador.getEvaluacionList().add(evaluacion);
                evaluador = em.merge(evaluador);
            }
            for (EvaluacionDetalle evaluacionDetalleListEvaluacionDetalle : evaluacion.getEvaluacionDetalleList()) {
                Evaluacion oldEvaluacionOfEvaluacionDetalleListEvaluacionDetalle = evaluacionDetalleListEvaluacionDetalle.getEvaluacion();
                evaluacionDetalleListEvaluacionDetalle.setEvaluacion(evaluacion);
                evaluacionDetalleListEvaluacionDetalle = em.merge(evaluacionDetalleListEvaluacionDetalle);
                if (oldEvaluacionOfEvaluacionDetalleListEvaluacionDetalle != null) {
                    oldEvaluacionOfEvaluacionDetalleListEvaluacionDetalle.getEvaluacionDetalleList().remove(evaluacionDetalleListEvaluacionDetalle);
                    oldEvaluacionOfEvaluacionDetalleListEvaluacionDetalle = em.merge(oldEvaluacionOfEvaluacionDetalleListEvaluacionDetalle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Evaluacion evaluacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evaluacion persistentEvaluacion = em.find(Evaluacion.class, evaluacion.getEvaluacion());
            Periodo periodoOld = persistentEvaluacion.getPeriodo();
            Periodo periodoNew = evaluacion.getPeriodo();
            Persona evaluadoOld = persistentEvaluacion.getEvaluado();
            Persona evaluadoNew = evaluacion.getEvaluado();
            Persona evaluadorOld = persistentEvaluacion.getEvaluador();
            Persona evaluadorNew = evaluacion.getEvaluador();
            List<EvaluacionDetalle> evaluacionDetalleListOld = persistentEvaluacion.getEvaluacionDetalleList();
            List<EvaluacionDetalle> evaluacionDetalleListNew = evaluacion.getEvaluacionDetalleList();
            List<String> illegalOrphanMessages = null;
            for (EvaluacionDetalle evaluacionDetalleListOldEvaluacionDetalle : evaluacionDetalleListOld) {
                if (!evaluacionDetalleListNew.contains(evaluacionDetalleListOldEvaluacionDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvaluacionDetalle " + evaluacionDetalleListOldEvaluacionDetalle + " since its evaluacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (periodoNew != null) {
                periodoNew = em.getReference(periodoNew.getClass(), periodoNew.getPeriodo());
                evaluacion.setPeriodo(periodoNew);
            }
            if (evaluadoNew != null) {
                evaluadoNew = em.getReference(evaluadoNew.getClass(), evaluadoNew.getPersona());
                evaluacion.setEvaluado(evaluadoNew);
            }
            if (evaluadorNew != null) {
                evaluadorNew = em.getReference(evaluadorNew.getClass(), evaluadorNew.getPersona());
                evaluacion.setEvaluador(evaluadorNew);
            }
            List<EvaluacionDetalle> attachedEvaluacionDetalleListNew = new ArrayList<EvaluacionDetalle>();
            for (EvaluacionDetalle evaluacionDetalleListNewEvaluacionDetalleToAttach : evaluacionDetalleListNew) {
                evaluacionDetalleListNewEvaluacionDetalleToAttach = em.getReference(evaluacionDetalleListNewEvaluacionDetalleToAttach.getClass(), evaluacionDetalleListNewEvaluacionDetalleToAttach.getEvaluacionDetalle());
                attachedEvaluacionDetalleListNew.add(evaluacionDetalleListNewEvaluacionDetalleToAttach);
            }
            evaluacionDetalleListNew = attachedEvaluacionDetalleListNew;
            evaluacion.setEvaluacionDetalleList(evaluacionDetalleListNew);
            evaluacion = em.merge(evaluacion);
            if (periodoOld != null && !periodoOld.equals(periodoNew)) {
                periodoOld.getEvaluacionList().remove(evaluacion);
                periodoOld = em.merge(periodoOld);
            }
            if (periodoNew != null && !periodoNew.equals(periodoOld)) {
                periodoNew.getEvaluacionList().add(evaluacion);
                periodoNew = em.merge(periodoNew);
            }
            if (evaluadoOld != null && !evaluadoOld.equals(evaluadoNew)) {
                evaluadoOld.getEvaluacionList().remove(evaluacion);
                evaluadoOld = em.merge(evaluadoOld);
            }
            if (evaluadoNew != null && !evaluadoNew.equals(evaluadoOld)) {
                evaluadoNew.getEvaluacionList().add(evaluacion);
                evaluadoNew = em.merge(evaluadoNew);
            }
            if (evaluadorOld != null && !evaluadorOld.equals(evaluadorNew)) {
                evaluadorOld.getEvaluacionList().remove(evaluacion);
                evaluadorOld = em.merge(evaluadorOld);
            }
            if (evaluadorNew != null && !evaluadorNew.equals(evaluadorOld)) {
                evaluadorNew.getEvaluacionList().add(evaluacion);
                evaluadorNew = em.merge(evaluadorNew);
            }
            for (EvaluacionDetalle evaluacionDetalleListNewEvaluacionDetalle : evaluacionDetalleListNew) {
                if (!evaluacionDetalleListOld.contains(evaluacionDetalleListNewEvaluacionDetalle)) {
                    Evaluacion oldEvaluacionOfEvaluacionDetalleListNewEvaluacionDetalle = evaluacionDetalleListNewEvaluacionDetalle.getEvaluacion();
                    evaluacionDetalleListNewEvaluacionDetalle.setEvaluacion(evaluacion);
                    evaluacionDetalleListNewEvaluacionDetalle = em.merge(evaluacionDetalleListNewEvaluacionDetalle);
                    if (oldEvaluacionOfEvaluacionDetalleListNewEvaluacionDetalle != null && !oldEvaluacionOfEvaluacionDetalleListNewEvaluacionDetalle.equals(evaluacion)) {
                        oldEvaluacionOfEvaluacionDetalleListNewEvaluacionDetalle.getEvaluacionDetalleList().remove(evaluacionDetalleListNewEvaluacionDetalle);
                        oldEvaluacionOfEvaluacionDetalleListNewEvaluacionDetalle = em.merge(oldEvaluacionOfEvaluacionDetalleListNewEvaluacionDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evaluacion.getEvaluacion();
                if (findEvaluacion(id) == null) {
                    throw new NonexistentEntityException("The evaluacion with id " + id + " no longer exists.");
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
            Evaluacion evaluacion;
            try {
                evaluacion = em.getReference(Evaluacion.class, id);
                evaluacion.getEvaluacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evaluacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EvaluacionDetalle> evaluacionDetalleListOrphanCheck = evaluacion.getEvaluacionDetalleList();
            for (EvaluacionDetalle evaluacionDetalleListOrphanCheckEvaluacionDetalle : evaluacionDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Evaluacion (" + evaluacion + ") cannot be destroyed since the EvaluacionDetalle " + evaluacionDetalleListOrphanCheckEvaluacionDetalle + " in its evaluacionDetalleList field has a non-nullable evaluacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Periodo periodo = evaluacion.getPeriodo();
            if (periodo != null) {
                periodo.getEvaluacionList().remove(evaluacion);
                periodo = em.merge(periodo);
            }
            Persona evaluado = evaluacion.getEvaluado();
            if (evaluado != null) {
                evaluado.getEvaluacionList().remove(evaluacion);
                evaluado = em.merge(evaluado);
            }
            Persona evaluador = evaluacion.getEvaluador();
            if (evaluador != null) {
                evaluador.getEvaluacionList().remove(evaluacion);
                evaluador = em.merge(evaluador);
            }
            em.remove(evaluacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Evaluacion> findEvaluacionEntities() {
        return findEvaluacionEntities(true, -1, -1);
    }

    public List<Evaluacion> findEvaluacionEntities(int maxResults, int firstResult) {
        return findEvaluacionEntities(false, maxResults, firstResult);
    }

    private List<Evaluacion> findEvaluacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evaluacion.class));
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

    public Evaluacion findEvaluacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evaluacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvaluacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evaluacion> rt = cq.from(Evaluacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
