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
import py.com.tipcsa.eva.entities.Area;
import py.com.tipcsa.eva.entities.Barrio;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.Persona;
import py.com.tipcsa.eva.entities.Sucursal;

/**
 *
 * @author santiago
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area area = persona.getArea();
            if (area != null) {
                area = em.getReference(area.getClass(), area.getArea());
                persona.setArea(area);
            }
            Barrio barrio = persona.getBarrio();
            if (barrio != null) {
                barrio = em.getReference(barrio.getClass(), barrio.getBarrio());
                persona.setBarrio(barrio);
            }
            Cargo cargo = persona.getCargo();
            if (cargo != null) {
                cargo = em.getReference(cargo.getClass(), cargo.getCargo());
                persona.setCargo(cargo);
            }
            Grupo grupo = persona.getGrupo();
            if (grupo != null) {
                grupo = em.getReference(grupo.getClass(), grupo.getGrupo());
                persona.setGrupo(grupo);
            }
            Sucursal sucursal = persona.getSucursal();
            if (sucursal != null) {
                sucursal = em.getReference(sucursal.getClass(), sucursal.getSucursal());
                persona.setSucursal(sucursal);
            }
            em.persist(persona);
            if (area != null) {
                area.getPersonaList().add(persona);
                area = em.merge(area);
            }
            if (barrio != null) {
                barrio.getPersonaList().add(persona);
                barrio = em.merge(barrio);
            }
            if (cargo != null) {
                cargo.getPersonaList().add(persona);
                cargo = em.merge(cargo);
            }
            if (grupo != null) {
                grupo.getPersonaList().add(persona);
                grupo = em.merge(grupo);
            }
            if (sucursal != null) {
                sucursal.getPersonaList().add(persona);
                sucursal = em.merge(sucursal);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getPersona());
            Area areaOld = persistentPersona.getArea();
            Area areaNew = persona.getArea();
            Barrio barrioOld = persistentPersona.getBarrio();
            Barrio barrioNew = persona.getBarrio();
            Cargo cargoOld = persistentPersona.getCargo();
            Cargo cargoNew = persona.getCargo();
            Grupo grupoOld = persistentPersona.getGrupo();
            Grupo grupoNew = persona.getGrupo();
            Sucursal sucursalOld = persistentPersona.getSucursal();
            Sucursal sucursalNew = persona.getSucursal();
            if (areaNew != null) {
                areaNew = em.getReference(areaNew.getClass(), areaNew.getArea());
                persona.setArea(areaNew);
            }
            if (barrioNew != null) {
                barrioNew = em.getReference(barrioNew.getClass(), barrioNew.getBarrio());
                persona.setBarrio(barrioNew);
            }
            if (cargoNew != null) {
                cargoNew = em.getReference(cargoNew.getClass(), cargoNew.getCargo());
                persona.setCargo(cargoNew);
            }
            if (grupoNew != null) {
                grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getGrupo());
                persona.setGrupo(grupoNew);
            }
            if (sucursalNew != null) {
                sucursalNew = em.getReference(sucursalNew.getClass(), sucursalNew.getSucursal());
                persona.setSucursal(sucursalNew);
            }
            persona = em.merge(persona);
            if (areaOld != null && !areaOld.equals(areaNew)) {
                areaOld.getPersonaList().remove(persona);
                areaOld = em.merge(areaOld);
            }
            if (areaNew != null && !areaNew.equals(areaOld)) {
                areaNew.getPersonaList().add(persona);
                areaNew = em.merge(areaNew);
            }
            if (barrioOld != null && !barrioOld.equals(barrioNew)) {
                barrioOld.getPersonaList().remove(persona);
                barrioOld = em.merge(barrioOld);
            }
            if (barrioNew != null && !barrioNew.equals(barrioOld)) {
                barrioNew.getPersonaList().add(persona);
                barrioNew = em.merge(barrioNew);
            }
            if (cargoOld != null && !cargoOld.equals(cargoNew)) {
                cargoOld.getPersonaList().remove(persona);
                cargoOld = em.merge(cargoOld);
            }
            if (cargoNew != null && !cargoNew.equals(cargoOld)) {
                cargoNew.getPersonaList().add(persona);
                cargoNew = em.merge(cargoNew);
            }
            if (grupoOld != null && !grupoOld.equals(grupoNew)) {
                grupoOld.getPersonaList().remove(persona);
                grupoOld = em.merge(grupoOld);
            }
            if (grupoNew != null && !grupoNew.equals(grupoOld)) {
                grupoNew.getPersonaList().add(persona);
                grupoNew = em.merge(grupoNew);
            }
            if (sucursalOld != null && !sucursalOld.equals(sucursalNew)) {
                sucursalOld.getPersonaList().remove(persona);
                sucursalOld = em.merge(sucursalOld);
            }
            if (sucursalNew != null && !sucursalNew.equals(sucursalOld)) {
                sucursalNew.getPersonaList().add(persona);
                sucursalNew = em.merge(sucursalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getPersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            Area area = persona.getArea();
            if (area != null) {
                area.getPersonaList().remove(persona);
                area = em.merge(area);
            }
            Barrio barrio = persona.getBarrio();
            if (barrio != null) {
                barrio.getPersonaList().remove(persona);
                barrio = em.merge(barrio);
            }
            Cargo cargo = persona.getCargo();
            if (cargo != null) {
                cargo.getPersonaList().remove(persona);
                cargo = em.merge(cargo);
            }
            Grupo grupo = persona.getGrupo();
            if (grupo != null) {
                grupo.getPersonaList().remove(persona);
                grupo = em.merge(grupo);
            }
            Sucursal sucursal = persona.getSucursal();
            if (sucursal != null) {
                sucursal.getPersonaList().remove(persona);
                sucursal = em.merge(sucursal);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
