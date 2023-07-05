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
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.PuestoGrupo;

/**
 *
 * @author santi
 */
public class PuestoGrupoJpaController implements Serializable {

    public PuestoGrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PuestoGrupo puestoGrupo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo puesto = puestoGrupo.getPuesto();
            if (puesto != null) {
                puesto = em.getReference(puesto.getClass(), puesto.getCargo());
                puestoGrupo.setPuesto(puesto);
            }
            Grupo grupo = puestoGrupo.getGrupo();
            if (grupo != null) {
                grupo = em.getReference(grupo.getClass(), grupo.getGrupo());
                puestoGrupo.setGrupo(grupo);
            }
            em.persist(puestoGrupo);
            if (puesto != null) {
                puesto.getPuestoGrupoList().add(puestoGrupo);
                puesto = em.merge(puesto);
            }
            if (grupo != null) {
                grupo.getPuestoGrupoList().add(puestoGrupo);
                grupo = em.merge(grupo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PuestoGrupo puestoGrupo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PuestoGrupo persistentPuestoGrupo = em.find(PuestoGrupo.class, puestoGrupo.getPuestoGrupo());
            Cargo puestoOld = persistentPuestoGrupo.getPuesto();
            Cargo puestoNew = puestoGrupo.getPuesto();
            Grupo grupoOld = persistentPuestoGrupo.getGrupo();
            Grupo grupoNew = puestoGrupo.getGrupo();
            if (puestoNew != null) {
                puestoNew = em.getReference(puestoNew.getClass(), puestoNew.getCargo());
                puestoGrupo.setPuesto(puestoNew);
            }
            if (grupoNew != null) {
                grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getGrupo());
                puestoGrupo.setGrupo(grupoNew);
            }
            puestoGrupo = em.merge(puestoGrupo);
            if (puestoOld != null && !puestoOld.equals(puestoNew)) {
                puestoOld.getPuestoGrupoList().remove(puestoGrupo);
                puestoOld = em.merge(puestoOld);
            }
            if (puestoNew != null && !puestoNew.equals(puestoOld)) {
                puestoNew.getPuestoGrupoList().add(puestoGrupo);
                puestoNew = em.merge(puestoNew);
            }
            if (grupoOld != null && !grupoOld.equals(grupoNew)) {
                grupoOld.getPuestoGrupoList().remove(puestoGrupo);
                grupoOld = em.merge(grupoOld);
            }
            if (grupoNew != null && !grupoNew.equals(grupoOld)) {
                grupoNew.getPuestoGrupoList().add(puestoGrupo);
                grupoNew = em.merge(grupoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = puestoGrupo.getPuestoGrupo();
                if (findPuestoGrupo(id) == null) {
                    throw new NonexistentEntityException("The puestoGrupo with id " + id + " no longer exists.");
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
            PuestoGrupo puestoGrupo;
            try {
                puestoGrupo = em.getReference(PuestoGrupo.class, id);
                puestoGrupo.getPuestoGrupo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The puestoGrupo with id " + id + " no longer exists.", enfe);
            }
            Cargo puesto = puestoGrupo.getPuesto();
            if (puesto != null) {
                puesto.getPuestoGrupoList().remove(puestoGrupo);
                puesto = em.merge(puesto);
            }
            Grupo grupo = puestoGrupo.getGrupo();
            if (grupo != null) {
                grupo.getPuestoGrupoList().remove(puestoGrupo);
                grupo = em.merge(grupo);
            }
            em.remove(puestoGrupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PuestoGrupo> findPuestoGrupoEntities() {
        return findPuestoGrupoEntities(true, -1, -1);
    }

    public List<PuestoGrupo> findPuestoGrupoEntities(int maxResults, int firstResult) {
        return findPuestoGrupoEntities(false, maxResults, firstResult);
    }

    private List<PuestoGrupo> findPuestoGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PuestoGrupo.class));
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

    public PuestoGrupo findPuestoGrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PuestoGrupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPuestoGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PuestoGrupo> rt = cq.from(PuestoGrupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
