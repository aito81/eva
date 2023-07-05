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
import py.com.tipcsa.eva.entities.Area;
import py.com.tipcsa.eva.entities.Barrio;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.Sucursal;
import py.com.tipcsa.eva.entities.Evaluacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.tipcsa.eva.controllers.exceptions.IllegalOrphanException;
import py.com.tipcsa.eva.controllers.exceptions.NonexistentEntityException;
import py.com.tipcsa.eva.entities.EvaluadorEvaluado;
import py.com.tipcsa.eva.entities.Persona;
import py.com.tipcsa.eva.entities.Usuario;

/**
 *
 * @author santi
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
        if (persona.getEvaluacionList() == null) {
            persona.setEvaluacionList(new ArrayList<Evaluacion>());
        }
        if (persona.getEvaluacionList1() == null) {
            persona.setEvaluacionList1(new ArrayList<Evaluacion>());
        }
        if (persona.getEvaluadorEvaluadoList() == null) {
            persona.setEvaluadorEvaluadoList(new ArrayList<EvaluadorEvaluado>());
        }
        if (persona.getUsuarioList() == null) {
            persona.setUsuarioList(new ArrayList<Usuario>());
        }
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
            Sucursal sucursal = persona.getSucursal();
            if (sucursal != null) {
                sucursal = em.getReference(sucursal.getClass(), sucursal.getSucursal());
                persona.setSucursal(sucursal);
            }
            List<Evaluacion> attachedEvaluacionList = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionListEvaluacionToAttach : persona.getEvaluacionList()) {
                evaluacionListEvaluacionToAttach = em.getReference(evaluacionListEvaluacionToAttach.getClass(), evaluacionListEvaluacionToAttach.getEvaluacion());
                attachedEvaluacionList.add(evaluacionListEvaluacionToAttach);
            }
            persona.setEvaluacionList(attachedEvaluacionList);
            List<Evaluacion> attachedEvaluacionList1 = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionList1EvaluacionToAttach : persona.getEvaluacionList1()) {
                evaluacionList1EvaluacionToAttach = em.getReference(evaluacionList1EvaluacionToAttach.getClass(), evaluacionList1EvaluacionToAttach.getEvaluacion());
                attachedEvaluacionList1.add(evaluacionList1EvaluacionToAttach);
            }
            persona.setEvaluacionList1(attachedEvaluacionList1);
            List<EvaluadorEvaluado> attachedEvaluadorEvaluadoList = new ArrayList<EvaluadorEvaluado>();
            for (EvaluadorEvaluado evaluadorEvaluadoListEvaluadorEvaluadoToAttach : persona.getEvaluadorEvaluadoList()) {
                evaluadorEvaluadoListEvaluadorEvaluadoToAttach = em.getReference(evaluadorEvaluadoListEvaluadorEvaluadoToAttach.getClass(), evaluadorEvaluadoListEvaluadorEvaluadoToAttach.getEvaluadorEvaluado());
                attachedEvaluadorEvaluadoList.add(evaluadorEvaluadoListEvaluadorEvaluadoToAttach);
            }
            persona.setEvaluadorEvaluadoList(attachedEvaluadorEvaluadoList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : persona.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            persona.setUsuarioList(attachedUsuarioList);
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
            if (sucursal != null) {
                sucursal.getPersonaList().add(persona);
                sucursal = em.merge(sucursal);
            }
            for (Evaluacion evaluacionListEvaluacion : persona.getEvaluacionList()) {
                Persona oldEvaluadoOfEvaluacionListEvaluacion = evaluacionListEvaluacion.getEvaluado();
                evaluacionListEvaluacion.setEvaluado(persona);
                evaluacionListEvaluacion = em.merge(evaluacionListEvaluacion);
                if (oldEvaluadoOfEvaluacionListEvaluacion != null) {
                    oldEvaluadoOfEvaluacionListEvaluacion.getEvaluacionList().remove(evaluacionListEvaluacion);
                    oldEvaluadoOfEvaluacionListEvaluacion = em.merge(oldEvaluadoOfEvaluacionListEvaluacion);
                }
            }
            for (Evaluacion evaluacionList1Evaluacion : persona.getEvaluacionList1()) {
                Persona oldEvaluadorOfEvaluacionList1Evaluacion = evaluacionList1Evaluacion.getEvaluador();
                evaluacionList1Evaluacion.setEvaluador(persona);
                evaluacionList1Evaluacion = em.merge(evaluacionList1Evaluacion);
                if (oldEvaluadorOfEvaluacionList1Evaluacion != null) {
                    oldEvaluadorOfEvaluacionList1Evaluacion.getEvaluacionList1().remove(evaluacionList1Evaluacion);
                    oldEvaluadorOfEvaluacionList1Evaluacion = em.merge(oldEvaluadorOfEvaluacionList1Evaluacion);
                }
            }
            for (EvaluadorEvaluado evaluadorEvaluadoListEvaluadorEvaluado : persona.getEvaluadorEvaluadoList()) {
                Persona oldEvaluadoOfEvaluadorEvaluadoListEvaluadorEvaluado = evaluadorEvaluadoListEvaluadorEvaluado.getEvaluado();
                evaluadorEvaluadoListEvaluadorEvaluado.setEvaluado(persona);
                evaluadorEvaluadoListEvaluadorEvaluado = em.merge(evaluadorEvaluadoListEvaluadorEvaluado);
                if (oldEvaluadoOfEvaluadorEvaluadoListEvaluadorEvaluado != null) {
                    oldEvaluadoOfEvaluadorEvaluadoListEvaluadorEvaluado.getEvaluadorEvaluadoList().remove(evaluadorEvaluadoListEvaluadorEvaluado);
                    oldEvaluadoOfEvaluadorEvaluadoListEvaluadorEvaluado = em.merge(oldEvaluadoOfEvaluadorEvaluadoListEvaluadorEvaluado);
                }
            }
            for (Usuario usuarioListUsuario : persona.getUsuarioList()) {
                Persona oldPersonaOfUsuarioListUsuario = usuarioListUsuario.getPersona();
                usuarioListUsuario.setPersona(persona);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldPersonaOfUsuarioListUsuario != null) {
                    oldPersonaOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldPersonaOfUsuarioListUsuario = em.merge(oldPersonaOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
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
            Sucursal sucursalOld = persistentPersona.getSucursal();
            Sucursal sucursalNew = persona.getSucursal();
            List<Evaluacion> evaluacionListOld = persistentPersona.getEvaluacionList();
            List<Evaluacion> evaluacionListNew = persona.getEvaluacionList();
            List<Evaluacion> evaluacionList1Old = persistentPersona.getEvaluacionList1();
            List<Evaluacion> evaluacionList1New = persona.getEvaluacionList1();
            List<EvaluadorEvaluado> evaluadorEvaluadoListOld = persistentPersona.getEvaluadorEvaluadoList();
            List<EvaluadorEvaluado> evaluadorEvaluadoListNew = persona.getEvaluadorEvaluadoList();
            List<Usuario> usuarioListOld = persistentPersona.getUsuarioList();
            List<Usuario> usuarioListNew = persona.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Evaluacion evaluacionListOldEvaluacion : evaluacionListOld) {
                if (!evaluacionListNew.contains(evaluacionListOldEvaluacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evaluacion " + evaluacionListOldEvaluacion + " since its evaluado field is not nullable.");
                }
            }
            for (Evaluacion evaluacionList1OldEvaluacion : evaluacionList1Old) {
                if (!evaluacionList1New.contains(evaluacionList1OldEvaluacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evaluacion " + evaluacionList1OldEvaluacion + " since its evaluador field is not nullable.");
                }
            }
            for (EvaluadorEvaluado evaluadorEvaluadoListOldEvaluadorEvaluado : evaluadorEvaluadoListOld) {
                if (!evaluadorEvaluadoListNew.contains(evaluadorEvaluadoListOldEvaluadorEvaluado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvaluadorEvaluado " + evaluadorEvaluadoListOldEvaluadorEvaluado + " since its evaluado field is not nullable.");
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its persona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
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
            if (sucursalNew != null) {
                sucursalNew = em.getReference(sucursalNew.getClass(), sucursalNew.getSucursal());
                persona.setSucursal(sucursalNew);
            }
            List<Evaluacion> attachedEvaluacionListNew = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionListNewEvaluacionToAttach : evaluacionListNew) {
                evaluacionListNewEvaluacionToAttach = em.getReference(evaluacionListNewEvaluacionToAttach.getClass(), evaluacionListNewEvaluacionToAttach.getEvaluacion());
                attachedEvaluacionListNew.add(evaluacionListNewEvaluacionToAttach);
            }
            evaluacionListNew = attachedEvaluacionListNew;
            persona.setEvaluacionList(evaluacionListNew);
            List<Evaluacion> attachedEvaluacionList1New = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionList1NewEvaluacionToAttach : evaluacionList1New) {
                evaluacionList1NewEvaluacionToAttach = em.getReference(evaluacionList1NewEvaluacionToAttach.getClass(), evaluacionList1NewEvaluacionToAttach.getEvaluacion());
                attachedEvaluacionList1New.add(evaluacionList1NewEvaluacionToAttach);
            }
            evaluacionList1New = attachedEvaluacionList1New;
            persona.setEvaluacionList1(evaluacionList1New);
            List<EvaluadorEvaluado> attachedEvaluadorEvaluadoListNew = new ArrayList<EvaluadorEvaluado>();
            for (EvaluadorEvaluado evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach : evaluadorEvaluadoListNew) {
                evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach = em.getReference(evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach.getClass(), evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach.getEvaluadorEvaluado());
                attachedEvaluadorEvaluadoListNew.add(evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach);
            }
            evaluadorEvaluadoListNew = attachedEvaluadorEvaluadoListNew;
            persona.setEvaluadorEvaluadoList(evaluadorEvaluadoListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            persona.setUsuarioList(usuarioListNew);
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
            if (sucursalOld != null && !sucursalOld.equals(sucursalNew)) {
                sucursalOld.getPersonaList().remove(persona);
                sucursalOld = em.merge(sucursalOld);
            }
            if (sucursalNew != null && !sucursalNew.equals(sucursalOld)) {
                sucursalNew.getPersonaList().add(persona);
                sucursalNew = em.merge(sucursalNew);
            }
            for (Evaluacion evaluacionListNewEvaluacion : evaluacionListNew) {
                if (!evaluacionListOld.contains(evaluacionListNewEvaluacion)) {
                    Persona oldEvaluadoOfEvaluacionListNewEvaluacion = evaluacionListNewEvaluacion.getEvaluado();
                    evaluacionListNewEvaluacion.setEvaluado(persona);
                    evaluacionListNewEvaluacion = em.merge(evaluacionListNewEvaluacion);
                    if (oldEvaluadoOfEvaluacionListNewEvaluacion != null && !oldEvaluadoOfEvaluacionListNewEvaluacion.equals(persona)) {
                        oldEvaluadoOfEvaluacionListNewEvaluacion.getEvaluacionList().remove(evaluacionListNewEvaluacion);
                        oldEvaluadoOfEvaluacionListNewEvaluacion = em.merge(oldEvaluadoOfEvaluacionListNewEvaluacion);
                    }
                }
            }
            for (Evaluacion evaluacionList1NewEvaluacion : evaluacionList1New) {
                if (!evaluacionList1Old.contains(evaluacionList1NewEvaluacion)) {
                    Persona oldEvaluadorOfEvaluacionList1NewEvaluacion = evaluacionList1NewEvaluacion.getEvaluador();
                    evaluacionList1NewEvaluacion.setEvaluador(persona);
                    evaluacionList1NewEvaluacion = em.merge(evaluacionList1NewEvaluacion);
                    if (oldEvaluadorOfEvaluacionList1NewEvaluacion != null && !oldEvaluadorOfEvaluacionList1NewEvaluacion.equals(persona)) {
                        oldEvaluadorOfEvaluacionList1NewEvaluacion.getEvaluacionList1().remove(evaluacionList1NewEvaluacion);
                        oldEvaluadorOfEvaluacionList1NewEvaluacion = em.merge(oldEvaluadorOfEvaluacionList1NewEvaluacion);
                    }
                }
            }
            for (EvaluadorEvaluado evaluadorEvaluadoListNewEvaluadorEvaluado : evaluadorEvaluadoListNew) {
                if (!evaluadorEvaluadoListOld.contains(evaluadorEvaluadoListNewEvaluadorEvaluado)) {
                    Persona oldEvaluadoOfEvaluadorEvaluadoListNewEvaluadorEvaluado = evaluadorEvaluadoListNewEvaluadorEvaluado.getEvaluado();
                    evaluadorEvaluadoListNewEvaluadorEvaluado.setEvaluado(persona);
                    evaluadorEvaluadoListNewEvaluadorEvaluado = em.merge(evaluadorEvaluadoListNewEvaluadorEvaluado);
                    if (oldEvaluadoOfEvaluadorEvaluadoListNewEvaluadorEvaluado != null && !oldEvaluadoOfEvaluadorEvaluadoListNewEvaluadorEvaluado.equals(persona)) {
                        oldEvaluadoOfEvaluadorEvaluadoListNewEvaluadorEvaluado.getEvaluadorEvaluadoList().remove(evaluadorEvaluadoListNewEvaluadorEvaluado);
                        oldEvaluadoOfEvaluadorEvaluadoListNewEvaluadorEvaluado = em.merge(oldEvaluadoOfEvaluadorEvaluadoListNewEvaluadorEvaluado);
                    }
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Persona oldPersonaOfUsuarioListNewUsuario = usuarioListNewUsuario.getPersona();
                    usuarioListNewUsuario.setPersona(persona);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldPersonaOfUsuarioListNewUsuario != null && !oldPersonaOfUsuarioListNewUsuario.equals(persona)) {
                        oldPersonaOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldPersonaOfUsuarioListNewUsuario = em.merge(oldPersonaOfUsuarioListNewUsuario);
                    }
                }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<Evaluacion> evaluacionListOrphanCheck = persona.getEvaluacionList();
            for (Evaluacion evaluacionListOrphanCheckEvaluacion : evaluacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Evaluacion " + evaluacionListOrphanCheckEvaluacion + " in its evaluacionList field has a non-nullable evaluado field.");
            }
            List<Evaluacion> evaluacionList1OrphanCheck = persona.getEvaluacionList1();
            for (Evaluacion evaluacionList1OrphanCheckEvaluacion : evaluacionList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Evaluacion " + evaluacionList1OrphanCheckEvaluacion + " in its evaluacionList1 field has a non-nullable evaluador field.");
            }
            List<EvaluadorEvaluado> evaluadorEvaluadoListOrphanCheck = persona.getEvaluadorEvaluadoList();
            for (EvaluadorEvaluado evaluadorEvaluadoListOrphanCheckEvaluadorEvaluado : evaluadorEvaluadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the EvaluadorEvaluado " + evaluadorEvaluadoListOrphanCheckEvaluadorEvaluado + " in its evaluadorEvaluadoList field has a non-nullable evaluado field.");
            }
            List<Usuario> usuarioListOrphanCheck = persona.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable persona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
