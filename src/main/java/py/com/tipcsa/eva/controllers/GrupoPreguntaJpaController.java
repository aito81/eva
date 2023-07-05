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
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.GrupoPregunta;
import py.com.tipcsa.eva.entities.Pregunta;

/**
 *
 * @author santi
 */
public class GrupoPreguntaJpaController implements Serializable {

    public GrupoPreguntaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GrupoPregunta grupoPregunta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupo = grupoPregunta.getGrupo();
            if (grupo != null) {
                grupo = em.getReference(grupo.getClass(), grupo.getGrupo());
                grupoPregunta.setGrupo(grupo);
            }
            Pregunta pregunta = grupoPregunta.getPregunta();
            if (pregunta != null) {
                pregunta = em.getReference(pregunta.getClass(), pregunta.getPregunta());
                grupoPregunta.setPregunta(pregunta);
            }
            em.persist(grupoPregunta);
            if (grupo != null) {
                grupo.getGrupoPreguntaList().add(grupoPregunta);
                grupo = em.merge(grupo);
            }
            if (pregunta != null) {
                pregunta.getGrupoPreguntaList().add(grupoPregunta);
                pregunta = em.merge(pregunta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GrupoPregunta grupoPregunta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoPregunta persistentGrupoPregunta = em.find(GrupoPregunta.class, grupoPregunta.getGrupoPregunta());
            Grupo grupoOld = persistentGrupoPregunta.getGrupo();
            Grupo grupoNew = grupoPregunta.getGrupo();
            Pregunta preguntaOld = persistentGrupoPregunta.getPregunta();
            Pregunta preguntaNew = grupoPregunta.getPregunta();
            if (grupoNew != null) {
                grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getGrupo());
                grupoPregunta.setGrupo(grupoNew);
            }
            if (preguntaNew != null) {
                preguntaNew = em.getReference(preguntaNew.getClass(), preguntaNew.getPregunta());
                grupoPregunta.setPregunta(preguntaNew);
            }
            grupoPregunta = em.merge(grupoPregunta);
            if (grupoOld != null && !grupoOld.equals(grupoNew)) {
                grupoOld.getGrupoPreguntaList().remove(grupoPregunta);
                grupoOld = em.merge(grupoOld);
            }
            if (grupoNew != null && !grupoNew.equals(grupoOld)) {
                grupoNew.getGrupoPreguntaList().add(grupoPregunta);
                grupoNew = em.merge(grupoNew);
            }
            if (preguntaOld != null && !preguntaOld.equals(preguntaNew)) {
                preguntaOld.getGrupoPreguntaList().remove(grupoPregunta);
                preguntaOld = em.merge(preguntaOld);
            }
            if (preguntaNew != null && !preguntaNew.equals(preguntaOld)) {
                preguntaNew.getGrupoPreguntaList().add(grupoPregunta);
                preguntaNew = em.merge(preguntaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupoPregunta.getGrupoPregunta();
                if (findGrupoPregunta(id) == null) {
                    throw new NonexistentEntityException("The grupoPregunta with id " + id + " no longer exists.");
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
            GrupoPregunta grupoPregunta;
            try {
                grupoPregunta = em.getReference(GrupoPregunta.class, id);
                grupoPregunta.getGrupoPregunta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupoPregunta with id " + id + " no longer exists.", enfe);
            }
            Grupo grupo = grupoPregunta.getGrupo();
            if (grupo != null) {
                grupo.getGrupoPreguntaList().remove(grupoPregunta);
                grupo = em.merge(grupo);
            }
            Pregunta pregunta = grupoPregunta.getPregunta();
            if (pregunta != null) {
                pregunta.getGrupoPreguntaList().remove(grupoPregunta);
                pregunta = em.merge(pregunta);
            }
            em.remove(grupoPregunta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GrupoPregunta> findGrupoPreguntaEntities() {
        return findGrupoPreguntaEntities(true, -1, -1);
    }

    public List<GrupoPregunta> findGrupoPreguntaEntities(int maxResults, int firstResult) {
        return findGrupoPreguntaEntities(false, maxResults, firstResult);
    }

    private List<GrupoPregunta> findGrupoPreguntaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GrupoPregunta.class));
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

    public GrupoPregunta findGrupoPregunta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GrupoPregunta.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoPreguntaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GrupoPregunta> rt = cq.from(GrupoPregunta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
