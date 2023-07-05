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
import py.com.tipcsa.eva.entities.GrupoPregunta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.tipcsa.eva.controllers.exceptions.IllegalOrphanException;
import py.com.tipcsa.eva.controllers.exceptions.NonexistentEntityException;
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.PuestoGrupo;

/**
 *
 * @author santi
 */
public class GrupoJpaController implements Serializable {

    public GrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) {
        if (grupo.getGrupoPreguntaList() == null) {
            grupo.setGrupoPreguntaList(new ArrayList<GrupoPregunta>());
        }
        if (grupo.getPuestoGrupoList() == null) {
            grupo.setPuestoGrupoList(new ArrayList<PuestoGrupo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GrupoPregunta> attachedGrupoPreguntaList = new ArrayList<GrupoPregunta>();
            for (GrupoPregunta grupoPreguntaListGrupoPreguntaToAttach : grupo.getGrupoPreguntaList()) {
                grupoPreguntaListGrupoPreguntaToAttach = em.getReference(grupoPreguntaListGrupoPreguntaToAttach.getClass(), grupoPreguntaListGrupoPreguntaToAttach.getGrupoPregunta());
                attachedGrupoPreguntaList.add(grupoPreguntaListGrupoPreguntaToAttach);
            }
            grupo.setGrupoPreguntaList(attachedGrupoPreguntaList);
            List<PuestoGrupo> attachedPuestoGrupoList = new ArrayList<PuestoGrupo>();
            for (PuestoGrupo puestoGrupoListPuestoGrupoToAttach : grupo.getPuestoGrupoList()) {
                puestoGrupoListPuestoGrupoToAttach = em.getReference(puestoGrupoListPuestoGrupoToAttach.getClass(), puestoGrupoListPuestoGrupoToAttach.getPuestoGrupo());
                attachedPuestoGrupoList.add(puestoGrupoListPuestoGrupoToAttach);
            }
            grupo.setPuestoGrupoList(attachedPuestoGrupoList);
            em.persist(grupo);
            for (GrupoPregunta grupoPreguntaListGrupoPregunta : grupo.getGrupoPreguntaList()) {
                Grupo oldGrupoOfGrupoPreguntaListGrupoPregunta = grupoPreguntaListGrupoPregunta.getGrupo();
                grupoPreguntaListGrupoPregunta.setGrupo(grupo);
                grupoPreguntaListGrupoPregunta = em.merge(grupoPreguntaListGrupoPregunta);
                if (oldGrupoOfGrupoPreguntaListGrupoPregunta != null) {
                    oldGrupoOfGrupoPreguntaListGrupoPregunta.getGrupoPreguntaList().remove(grupoPreguntaListGrupoPregunta);
                    oldGrupoOfGrupoPreguntaListGrupoPregunta = em.merge(oldGrupoOfGrupoPreguntaListGrupoPregunta);
                }
            }
            for (PuestoGrupo puestoGrupoListPuestoGrupo : grupo.getPuestoGrupoList()) {
                Grupo oldGrupoOfPuestoGrupoListPuestoGrupo = puestoGrupoListPuestoGrupo.getGrupo();
                puestoGrupoListPuestoGrupo.setGrupo(grupo);
                puestoGrupoListPuestoGrupo = em.merge(puestoGrupoListPuestoGrupo);
                if (oldGrupoOfPuestoGrupoListPuestoGrupo != null) {
                    oldGrupoOfPuestoGrupoListPuestoGrupo.getPuestoGrupoList().remove(puestoGrupoListPuestoGrupo);
                    oldGrupoOfPuestoGrupoListPuestoGrupo = em.merge(oldGrupoOfPuestoGrupoListPuestoGrupo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getGrupo());
            List<GrupoPregunta> grupoPreguntaListOld = persistentGrupo.getGrupoPreguntaList();
            List<GrupoPregunta> grupoPreguntaListNew = grupo.getGrupoPreguntaList();
            List<PuestoGrupo> puestoGrupoListOld = persistentGrupo.getPuestoGrupoList();
            List<PuestoGrupo> puestoGrupoListNew = grupo.getPuestoGrupoList();
            List<String> illegalOrphanMessages = null;
            for (GrupoPregunta grupoPreguntaListOldGrupoPregunta : grupoPreguntaListOld) {
                if (!grupoPreguntaListNew.contains(grupoPreguntaListOldGrupoPregunta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GrupoPregunta " + grupoPreguntaListOldGrupoPregunta + " since its grupo field is not nullable.");
                }
            }
            for (PuestoGrupo puestoGrupoListOldPuestoGrupo : puestoGrupoListOld) {
                if (!puestoGrupoListNew.contains(puestoGrupoListOldPuestoGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PuestoGrupo " + puestoGrupoListOldPuestoGrupo + " since its grupo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GrupoPregunta> attachedGrupoPreguntaListNew = new ArrayList<GrupoPregunta>();
            for (GrupoPregunta grupoPreguntaListNewGrupoPreguntaToAttach : grupoPreguntaListNew) {
                grupoPreguntaListNewGrupoPreguntaToAttach = em.getReference(grupoPreguntaListNewGrupoPreguntaToAttach.getClass(), grupoPreguntaListNewGrupoPreguntaToAttach.getGrupoPregunta());
                attachedGrupoPreguntaListNew.add(grupoPreguntaListNewGrupoPreguntaToAttach);
            }
            grupoPreguntaListNew = attachedGrupoPreguntaListNew;
            grupo.setGrupoPreguntaList(grupoPreguntaListNew);
            List<PuestoGrupo> attachedPuestoGrupoListNew = new ArrayList<PuestoGrupo>();
            for (PuestoGrupo puestoGrupoListNewPuestoGrupoToAttach : puestoGrupoListNew) {
                puestoGrupoListNewPuestoGrupoToAttach = em.getReference(puestoGrupoListNewPuestoGrupoToAttach.getClass(), puestoGrupoListNewPuestoGrupoToAttach.getPuestoGrupo());
                attachedPuestoGrupoListNew.add(puestoGrupoListNewPuestoGrupoToAttach);
            }
            puestoGrupoListNew = attachedPuestoGrupoListNew;
            grupo.setPuestoGrupoList(puestoGrupoListNew);
            grupo = em.merge(grupo);
            for (GrupoPregunta grupoPreguntaListNewGrupoPregunta : grupoPreguntaListNew) {
                if (!grupoPreguntaListOld.contains(grupoPreguntaListNewGrupoPregunta)) {
                    Grupo oldGrupoOfGrupoPreguntaListNewGrupoPregunta = grupoPreguntaListNewGrupoPregunta.getGrupo();
                    grupoPreguntaListNewGrupoPregunta.setGrupo(grupo);
                    grupoPreguntaListNewGrupoPregunta = em.merge(grupoPreguntaListNewGrupoPregunta);
                    if (oldGrupoOfGrupoPreguntaListNewGrupoPregunta != null && !oldGrupoOfGrupoPreguntaListNewGrupoPregunta.equals(grupo)) {
                        oldGrupoOfGrupoPreguntaListNewGrupoPregunta.getGrupoPreguntaList().remove(grupoPreguntaListNewGrupoPregunta);
                        oldGrupoOfGrupoPreguntaListNewGrupoPregunta = em.merge(oldGrupoOfGrupoPreguntaListNewGrupoPregunta);
                    }
                }
            }
            for (PuestoGrupo puestoGrupoListNewPuestoGrupo : puestoGrupoListNew) {
                if (!puestoGrupoListOld.contains(puestoGrupoListNewPuestoGrupo)) {
                    Grupo oldGrupoOfPuestoGrupoListNewPuestoGrupo = puestoGrupoListNewPuestoGrupo.getGrupo();
                    puestoGrupoListNewPuestoGrupo.setGrupo(grupo);
                    puestoGrupoListNewPuestoGrupo = em.merge(puestoGrupoListNewPuestoGrupo);
                    if (oldGrupoOfPuestoGrupoListNewPuestoGrupo != null && !oldGrupoOfPuestoGrupoListNewPuestoGrupo.equals(grupo)) {
                        oldGrupoOfPuestoGrupoListNewPuestoGrupo.getPuestoGrupoList().remove(puestoGrupoListNewPuestoGrupo);
                        oldGrupoOfPuestoGrupoListNewPuestoGrupo = em.merge(oldGrupoOfPuestoGrupoListNewPuestoGrupo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupo.getGrupo();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
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
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getGrupo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GrupoPregunta> grupoPreguntaListOrphanCheck = grupo.getGrupoPreguntaList();
            for (GrupoPregunta grupoPreguntaListOrphanCheckGrupoPregunta : grupoPreguntaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the GrupoPregunta " + grupoPreguntaListOrphanCheckGrupoPregunta + " in its grupoPreguntaList field has a non-nullable grupo field.");
            }
            List<PuestoGrupo> puestoGrupoListOrphanCheck = grupo.getPuestoGrupoList();
            for (PuestoGrupo puestoGrupoListOrphanCheckPuestoGrupo : puestoGrupoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the PuestoGrupo " + puestoGrupoListOrphanCheckPuestoGrupo + " in its puestoGrupoList field has a non-nullable grupo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(grupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
