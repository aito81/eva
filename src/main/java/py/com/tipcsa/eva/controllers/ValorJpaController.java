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
import py.com.tipcsa.eva.entities.EvaluacionDetalle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.tipcsa.eva.controllers.exceptions.IllegalOrphanException;
import py.com.tipcsa.eva.controllers.exceptions.NonexistentEntityException;
import py.com.tipcsa.eva.entities.Valor;

/**
 *
 * @author santiago
 */
public class ValorJpaController implements Serializable {

    public ValorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Valor valor) {
        if (valor.getEvaluacionDetalleList() == null) {
            valor.setEvaluacionDetalleList(new ArrayList<EvaluacionDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EvaluacionDetalle> attachedEvaluacionDetalleList = new ArrayList<EvaluacionDetalle>();
            for (EvaluacionDetalle evaluacionDetalleListEvaluacionDetalleToAttach : valor.getEvaluacionDetalleList()) {
                evaluacionDetalleListEvaluacionDetalleToAttach = em.getReference(evaluacionDetalleListEvaluacionDetalleToAttach.getClass(), evaluacionDetalleListEvaluacionDetalleToAttach.getEvaluacionDetalle());
                attachedEvaluacionDetalleList.add(evaluacionDetalleListEvaluacionDetalleToAttach);
            }
            valor.setEvaluacionDetalleList(attachedEvaluacionDetalleList);
            em.persist(valor);
            for (EvaluacionDetalle evaluacionDetalleListEvaluacionDetalle : valor.getEvaluacionDetalleList()) {
                Valor oldValorOfEvaluacionDetalleListEvaluacionDetalle = evaluacionDetalleListEvaluacionDetalle.getValor();
                evaluacionDetalleListEvaluacionDetalle.setValor(valor);
                evaluacionDetalleListEvaluacionDetalle = em.merge(evaluacionDetalleListEvaluacionDetalle);
                if (oldValorOfEvaluacionDetalleListEvaluacionDetalle != null) {
                    oldValorOfEvaluacionDetalleListEvaluacionDetalle.getEvaluacionDetalleList().remove(evaluacionDetalleListEvaluacionDetalle);
                    oldValorOfEvaluacionDetalleListEvaluacionDetalle = em.merge(oldValorOfEvaluacionDetalleListEvaluacionDetalle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Valor valor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Valor persistentValor = em.find(Valor.class, valor.getValor());
            List<EvaluacionDetalle> evaluacionDetalleListOld = persistentValor.getEvaluacionDetalleList();
            List<EvaluacionDetalle> evaluacionDetalleListNew = valor.getEvaluacionDetalleList();
            List<String> illegalOrphanMessages = null;
            for (EvaluacionDetalle evaluacionDetalleListOldEvaluacionDetalle : evaluacionDetalleListOld) {
                if (!evaluacionDetalleListNew.contains(evaluacionDetalleListOldEvaluacionDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvaluacionDetalle " + evaluacionDetalleListOldEvaluacionDetalle + " since its valor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<EvaluacionDetalle> attachedEvaluacionDetalleListNew = new ArrayList<EvaluacionDetalle>();
            for (EvaluacionDetalle evaluacionDetalleListNewEvaluacionDetalleToAttach : evaluacionDetalleListNew) {
                evaluacionDetalleListNewEvaluacionDetalleToAttach = em.getReference(evaluacionDetalleListNewEvaluacionDetalleToAttach.getClass(), evaluacionDetalleListNewEvaluacionDetalleToAttach.getEvaluacionDetalle());
                attachedEvaluacionDetalleListNew.add(evaluacionDetalleListNewEvaluacionDetalleToAttach);
            }
            evaluacionDetalleListNew = attachedEvaluacionDetalleListNew;
            valor.setEvaluacionDetalleList(evaluacionDetalleListNew);
            valor = em.merge(valor);
            for (EvaluacionDetalle evaluacionDetalleListNewEvaluacionDetalle : evaluacionDetalleListNew) {
                if (!evaluacionDetalleListOld.contains(evaluacionDetalleListNewEvaluacionDetalle)) {
                    Valor oldValorOfEvaluacionDetalleListNewEvaluacionDetalle = evaluacionDetalleListNewEvaluacionDetalle.getValor();
                    evaluacionDetalleListNewEvaluacionDetalle.setValor(valor);
                    evaluacionDetalleListNewEvaluacionDetalle = em.merge(evaluacionDetalleListNewEvaluacionDetalle);
                    if (oldValorOfEvaluacionDetalleListNewEvaluacionDetalle != null && !oldValorOfEvaluacionDetalleListNewEvaluacionDetalle.equals(valor)) {
                        oldValorOfEvaluacionDetalleListNewEvaluacionDetalle.getEvaluacionDetalleList().remove(evaluacionDetalleListNewEvaluacionDetalle);
                        oldValorOfEvaluacionDetalleListNewEvaluacionDetalle = em.merge(oldValorOfEvaluacionDetalleListNewEvaluacionDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = valor.getValor();
                if (findValor(id) == null) {
                    throw new NonexistentEntityException("The valor with id " + id + " no longer exists.");
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
            Valor valor;
            try {
                valor = em.getReference(Valor.class, id);
                valor.getValor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The valor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EvaluacionDetalle> evaluacionDetalleListOrphanCheck = valor.getEvaluacionDetalleList();
            for (EvaluacionDetalle evaluacionDetalleListOrphanCheckEvaluacionDetalle : evaluacionDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Valor (" + valor + ") cannot be destroyed since the EvaluacionDetalle " + evaluacionDetalleListOrphanCheckEvaluacionDetalle + " in its evaluacionDetalleList field has a non-nullable valor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(valor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Valor> findValorEntities() {
        return findValorEntities(true, -1, -1);
    }

    public List<Valor> findValorEntities(int maxResults, int firstResult) {
        return findValorEntities(false, maxResults, firstResult);
    }

    private List<Valor> findValorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Valor.class));
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

    public Valor findValor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Valor.class, id);
        } finally {
            em.close();
        }
    }

    public int getValorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Valor> rt = cq.from(Valor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
