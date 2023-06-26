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
import py.com.tipcsa.eva.entities.Perfil;
import py.com.tipcsa.eva.entities.PerfilUsuario;
import py.com.tipcsa.eva.entities.Usuario;

/**
 *
 * @author santiago
 */
public class PerfilUsuarioJpaController implements Serializable {

    public PerfilUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PerfilUsuario perfilUsuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perfil perfil = perfilUsuario.getPerfil();
            if (perfil != null) {
                perfil = em.getReference(perfil.getClass(), perfil.getPerfil());
                perfilUsuario.setPerfil(perfil);
            }
            Usuario usuario = perfilUsuario.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                perfilUsuario.setUsuario(usuario);
            }
            em.persist(perfilUsuario);
            if (perfil != null) {
                perfil.getPerfilUsuarioList().add(perfilUsuario);
                perfil = em.merge(perfil);
            }
            if (usuario != null) {
                usuario.getPerfilUsuarioList().add(perfilUsuario);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PerfilUsuario perfilUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PerfilUsuario persistentPerfilUsuario = em.find(PerfilUsuario.class, perfilUsuario.getPerfilUsuario());
            Perfil perfilOld = persistentPerfilUsuario.getPerfil();
            Perfil perfilNew = perfilUsuario.getPerfil();
            Usuario usuarioOld = persistentPerfilUsuario.getUsuario();
            Usuario usuarioNew = perfilUsuario.getUsuario();
            if (perfilNew != null) {
                perfilNew = em.getReference(perfilNew.getClass(), perfilNew.getPerfil());
                perfilUsuario.setPerfil(perfilNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                perfilUsuario.setUsuario(usuarioNew);
            }
            perfilUsuario = em.merge(perfilUsuario);
            if (perfilOld != null && !perfilOld.equals(perfilNew)) {
                perfilOld.getPerfilUsuarioList().remove(perfilUsuario);
                perfilOld = em.merge(perfilOld);
            }
            if (perfilNew != null && !perfilNew.equals(perfilOld)) {
                perfilNew.getPerfilUsuarioList().add(perfilUsuario);
                perfilNew = em.merge(perfilNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getPerfilUsuarioList().remove(perfilUsuario);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getPerfilUsuarioList().add(perfilUsuario);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = perfilUsuario.getPerfilUsuario();
                if (findPerfilUsuario(id) == null) {
                    throw new NonexistentEntityException("The perfilUsuario with id " + id + " no longer exists.");
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
            PerfilUsuario perfilUsuario;
            try {
                perfilUsuario = em.getReference(PerfilUsuario.class, id);
                perfilUsuario.getPerfilUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perfilUsuario with id " + id + " no longer exists.", enfe);
            }
            Perfil perfil = perfilUsuario.getPerfil();
            if (perfil != null) {
                perfil.getPerfilUsuarioList().remove(perfilUsuario);
                perfil = em.merge(perfil);
            }
            Usuario usuario = perfilUsuario.getUsuario();
            if (usuario != null) {
                usuario.getPerfilUsuarioList().remove(perfilUsuario);
                usuario = em.merge(usuario);
            }
            em.remove(perfilUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PerfilUsuario> findPerfilUsuarioEntities() {
        return findPerfilUsuarioEntities(true, -1, -1);
    }

    public List<PerfilUsuario> findPerfilUsuarioEntities(int maxResults, int firstResult) {
        return findPerfilUsuarioEntities(false, maxResults, firstResult);
    }

    private List<PerfilUsuario> findPerfilUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PerfilUsuario.class));
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

    public PerfilUsuario findPerfilUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PerfilUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerfilUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PerfilUsuario> rt = cq.from(PerfilUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
