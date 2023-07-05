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
import py.com.tipcsa.eva.entities.EvaluadorCargo;

/**
 *
 * @author santi
 */
public class EvaluadorCargoJpaController implements Serializable {

    public EvaluadorCargoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EvaluadorCargo evaluadorCargo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo cargo = evaluadorCargo.getCargo();
            if (cargo != null) {
                cargo = em.getReference(cargo.getClass(), cargo.getCargo());
                evaluadorCargo.setCargo(cargo);
            }
            Cargo evaluador = evaluadorCargo.getEvaluador();
            if (evaluador != null) {
                evaluador = em.getReference(evaluador.getClass(), evaluador.getCargo());
                evaluadorCargo.setEvaluador(evaluador);
            }
            em.persist(evaluadorCargo);
            if (cargo != null) {
                cargo.getEvaluadorCargoList().add(evaluadorCargo);
                cargo = em.merge(cargo);
            }
            if (evaluador != null) {
                evaluador.getEvaluadorCargoList().add(evaluadorCargo);
                evaluador = em.merge(evaluador);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EvaluadorCargo evaluadorCargo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EvaluadorCargo persistentEvaluadorCargo = em.find(EvaluadorCargo.class, evaluadorCargo.getEvaluadorCargo());
            Cargo cargoOld = persistentEvaluadorCargo.getCargo();
            Cargo cargoNew = evaluadorCargo.getCargo();
            Cargo evaluadorOld = persistentEvaluadorCargo.getEvaluador();
            Cargo evaluadorNew = evaluadorCargo.getEvaluador();
            if (cargoNew != null) {
                cargoNew = em.getReference(cargoNew.getClass(), cargoNew.getCargo());
                evaluadorCargo.setCargo(cargoNew);
            }
            if (evaluadorNew != null) {
                evaluadorNew = em.getReference(evaluadorNew.getClass(), evaluadorNew.getCargo());
                evaluadorCargo.setEvaluador(evaluadorNew);
            }
            evaluadorCargo = em.merge(evaluadorCargo);
            if (cargoOld != null && !cargoOld.equals(cargoNew)) {
                cargoOld.getEvaluadorCargoList().remove(evaluadorCargo);
                cargoOld = em.merge(cargoOld);
            }
            if (cargoNew != null && !cargoNew.equals(cargoOld)) {
                cargoNew.getEvaluadorCargoList().add(evaluadorCargo);
                cargoNew = em.merge(cargoNew);
            }
            if (evaluadorOld != null && !evaluadorOld.equals(evaluadorNew)) {
                evaluadorOld.getEvaluadorCargoList().remove(evaluadorCargo);
                evaluadorOld = em.merge(evaluadorOld);
            }
            if (evaluadorNew != null && !evaluadorNew.equals(evaluadorOld)) {
                evaluadorNew.getEvaluadorCargoList().add(evaluadorCargo);
                evaluadorNew = em.merge(evaluadorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evaluadorCargo.getEvaluadorCargo();
                if (findEvaluadorCargo(id) == null) {
                    throw new NonexistentEntityException("The evaluadorCargo with id " + id + " no longer exists.");
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
            EvaluadorCargo evaluadorCargo;
            try {
                evaluadorCargo = em.getReference(EvaluadorCargo.class, id);
                evaluadorCargo.getEvaluadorCargo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evaluadorCargo with id " + id + " no longer exists.", enfe);
            }
            Cargo cargo = evaluadorCargo.getCargo();
            if (cargo != null) {
                cargo.getEvaluadorCargoList().remove(evaluadorCargo);
                cargo = em.merge(cargo);
            }
            Cargo evaluador = evaluadorCargo.getEvaluador();
            if (evaluador != null) {
                evaluador.getEvaluadorCargoList().remove(evaluadorCargo);
                evaluador = em.merge(evaluador);
            }
            em.remove(evaluadorCargo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EvaluadorCargo> findEvaluadorCargoEntities() {
        return findEvaluadorCargoEntities(true, -1, -1);
    }

    public List<EvaluadorCargo> findEvaluadorCargoEntities(int maxResults, int firstResult) {
        return findEvaluadorCargoEntities(false, maxResults, firstResult);
    }

    private List<EvaluadorCargo> findEvaluadorCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EvaluadorCargo.class));
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

    public EvaluadorCargo findEvaluadorCargo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EvaluadorCargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvaluadorCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EvaluadorCargo> rt = cq.from(EvaluadorCargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
