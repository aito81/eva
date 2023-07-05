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
import py.com.tipcsa.eva.entities.NivelAcceso;
import py.com.tipcsa.eva.entities.Perfil;
import py.com.tipcsa.eva.entities.Persona;
import py.com.tipcsa.eva.entities.Usuario;

/**
 *
 * @author santi
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NivelAcceso nivelAcceso = usuario.getNivelAcceso();
            if (nivelAcceso != null) {
                nivelAcceso = em.getReference(nivelAcceso.getClass(), nivelAcceso.getNivelAcceso());
                usuario.setNivelAcceso(nivelAcceso);
            }
            Perfil perfil = usuario.getPerfil();
            if (perfil != null) {
                perfil = em.getReference(perfil.getClass(), perfil.getPerfil());
                usuario.setPerfil(perfil);
            }
            Persona persona = usuario.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getPersona());
                usuario.setPersona(persona);
            }
            em.persist(usuario);
            if (nivelAcceso != null) {
                nivelAcceso.getUsuarioList().add(usuario);
                nivelAcceso = em.merge(nivelAcceso);
            }
            if (perfil != null) {
                perfil.getUsuarioList().add(usuario);
                perfil = em.merge(perfil);
            }
            if (persona != null) {
                persona.getUsuarioList().add(usuario);
                persona = em.merge(persona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuario());
            NivelAcceso nivelAccesoOld = persistentUsuario.getNivelAcceso();
            NivelAcceso nivelAccesoNew = usuario.getNivelAcceso();
            Perfil perfilOld = persistentUsuario.getPerfil();
            Perfil perfilNew = usuario.getPerfil();
            Persona personaOld = persistentUsuario.getPersona();
            Persona personaNew = usuario.getPersona();
            if (nivelAccesoNew != null) {
                nivelAccesoNew = em.getReference(nivelAccesoNew.getClass(), nivelAccesoNew.getNivelAcceso());
                usuario.setNivelAcceso(nivelAccesoNew);
            }
            if (perfilNew != null) {
                perfilNew = em.getReference(perfilNew.getClass(), perfilNew.getPerfil());
                usuario.setPerfil(perfilNew);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getPersona());
                usuario.setPersona(personaNew);
            }
            usuario = em.merge(usuario);
            if (nivelAccesoOld != null && !nivelAccesoOld.equals(nivelAccesoNew)) {
                nivelAccesoOld.getUsuarioList().remove(usuario);
                nivelAccesoOld = em.merge(nivelAccesoOld);
            }
            if (nivelAccesoNew != null && !nivelAccesoNew.equals(nivelAccesoOld)) {
                nivelAccesoNew.getUsuarioList().add(usuario);
                nivelAccesoNew = em.merge(nivelAccesoNew);
            }
            if (perfilOld != null && !perfilOld.equals(perfilNew)) {
                perfilOld.getUsuarioList().remove(usuario);
                perfilOld = em.merge(perfilOld);
            }
            if (perfilNew != null && !perfilNew.equals(perfilOld)) {
                perfilNew.getUsuarioList().add(usuario);
                perfilNew = em.merge(perfilNew);
            }
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getUsuarioList().remove(usuario);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getUsuarioList().add(usuario);
                personaNew = em.merge(personaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            NivelAcceso nivelAcceso = usuario.getNivelAcceso();
            if (nivelAcceso != null) {
                nivelAcceso.getUsuarioList().remove(usuario);
                nivelAcceso = em.merge(nivelAcceso);
            }
            Perfil perfil = usuario.getPerfil();
            if (perfil != null) {
                perfil.getUsuarioList().remove(usuario);
                perfil = em.merge(perfil);
            }
            Persona persona = usuario.getPersona();
            if (persona != null) {
                persona.getUsuarioList().remove(usuario);
                persona = em.merge(persona);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
