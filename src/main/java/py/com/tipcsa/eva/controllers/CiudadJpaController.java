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
import py.com.tipcsa.eva.entities.Departamento;
import py.com.tipcsa.eva.entities.Barrio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.tipcsa.eva.controllers.exceptions.IllegalOrphanException;
import py.com.tipcsa.eva.controllers.exceptions.NonexistentEntityException;
import py.com.tipcsa.eva.entities.Ciudad;

/**
 *
 * @author santi
 */
public class CiudadJpaController implements Serializable {

    public CiudadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudad ciudad) {
        if (ciudad.getBarrioList() == null) {
            ciudad.setBarrioList(new ArrayList<Barrio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento departamento = ciudad.getDepartamento();
            if (departamento != null) {
                departamento = em.getReference(departamento.getClass(), departamento.getDepartamento());
                ciudad.setDepartamento(departamento);
            }
            List<Barrio> attachedBarrioList = new ArrayList<Barrio>();
            for (Barrio barrioListBarrioToAttach : ciudad.getBarrioList()) {
                barrioListBarrioToAttach = em.getReference(barrioListBarrioToAttach.getClass(), barrioListBarrioToAttach.getBarrio());
                attachedBarrioList.add(barrioListBarrioToAttach);
            }
            ciudad.setBarrioList(attachedBarrioList);
            em.persist(ciudad);
            if (departamento != null) {
                departamento.getCiudadList().add(ciudad);
                departamento = em.merge(departamento);
            }
            for (Barrio barrioListBarrio : ciudad.getBarrioList()) {
                Ciudad oldCiudadOfBarrioListBarrio = barrioListBarrio.getCiudad();
                barrioListBarrio.setCiudad(ciudad);
                barrioListBarrio = em.merge(barrioListBarrio);
                if (oldCiudadOfBarrioListBarrio != null) {
                    oldCiudadOfBarrioListBarrio.getBarrioList().remove(barrioListBarrio);
                    oldCiudadOfBarrioListBarrio = em.merge(oldCiudadOfBarrioListBarrio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ciudad ciudad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad persistentCiudad = em.find(Ciudad.class, ciudad.getCiudad());
            Departamento departamentoOld = persistentCiudad.getDepartamento();
            Departamento departamentoNew = ciudad.getDepartamento();
            List<Barrio> barrioListOld = persistentCiudad.getBarrioList();
            List<Barrio> barrioListNew = ciudad.getBarrioList();
            List<String> illegalOrphanMessages = null;
            for (Barrio barrioListOldBarrio : barrioListOld) {
                if (!barrioListNew.contains(barrioListOldBarrio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Barrio " + barrioListOldBarrio + " since its ciudad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (departamentoNew != null) {
                departamentoNew = em.getReference(departamentoNew.getClass(), departamentoNew.getDepartamento());
                ciudad.setDepartamento(departamentoNew);
            }
            List<Barrio> attachedBarrioListNew = new ArrayList<Barrio>();
            for (Barrio barrioListNewBarrioToAttach : barrioListNew) {
                barrioListNewBarrioToAttach = em.getReference(barrioListNewBarrioToAttach.getClass(), barrioListNewBarrioToAttach.getBarrio());
                attachedBarrioListNew.add(barrioListNewBarrioToAttach);
            }
            barrioListNew = attachedBarrioListNew;
            ciudad.setBarrioList(barrioListNew);
            ciudad = em.merge(ciudad);
            if (departamentoOld != null && !departamentoOld.equals(departamentoNew)) {
                departamentoOld.getCiudadList().remove(ciudad);
                departamentoOld = em.merge(departamentoOld);
            }
            if (departamentoNew != null && !departamentoNew.equals(departamentoOld)) {
                departamentoNew.getCiudadList().add(ciudad);
                departamentoNew = em.merge(departamentoNew);
            }
            for (Barrio barrioListNewBarrio : barrioListNew) {
                if (!barrioListOld.contains(barrioListNewBarrio)) {
                    Ciudad oldCiudadOfBarrioListNewBarrio = barrioListNewBarrio.getCiudad();
                    barrioListNewBarrio.setCiudad(ciudad);
                    barrioListNewBarrio = em.merge(barrioListNewBarrio);
                    if (oldCiudadOfBarrioListNewBarrio != null && !oldCiudadOfBarrioListNewBarrio.equals(ciudad)) {
                        oldCiudadOfBarrioListNewBarrio.getBarrioList().remove(barrioListNewBarrio);
                        oldCiudadOfBarrioListNewBarrio = em.merge(oldCiudadOfBarrioListNewBarrio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ciudad.getCiudad();
                if (findCiudad(id) == null) {
                    throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.");
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
            Ciudad ciudad;
            try {
                ciudad = em.getReference(Ciudad.class, id);
                ciudad.getCiudad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Barrio> barrioListOrphanCheck = ciudad.getBarrioList();
            for (Barrio barrioListOrphanCheckBarrio : barrioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Barrio " + barrioListOrphanCheckBarrio + " in its barrioList field has a non-nullable ciudad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento departamento = ciudad.getDepartamento();
            if (departamento != null) {
                departamento.getCiudadList().remove(ciudad);
                departamento = em.merge(departamento);
            }
            em.remove(ciudad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ciudad> findCiudadEntities() {
        return findCiudadEntities(true, -1, -1);
    }

    public List<Ciudad> findCiudadEntities(int maxResults, int firstResult) {
        return findCiudadEntities(false, maxResults, firstResult);
    }

    private List<Ciudad> findCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudad.class));
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

    public Ciudad findCiudad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudad.class, id);
        } finally {
            em.close();
        }
    }

    public int getCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudad> rt = cq.from(Ciudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
