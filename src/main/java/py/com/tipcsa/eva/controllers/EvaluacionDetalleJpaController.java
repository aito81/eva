/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.tipcsa.eva.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import py.com.tipcsa.eva.controllers.exceptions.NonexistentEntityException;
import py.com.tipcsa.eva.entities.Evaluacion;
import py.com.tipcsa.eva.entities.EvaluacionDetalle;
import py.com.tipcsa.eva.entities.Pregunta;
import py.com.tipcsa.eva.entities.Valor;

/**
 *
 * @author santiago
 */
public class EvaluacionDetalleJpaController implements Serializable {

    public EvaluacionDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EvaluacionDetalle evaluacionDetalle) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evaluacion evaluacion = evaluacionDetalle.getEvaluacion();
            if (evaluacion != null) {
                evaluacion = em.getReference(evaluacion.getClass(), evaluacion.getEvaluacion());
                evaluacionDetalle.setEvaluacion(evaluacion);
            }
            Pregunta pregunta = evaluacionDetalle.getPregunta();
            if (pregunta != null) {
                pregunta = em.getReference(pregunta.getClass(), pregunta.getPregunta());
                evaluacionDetalle.setPregunta(pregunta);
            }
            Valor valor = evaluacionDetalle.getValor();
            if (valor != null) {
                valor = em.getReference(valor.getClass(), valor.getValor());
                evaluacionDetalle.setValor(valor);
            }
            em.persist(evaluacionDetalle);
            if (evaluacion != null) {
                evaluacion.getEvaluacionDetalleList().add(evaluacionDetalle);
                evaluacion = em.merge(evaluacion);
            }
            if (pregunta != null) {
                pregunta.getEvaluacionDetalleList().add(evaluacionDetalle);
                pregunta = em.merge(pregunta);
            }
            if (valor != null) {
                valor.getEvaluacionDetalleList().add(evaluacionDetalle);
                valor = em.merge(valor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EvaluacionDetalle evaluacionDetalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EvaluacionDetalle persistentEvaluacionDetalle = em.find(EvaluacionDetalle.class, evaluacionDetalle.getEvaluacionDetalle());
            Evaluacion evaluacionOld = persistentEvaluacionDetalle.getEvaluacion();
            Evaluacion evaluacionNew = evaluacionDetalle.getEvaluacion();
            Pregunta preguntaOld = persistentEvaluacionDetalle.getPregunta();
            Pregunta preguntaNew = evaluacionDetalle.getPregunta();
            Valor valorOld = persistentEvaluacionDetalle.getValor();
            Valor valorNew = evaluacionDetalle.getValor();
            if (evaluacionNew != null) {
                evaluacionNew = em.getReference(evaluacionNew.getClass(), evaluacionNew.getEvaluacion());
                evaluacionDetalle.setEvaluacion(evaluacionNew);
            }
            if (preguntaNew != null) {
                preguntaNew = em.getReference(preguntaNew.getClass(), preguntaNew.getPregunta());
                evaluacionDetalle.setPregunta(preguntaNew);
            }
            if (valorNew != null) {
                valorNew = em.getReference(valorNew.getClass(), valorNew.getValor());
                evaluacionDetalle.setValor(valorNew);
            }
            evaluacionDetalle = em.merge(evaluacionDetalle);
            if (evaluacionOld != null && !evaluacionOld.equals(evaluacionNew)) {
                evaluacionOld.getEvaluacionDetalleList().remove(evaluacionDetalle);
                evaluacionOld = em.merge(evaluacionOld);
            }
            if (evaluacionNew != null && !evaluacionNew.equals(evaluacionOld)) {
                evaluacionNew.getEvaluacionDetalleList().add(evaluacionDetalle);
                evaluacionNew = em.merge(evaluacionNew);
            }
            if (preguntaOld != null && !preguntaOld.equals(preguntaNew)) {
                preguntaOld.getEvaluacionDetalleList().remove(evaluacionDetalle);
                preguntaOld = em.merge(preguntaOld);
            }
            if (preguntaNew != null && !preguntaNew.equals(preguntaOld)) {
                preguntaNew.getEvaluacionDetalleList().add(evaluacionDetalle);
                preguntaNew = em.merge(preguntaNew);
            }
            if (valorOld != null && !valorOld.equals(valorNew)) {
                valorOld.getEvaluacionDetalleList().remove(evaluacionDetalle);
                valorOld = em.merge(valorOld);
            }
            if (valorNew != null && !valorNew.equals(valorOld)) {
                valorNew.getEvaluacionDetalleList().add(evaluacionDetalle);
                valorNew = em.merge(valorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evaluacionDetalle.getEvaluacionDetalle();
                if (findEvaluacionDetalle(id) == null) {
                    throw new NonexistentEntityException("The evaluacionDetalle with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EvaluacionDetalle evaluacionDetalle;
            try {
                evaluacionDetalle = em.getReference(EvaluacionDetalle.class, id);
                evaluacionDetalle.getEvaluacionDetalle();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evaluacionDetalle with id " + id + " no longer exists.", enfe);
            }
            Evaluacion evaluacion = evaluacionDetalle.getEvaluacion();
            if (evaluacion != null) {
                evaluacion.getEvaluacionDetalleList().remove(evaluacionDetalle);
                evaluacion = em.merge(evaluacion);
            }
            Pregunta pregunta = evaluacionDetalle.getPregunta();
            if (pregunta != null) {
                pregunta.getEvaluacionDetalleList().remove(evaluacionDetalle);
                pregunta = em.merge(pregunta);
            }
            Valor valor = evaluacionDetalle.getValor();
            if (valor != null) {
                valor.getEvaluacionDetalleList().remove(evaluacionDetalle);
                valor = em.merge(valor);
            }
            em.remove(evaluacionDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EvaluacionDetalle> findEvaluacionDetalleEntities() {
        return findEvaluacionDetalleEntities(true, -1, -1);
    }

    public List<EvaluacionDetalle> findEvaluacionDetalleEntities(int maxResults, int firstResult) {
        return findEvaluacionDetalleEntities(false, maxResults, firstResult);
    }

    private List<EvaluacionDetalle> findEvaluacionDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EvaluacionDetalle.class));
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

    public EvaluacionDetalle findEvaluacionDetalle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EvaluacionDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvaluacionDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EvaluacionDetalle> rt = cq.from(EvaluacionDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
