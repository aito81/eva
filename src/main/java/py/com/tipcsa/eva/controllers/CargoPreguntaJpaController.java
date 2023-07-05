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
import py.com.tipcsa.eva.entities.CargoPregunta;
import py.com.tipcsa.eva.entities.Pregunta;

/**
 *
 * @author santi
 */
public class CargoPreguntaJpaController implements Serializable {

    public CargoPreguntaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CargoPregunta cargoPregunta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo cargo = cargoPregunta.getCargo();
            if (cargo != null) {
                cargo = em.getReference(cargo.getClass(), cargo.getCargo());
                cargoPregunta.setCargo(cargo);
            }
            Pregunta pregunta = cargoPregunta.getPregunta();
            if (pregunta != null) {
                pregunta = em.getReference(pregunta.getClass(), pregunta.getPregunta());
                cargoPregunta.setPregunta(pregunta);
            }
            em.persist(cargoPregunta);
            if (cargo != null) {
                cargo.getCargoPreguntaList().add(cargoPregunta);
                cargo = em.merge(cargo);
            }
            if (pregunta != null) {
                pregunta.getCargoPreguntaList().add(cargoPregunta);
                pregunta = em.merge(pregunta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CargoPregunta cargoPregunta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CargoPregunta persistentCargoPregunta = em.find(CargoPregunta.class, cargoPregunta.getCargoPregunta());
            Cargo cargoOld = persistentCargoPregunta.getCargo();
            Cargo cargoNew = cargoPregunta.getCargo();
            Pregunta preguntaOld = persistentCargoPregunta.getPregunta();
            Pregunta preguntaNew = cargoPregunta.getPregunta();
            if (cargoNew != null) {
                cargoNew = em.getReference(cargoNew.getClass(), cargoNew.getCargo());
                cargoPregunta.setCargo(cargoNew);
            }
            if (preguntaNew != null) {
                preguntaNew = em.getReference(preguntaNew.getClass(), preguntaNew.getPregunta());
                cargoPregunta.setPregunta(preguntaNew);
            }
            cargoPregunta = em.merge(cargoPregunta);
            if (cargoOld != null && !cargoOld.equals(cargoNew)) {
                cargoOld.getCargoPreguntaList().remove(cargoPregunta);
                cargoOld = em.merge(cargoOld);
            }
            if (cargoNew != null && !cargoNew.equals(cargoOld)) {
                cargoNew.getCargoPreguntaList().add(cargoPregunta);
                cargoNew = em.merge(cargoNew);
            }
            if (preguntaOld != null && !preguntaOld.equals(preguntaNew)) {
                preguntaOld.getCargoPreguntaList().remove(cargoPregunta);
                preguntaOld = em.merge(preguntaOld);
            }
            if (preguntaNew != null && !preguntaNew.equals(preguntaOld)) {
                preguntaNew.getCargoPreguntaList().add(cargoPregunta);
                preguntaNew = em.merge(preguntaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cargoPregunta.getCargoPregunta();
                if (findCargoPregunta(id) == null) {
                    throw new NonexistentEntityException("The cargoPregunta with id " + id + " no longer exists.");
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
            CargoPregunta cargoPregunta;
            try {
                cargoPregunta = em.getReference(CargoPregunta.class, id);
                cargoPregunta.getCargoPregunta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargoPregunta with id " + id + " no longer exists.", enfe);
            }
            Cargo cargo = cargoPregunta.getCargo();
            if (cargo != null) {
                cargo.getCargoPreguntaList().remove(cargoPregunta);
                cargo = em.merge(cargo);
            }
            Pregunta pregunta = cargoPregunta.getPregunta();
            if (pregunta != null) {
                pregunta.getCargoPreguntaList().remove(cargoPregunta);
                pregunta = em.merge(pregunta);
            }
            em.remove(cargoPregunta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CargoPregunta> findCargoPreguntaEntities() {
        return findCargoPreguntaEntities(true, -1, -1);
    }

    public List<CargoPregunta> findCargoPreguntaEntities(int maxResults, int firstResult) {
        return findCargoPreguntaEntities(false, maxResults, firstResult);
    }

    private List<CargoPregunta> findCargoPreguntaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CargoPregunta.class));
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

    public CargoPregunta findCargoPregunta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CargoPregunta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargoPreguntaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CargoPregunta> rt = cq.from(CargoPregunta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
