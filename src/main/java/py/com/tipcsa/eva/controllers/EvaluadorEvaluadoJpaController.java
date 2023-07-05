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
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.EvaluadorEvaluado;
import py.com.tipcsa.eva.entities.Persona;

/**
 *
 * @author santi
 */
public class EvaluadorEvaluadoJpaController implements Serializable {

    public EvaluadorEvaluadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EvaluadorEvaluado evaluadorEvaluado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo evaluador = evaluadorEvaluado.getEvaluador();
            if (evaluador != null) {
                evaluador = em.getReference(evaluador.getClass(), evaluador.getCargo());
                evaluadorEvaluado.setEvaluador(evaluador);
            }
            Persona evaluado = evaluadorEvaluado.getEvaluado();
            if (evaluado != null) {
                evaluado = em.getReference(evaluado.getClass(), evaluado.getPersona());
                evaluadorEvaluado.setEvaluado(evaluado);
            }
            em.persist(evaluadorEvaluado);
            if (evaluador != null) {
                evaluador.getEvaluadorEvaluadoList().add(evaluadorEvaluado);
                evaluador = em.merge(evaluador);
            }
            if (evaluado != null) {
                evaluado.getEvaluadorEvaluadoList().add(evaluadorEvaluado);
                evaluado = em.merge(evaluado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EvaluadorEvaluado evaluadorEvaluado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EvaluadorEvaluado persistentEvaluadorEvaluado = em.find(EvaluadorEvaluado.class, evaluadorEvaluado.getEvaluadorEvaluado());
            Cargo evaluadorOld = persistentEvaluadorEvaluado.getEvaluador();
            Cargo evaluadorNew = evaluadorEvaluado.getEvaluador();
            Persona evaluadoOld = persistentEvaluadorEvaluado.getEvaluado();
            Persona evaluadoNew = evaluadorEvaluado.getEvaluado();
            if (evaluadorNew != null) {
                evaluadorNew = em.getReference(evaluadorNew.getClass(), evaluadorNew.getCargo());
                evaluadorEvaluado.setEvaluador(evaluadorNew);
            }
            if (evaluadoNew != null) {
                evaluadoNew = em.getReference(evaluadoNew.getClass(), evaluadoNew.getPersona());
                evaluadorEvaluado.setEvaluado(evaluadoNew);
            }
            evaluadorEvaluado = em.merge(evaluadorEvaluado);
            if (evaluadorOld != null && !evaluadorOld.equals(evaluadorNew)) {
                evaluadorOld.getEvaluadorEvaluadoList().remove(evaluadorEvaluado);
                evaluadorOld = em.merge(evaluadorOld);
            }
            if (evaluadorNew != null && !evaluadorNew.equals(evaluadorOld)) {
                evaluadorNew.getEvaluadorEvaluadoList().add(evaluadorEvaluado);
                evaluadorNew = em.merge(evaluadorNew);
            }
            if (evaluadoOld != null && !evaluadoOld.equals(evaluadoNew)) {
                evaluadoOld.getEvaluadorEvaluadoList().remove(evaluadorEvaluado);
                evaluadoOld = em.merge(evaluadoOld);
            }
            if (evaluadoNew != null && !evaluadoNew.equals(evaluadoOld)) {
                evaluadoNew.getEvaluadorEvaluadoList().add(evaluadorEvaluado);
                evaluadoNew = em.merge(evaluadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evaluadorEvaluado.getEvaluadorEvaluado();
                if (findEvaluadorEvaluado(id) == null) {
                    throw new NonexistentEntityException("The evaluadorEvaluado with id " + id + " no longer exists.");
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
            EvaluadorEvaluado evaluadorEvaluado;
            try {
                evaluadorEvaluado = em.getReference(EvaluadorEvaluado.class, id);
                evaluadorEvaluado.getEvaluadorEvaluado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evaluadorEvaluado with id " + id + " no longer exists.", enfe);
            }
            Cargo evaluador = evaluadorEvaluado.getEvaluador();
            if (evaluador != null) {
                evaluador.getEvaluadorEvaluadoList().remove(evaluadorEvaluado);
                evaluador = em.merge(evaluador);
            }
            Persona evaluado = evaluadorEvaluado.getEvaluado();
            if (evaluado != null) {
                evaluado.getEvaluadorEvaluadoList().remove(evaluadorEvaluado);
                evaluado = em.merge(evaluado);
            }
            em.remove(evaluadorEvaluado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EvaluadorEvaluado> findEvaluadorEvaluadoEntities() {
        return findEvaluadorEvaluadoEntities(true, -1, -1);
    }

    public List<EvaluadorEvaluado> findEvaluadorEvaluadoEntities(int maxResults, int firstResult) {
        return findEvaluadorEvaluadoEntities(false, maxResults, firstResult);
    }

    private List<EvaluadorEvaluado> findEvaluadorEvaluadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EvaluadorEvaluado.class));
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

    public EvaluadorEvaluado findEvaluadorEvaluado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EvaluadorEvaluado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvaluadorEvaluadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EvaluadorEvaluado> rt = cq.from(EvaluadorEvaluado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
