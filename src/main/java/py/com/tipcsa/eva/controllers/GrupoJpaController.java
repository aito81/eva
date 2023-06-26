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
import py.com.tipcsa.eva.entities.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.tipcsa.eva.controllers.exceptions.IllegalOrphanException;
import py.com.tipcsa.eva.controllers.exceptions.NonexistentEntityException;
import py.com.tipcsa.eva.entities.GrupoPregunta;
import py.com.tipcsa.eva.entities.EvaluadorGrupo;
import py.com.tipcsa.eva.entities.Grupo;

/**
 *
 * @author santiago
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
        if (grupo.getPersonaList() == null) {
            grupo.setPersonaList(new ArrayList<Persona>());
        }
        if (grupo.getGrupoPreguntaList() == null) {
            grupo.setGrupoPreguntaList(new ArrayList<GrupoPregunta>());
        }
        if (grupo.getEvaluadorGrupoList() == null) {
            grupo.setEvaluadorGrupoList(new ArrayList<EvaluadorGrupo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Persona> attachedPersonaList = new ArrayList<Persona>();
            for (Persona personaListPersonaToAttach : grupo.getPersonaList()) {
                personaListPersonaToAttach = em.getReference(personaListPersonaToAttach.getClass(), personaListPersonaToAttach.getPersona());
                attachedPersonaList.add(personaListPersonaToAttach);
            }
            grupo.setPersonaList(attachedPersonaList);
            List<GrupoPregunta> attachedGrupoPreguntaList = new ArrayList<GrupoPregunta>();
            for (GrupoPregunta grupoPreguntaListGrupoPreguntaToAttach : grupo.getGrupoPreguntaList()) {
                grupoPreguntaListGrupoPreguntaToAttach = em.getReference(grupoPreguntaListGrupoPreguntaToAttach.getClass(), grupoPreguntaListGrupoPreguntaToAttach.getGrupoPregunta());
                attachedGrupoPreguntaList.add(grupoPreguntaListGrupoPreguntaToAttach);
            }
            grupo.setGrupoPreguntaList(attachedGrupoPreguntaList);
            List<EvaluadorGrupo> attachedEvaluadorGrupoList = new ArrayList<EvaluadorGrupo>();
            for (EvaluadorGrupo evaluadorGrupoListEvaluadorGrupoToAttach : grupo.getEvaluadorGrupoList()) {
                evaluadorGrupoListEvaluadorGrupoToAttach = em.getReference(evaluadorGrupoListEvaluadorGrupoToAttach.getClass(), evaluadorGrupoListEvaluadorGrupoToAttach.getEvaluadorGrupo());
                attachedEvaluadorGrupoList.add(evaluadorGrupoListEvaluadorGrupoToAttach);
            }
            grupo.setEvaluadorGrupoList(attachedEvaluadorGrupoList);
            em.persist(grupo);
            for (Persona personaListPersona : grupo.getPersonaList()) {
                Grupo oldGrupoOfPersonaListPersona = personaListPersona.getGrupo();
                personaListPersona.setGrupo(grupo);
                personaListPersona = em.merge(personaListPersona);
                if (oldGrupoOfPersonaListPersona != null) {
                    oldGrupoOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldGrupoOfPersonaListPersona = em.merge(oldGrupoOfPersonaListPersona);
                }
            }
            for (GrupoPregunta grupoPreguntaListGrupoPregunta : grupo.getGrupoPreguntaList()) {
                Grupo oldGrupoOfGrupoPreguntaListGrupoPregunta = grupoPreguntaListGrupoPregunta.getGrupo();
                grupoPreguntaListGrupoPregunta.setGrupo(grupo);
                grupoPreguntaListGrupoPregunta = em.merge(grupoPreguntaListGrupoPregunta);
                if (oldGrupoOfGrupoPreguntaListGrupoPregunta != null) {
                    oldGrupoOfGrupoPreguntaListGrupoPregunta.getGrupoPreguntaList().remove(grupoPreguntaListGrupoPregunta);
                    oldGrupoOfGrupoPreguntaListGrupoPregunta = em.merge(oldGrupoOfGrupoPreguntaListGrupoPregunta);
                }
            }
            for (EvaluadorGrupo evaluadorGrupoListEvaluadorGrupo : grupo.getEvaluadorGrupoList()) {
                Grupo oldGrupoOfEvaluadorGrupoListEvaluadorGrupo = evaluadorGrupoListEvaluadorGrupo.getGrupo();
                evaluadorGrupoListEvaluadorGrupo.setGrupo(grupo);
                evaluadorGrupoListEvaluadorGrupo = em.merge(evaluadorGrupoListEvaluadorGrupo);
                if (oldGrupoOfEvaluadorGrupoListEvaluadorGrupo != null) {
                    oldGrupoOfEvaluadorGrupoListEvaluadorGrupo.getEvaluadorGrupoList().remove(evaluadorGrupoListEvaluadorGrupo);
                    oldGrupoOfEvaluadorGrupoListEvaluadorGrupo = em.merge(oldGrupoOfEvaluadorGrupoListEvaluadorGrupo);
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
            List<Persona> personaListOld = persistentGrupo.getPersonaList();
            List<Persona> personaListNew = grupo.getPersonaList();
            List<GrupoPregunta> grupoPreguntaListOld = persistentGrupo.getGrupoPreguntaList();
            List<GrupoPregunta> grupoPreguntaListNew = grupo.getGrupoPreguntaList();
            List<EvaluadorGrupo> evaluadorGrupoListOld = persistentGrupo.getEvaluadorGrupoList();
            List<EvaluadorGrupo> evaluadorGrupoListNew = grupo.getEvaluadorGrupoList();
            List<String> illegalOrphanMessages = null;
            for (Persona personaListOldPersona : personaListOld) {
                if (!personaListNew.contains(personaListOldPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Persona " + personaListOldPersona + " since its grupo field is not nullable.");
                }
            }
            for (GrupoPregunta grupoPreguntaListOldGrupoPregunta : grupoPreguntaListOld) {
                if (!grupoPreguntaListNew.contains(grupoPreguntaListOldGrupoPregunta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GrupoPregunta " + grupoPreguntaListOldGrupoPregunta + " since its grupo field is not nullable.");
                }
            }
            for (EvaluadorGrupo evaluadorGrupoListOldEvaluadorGrupo : evaluadorGrupoListOld) {
                if (!evaluadorGrupoListNew.contains(evaluadorGrupoListOldEvaluadorGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvaluadorGrupo " + evaluadorGrupoListOldEvaluadorGrupo + " since its grupo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Persona> attachedPersonaListNew = new ArrayList<Persona>();
            for (Persona personaListNewPersonaToAttach : personaListNew) {
                personaListNewPersonaToAttach = em.getReference(personaListNewPersonaToAttach.getClass(), personaListNewPersonaToAttach.getPersona());
                attachedPersonaListNew.add(personaListNewPersonaToAttach);
            }
            personaListNew = attachedPersonaListNew;
            grupo.setPersonaList(personaListNew);
            List<GrupoPregunta> attachedGrupoPreguntaListNew = new ArrayList<GrupoPregunta>();
            for (GrupoPregunta grupoPreguntaListNewGrupoPreguntaToAttach : grupoPreguntaListNew) {
                grupoPreguntaListNewGrupoPreguntaToAttach = em.getReference(grupoPreguntaListNewGrupoPreguntaToAttach.getClass(), grupoPreguntaListNewGrupoPreguntaToAttach.getGrupoPregunta());
                attachedGrupoPreguntaListNew.add(grupoPreguntaListNewGrupoPreguntaToAttach);
            }
            grupoPreguntaListNew = attachedGrupoPreguntaListNew;
            grupo.setGrupoPreguntaList(grupoPreguntaListNew);
            List<EvaluadorGrupo> attachedEvaluadorGrupoListNew = new ArrayList<EvaluadorGrupo>();
            for (EvaluadorGrupo evaluadorGrupoListNewEvaluadorGrupoToAttach : evaluadorGrupoListNew) {
                evaluadorGrupoListNewEvaluadorGrupoToAttach = em.getReference(evaluadorGrupoListNewEvaluadorGrupoToAttach.getClass(), evaluadorGrupoListNewEvaluadorGrupoToAttach.getEvaluadorGrupo());
                attachedEvaluadorGrupoListNew.add(evaluadorGrupoListNewEvaluadorGrupoToAttach);
            }
            evaluadorGrupoListNew = attachedEvaluadorGrupoListNew;
            grupo.setEvaluadorGrupoList(evaluadorGrupoListNew);
            grupo = em.merge(grupo);
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    Grupo oldGrupoOfPersonaListNewPersona = personaListNewPersona.getGrupo();
                    personaListNewPersona.setGrupo(grupo);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldGrupoOfPersonaListNewPersona != null && !oldGrupoOfPersonaListNewPersona.equals(grupo)) {
                        oldGrupoOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldGrupoOfPersonaListNewPersona = em.merge(oldGrupoOfPersonaListNewPersona);
                    }
                }
            }
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
            for (EvaluadorGrupo evaluadorGrupoListNewEvaluadorGrupo : evaluadorGrupoListNew) {
                if (!evaluadorGrupoListOld.contains(evaluadorGrupoListNewEvaluadorGrupo)) {
                    Grupo oldGrupoOfEvaluadorGrupoListNewEvaluadorGrupo = evaluadorGrupoListNewEvaluadorGrupo.getGrupo();
                    evaluadorGrupoListNewEvaluadorGrupo.setGrupo(grupo);
                    evaluadorGrupoListNewEvaluadorGrupo = em.merge(evaluadorGrupoListNewEvaluadorGrupo);
                    if (oldGrupoOfEvaluadorGrupoListNewEvaluadorGrupo != null && !oldGrupoOfEvaluadorGrupoListNewEvaluadorGrupo.equals(grupo)) {
                        oldGrupoOfEvaluadorGrupoListNewEvaluadorGrupo.getEvaluadorGrupoList().remove(evaluadorGrupoListNewEvaluadorGrupo);
                        oldGrupoOfEvaluadorGrupoListNewEvaluadorGrupo = em.merge(oldGrupoOfEvaluadorGrupoListNewEvaluadorGrupo);
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
            List<Persona> personaListOrphanCheck = grupo.getPersonaList();
            for (Persona personaListOrphanCheckPersona : personaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the Persona " + personaListOrphanCheckPersona + " in its personaList field has a non-nullable grupo field.");
            }
            List<GrupoPregunta> grupoPreguntaListOrphanCheck = grupo.getGrupoPreguntaList();
            for (GrupoPregunta grupoPreguntaListOrphanCheckGrupoPregunta : grupoPreguntaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the GrupoPregunta " + grupoPreguntaListOrphanCheckGrupoPregunta + " in its grupoPreguntaList field has a non-nullable grupo field.");
            }
            List<EvaluadorGrupo> evaluadorGrupoListOrphanCheck = grupo.getEvaluadorGrupoList();
            for (EvaluadorGrupo evaluadorGrupoListOrphanCheckEvaluadorGrupo : evaluadorGrupoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the EvaluadorGrupo " + evaluadorGrupoListOrphanCheckEvaluadorGrupo + " in its evaluadorGrupoList field has a non-nullable grupo field.");
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
