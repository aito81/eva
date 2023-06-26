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
import py.com.tipcsa.eva.entities.EvaluadorGrupo;
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.Usuario;

/**
 *
 * @author santiago
 */
public class EvaluadorGrupoJpaController implements Serializable {

    public EvaluadorGrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EvaluadorGrupo evaluadorGrupo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupo = evaluadorGrupo.getGrupo();
            if (grupo != null) {
                grupo = em.getReference(grupo.getClass(), grupo.getGrupo());
                evaluadorGrupo.setGrupo(grupo);
            }
            Usuario evaluador = evaluadorGrupo.getEvaluador();
            if (evaluador != null) {
                evaluador = em.getReference(evaluador.getClass(), evaluador.getUsuario());
                evaluadorGrupo.setEvaluador(evaluador);
            }
            em.persist(evaluadorGrupo);
            if (grupo != null) {
                grupo.getEvaluadorGrupoList().add(evaluadorGrupo);
                grupo = em.merge(grupo);
            }
            if (evaluador != null) {
                evaluador.getEvaluadorGrupoList().add(evaluadorGrupo);
                evaluador = em.merge(evaluador);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EvaluadorGrupo evaluadorGrupo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EvaluadorGrupo persistentEvaluadorGrupo = em.find(EvaluadorGrupo.class, evaluadorGrupo.getEvaluadorGrupo());
            Grupo grupoOld = persistentEvaluadorGrupo.getGrupo();
            Grupo grupoNew = evaluadorGrupo.getGrupo();
            Usuario evaluadorOld = persistentEvaluadorGrupo.getEvaluador();
            Usuario evaluadorNew = evaluadorGrupo.getEvaluador();
            if (grupoNew != null) {
                grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getGrupo());
                evaluadorGrupo.setGrupo(grupoNew);
            }
            if (evaluadorNew != null) {
                evaluadorNew = em.getReference(evaluadorNew.getClass(), evaluadorNew.getUsuario());
                evaluadorGrupo.setEvaluador(evaluadorNew);
            }
            evaluadorGrupo = em.merge(evaluadorGrupo);
            if (grupoOld != null && !grupoOld.equals(grupoNew)) {
                grupoOld.getEvaluadorGrupoList().remove(evaluadorGrupo);
                grupoOld = em.merge(grupoOld);
            }
            if (grupoNew != null && !grupoNew.equals(grupoOld)) {
                grupoNew.getEvaluadorGrupoList().add(evaluadorGrupo);
                grupoNew = em.merge(grupoNew);
            }
            if (evaluadorOld != null && !evaluadorOld.equals(evaluadorNew)) {
                evaluadorOld.getEvaluadorGrupoList().remove(evaluadorGrupo);
                evaluadorOld = em.merge(evaluadorOld);
            }
            if (evaluadorNew != null && !evaluadorNew.equals(evaluadorOld)) {
                evaluadorNew.getEvaluadorGrupoList().add(evaluadorGrupo);
                evaluadorNew = em.merge(evaluadorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evaluadorGrupo.getEvaluadorGrupo();
                if (findEvaluadorGrupo(id) == null) {
                    throw new NonexistentEntityException("The evaluadorGrupo with id " + id + " no longer exists.");
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
            EvaluadorGrupo evaluadorGrupo;
            try {
                evaluadorGrupo = em.getReference(EvaluadorGrupo.class, id);
                evaluadorGrupo.getEvaluadorGrupo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evaluadorGrupo with id " + id + " no longer exists.", enfe);
            }
            Grupo grupo = evaluadorGrupo.getGrupo();
            if (grupo != null) {
                grupo.getEvaluadorGrupoList().remove(evaluadorGrupo);
                grupo = em.merge(grupo);
            }
            Usuario evaluador = evaluadorGrupo.getEvaluador();
            if (evaluador != null) {
                evaluador.getEvaluadorGrupoList().remove(evaluadorGrupo);
                evaluador = em.merge(evaluador);
            }
            em.remove(evaluadorGrupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EvaluadorGrupo> findEvaluadorGrupoEntities() {
        return findEvaluadorGrupoEntities(true, -1, -1);
    }

    public List<EvaluadorGrupo> findEvaluadorGrupoEntities(int maxResults, int firstResult) {
        return findEvaluadorGrupoEntities(false, maxResults, firstResult);
    }

    private List<EvaluadorGrupo> findEvaluadorGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EvaluadorGrupo.class));
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

    public EvaluadorGrupo findEvaluadorGrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EvaluadorGrupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvaluadorGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EvaluadorGrupo> rt = cq.from(EvaluadorGrupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
