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
import py.com.tipcsa.eva.entities.PerfilUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.tipcsa.eva.controllers.exceptions.IllegalOrphanException;
import py.com.tipcsa.eva.controllers.exceptions.NonexistentEntityException;
import py.com.tipcsa.eva.entities.Perfil;

/**
 *
 * @author santiago
 */
public class PerfilJpaController implements Serializable {

    public PerfilJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Perfil perfil) {
        if (perfil.getPerfilUsuarioList() == null) {
            perfil.setPerfilUsuarioList(new ArrayList<PerfilUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PerfilUsuario> attachedPerfilUsuarioList = new ArrayList<PerfilUsuario>();
            for (PerfilUsuario perfilUsuarioListPerfilUsuarioToAttach : perfil.getPerfilUsuarioList()) {
                perfilUsuarioListPerfilUsuarioToAttach = em.getReference(perfilUsuarioListPerfilUsuarioToAttach.getClass(), perfilUsuarioListPerfilUsuarioToAttach.getPerfilUsuario());
                attachedPerfilUsuarioList.add(perfilUsuarioListPerfilUsuarioToAttach);
            }
            perfil.setPerfilUsuarioList(attachedPerfilUsuarioList);
            em.persist(perfil);
            for (PerfilUsuario perfilUsuarioListPerfilUsuario : perfil.getPerfilUsuarioList()) {
                Perfil oldPerfilOfPerfilUsuarioListPerfilUsuario = perfilUsuarioListPerfilUsuario.getPerfil();
                perfilUsuarioListPerfilUsuario.setPerfil(perfil);
                perfilUsuarioListPerfilUsuario = em.merge(perfilUsuarioListPerfilUsuario);
                if (oldPerfilOfPerfilUsuarioListPerfilUsuario != null) {
                    oldPerfilOfPerfilUsuarioListPerfilUsuario.getPerfilUsuarioList().remove(perfilUsuarioListPerfilUsuario);
                    oldPerfilOfPerfilUsuarioListPerfilUsuario = em.merge(oldPerfilOfPerfilUsuarioListPerfilUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Perfil perfil) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perfil persistentPerfil = em.find(Perfil.class, perfil.getPerfil());
            List<PerfilUsuario> perfilUsuarioListOld = persistentPerfil.getPerfilUsuarioList();
            List<PerfilUsuario> perfilUsuarioListNew = perfil.getPerfilUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (PerfilUsuario perfilUsuarioListOldPerfilUsuario : perfilUsuarioListOld) {
                if (!perfilUsuarioListNew.contains(perfilUsuarioListOldPerfilUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PerfilUsuario " + perfilUsuarioListOldPerfilUsuario + " since its perfil field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PerfilUsuario> attachedPerfilUsuarioListNew = new ArrayList<PerfilUsuario>();
            for (PerfilUsuario perfilUsuarioListNewPerfilUsuarioToAttach : perfilUsuarioListNew) {
                perfilUsuarioListNewPerfilUsuarioToAttach = em.getReference(perfilUsuarioListNewPerfilUsuarioToAttach.getClass(), perfilUsuarioListNewPerfilUsuarioToAttach.getPerfilUsuario());
                attachedPerfilUsuarioListNew.add(perfilUsuarioListNewPerfilUsuarioToAttach);
            }
            perfilUsuarioListNew = attachedPerfilUsuarioListNew;
            perfil.setPerfilUsuarioList(perfilUsuarioListNew);
            perfil = em.merge(perfil);
            for (PerfilUsuario perfilUsuarioListNewPerfilUsuario : perfilUsuarioListNew) {
                if (!perfilUsuarioListOld.contains(perfilUsuarioListNewPerfilUsuario)) {
                    Perfil oldPerfilOfPerfilUsuarioListNewPerfilUsuario = perfilUsuarioListNewPerfilUsuario.getPerfil();
                    perfilUsuarioListNewPerfilUsuario.setPerfil(perfil);
                    perfilUsuarioListNewPerfilUsuario = em.merge(perfilUsuarioListNewPerfilUsuario);
                    if (oldPerfilOfPerfilUsuarioListNewPerfilUsuario != null && !oldPerfilOfPerfilUsuarioListNewPerfilUsuario.equals(perfil)) {
                        oldPerfilOfPerfilUsuarioListNewPerfilUsuario.getPerfilUsuarioList().remove(perfilUsuarioListNewPerfilUsuario);
                        oldPerfilOfPerfilUsuarioListNewPerfilUsuario = em.merge(oldPerfilOfPerfilUsuarioListNewPerfilUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = perfil.getPerfil();
                if (findPerfil(id) == null) {
                    throw new NonexistentEntityException("The perfil with id " + id + " no longer exists.");
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
            Perfil perfil;
            try {
                perfil = em.getReference(Perfil.class, id);
                perfil.getPerfil();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perfil with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PerfilUsuario> perfilUsuarioListOrphanCheck = perfil.getPerfilUsuarioList();
            for (PerfilUsuario perfilUsuarioListOrphanCheckPerfilUsuario : perfilUsuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Perfil (" + perfil + ") cannot be destroyed since the PerfilUsuario " + perfilUsuarioListOrphanCheckPerfilUsuario + " in its perfilUsuarioList field has a non-nullable perfil field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(perfil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Perfil> findPerfilEntities() {
        return findPerfilEntities(true, -1, -1);
    }

    public List<Perfil> findPerfilEntities(int maxResults, int firstResult) {
        return findPerfilEntities(false, maxResults, firstResult);
    }

    private List<Perfil> findPerfilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Perfil.class));
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

    public Perfil findPerfil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Perfil.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerfilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Perfil> rt = cq.from(Perfil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
