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
import py.com.tipcsa.eva.entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.tipcsa.eva.controllers.exceptions.IllegalOrphanException;
import py.com.tipcsa.eva.controllers.exceptions.NonexistentEntityException;
import py.com.tipcsa.eva.entities.NivelAcceso;

/**
 *
 * @author santiago
 */
public class NivelAccesoJpaController implements Serializable {

    public NivelAccesoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NivelAcceso nivelAcceso) {
        if (nivelAcceso.getUsuarioList() == null) {
            nivelAcceso.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : nivelAcceso.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            nivelAcceso.setUsuarioList(attachedUsuarioList);
            em.persist(nivelAcceso);
            for (Usuario usuarioListUsuario : nivelAcceso.getUsuarioList()) {
                NivelAcceso oldNivelAccesoOfUsuarioListUsuario = usuarioListUsuario.getNivelAcceso();
                usuarioListUsuario.setNivelAcceso(nivelAcceso);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldNivelAccesoOfUsuarioListUsuario != null) {
                    oldNivelAccesoOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldNivelAccesoOfUsuarioListUsuario = em.merge(oldNivelAccesoOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NivelAcceso nivelAcceso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NivelAcceso persistentNivelAcceso = em.find(NivelAcceso.class, nivelAcceso.getNivelAcceso());
            List<Usuario> usuarioListOld = persistentNivelAcceso.getUsuarioList();
            List<Usuario> usuarioListNew = nivelAcceso.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its nivelAcceso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            nivelAcceso.setUsuarioList(usuarioListNew);
            nivelAcceso = em.merge(nivelAcceso);
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    NivelAcceso oldNivelAccesoOfUsuarioListNewUsuario = usuarioListNewUsuario.getNivelAcceso();
                    usuarioListNewUsuario.setNivelAcceso(nivelAcceso);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldNivelAccesoOfUsuarioListNewUsuario != null && !oldNivelAccesoOfUsuarioListNewUsuario.equals(nivelAcceso)) {
                        oldNivelAccesoOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldNivelAccesoOfUsuarioListNewUsuario = em.merge(oldNivelAccesoOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nivelAcceso.getNivelAcceso();
                if (findNivelAcceso(id) == null) {
                    throw new NonexistentEntityException("The nivelAcceso with id " + id + " no longer exists.");
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
            NivelAcceso nivelAcceso;
            try {
                nivelAcceso = em.getReference(NivelAcceso.class, id);
                nivelAcceso.getNivelAcceso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nivelAcceso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuario> usuarioListOrphanCheck = nivelAcceso.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This NivelAcceso (" + nivelAcceso + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable nivelAcceso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(nivelAcceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NivelAcceso> findNivelAccesoEntities() {
        return findNivelAccesoEntities(true, -1, -1);
    }

    public List<NivelAcceso> findNivelAccesoEntities(int maxResults, int firstResult) {
        return findNivelAccesoEntities(false, maxResults, firstResult);
    }

    private List<NivelAcceso> findNivelAccesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NivelAcceso.class));
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

    public NivelAcceso findNivelAcceso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NivelAcceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getNivelAccesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NivelAcceso> rt = cq.from(NivelAcceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
