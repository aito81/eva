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
import py.com.tipcsa.eva.entities.CargoPregunta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.tipcsa.eva.controllers.exceptions.IllegalOrphanException;
import py.com.tipcsa.eva.controllers.exceptions.NonexistentEntityException;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.EvaluadorCargo;
import py.com.tipcsa.eva.entities.Persona;
import py.com.tipcsa.eva.entities.EvaluadorEvaluado;
import py.com.tipcsa.eva.entities.PuestoGrupo;

/**
 *
 * @author santi
 */
public class CargoJpaController implements Serializable {

    public CargoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cargo cargo) {
        if (cargo.getCargoPreguntaList() == null) {
            cargo.setCargoPreguntaList(new ArrayList<CargoPregunta>());
        }
        if (cargo.getEvaluadorCargoList() == null) {
            cargo.setEvaluadorCargoList(new ArrayList<EvaluadorCargo>());
        }
        if (cargo.getEvaluadorCargoList1() == null) {
            cargo.setEvaluadorCargoList1(new ArrayList<EvaluadorCargo>());
        }
        if (cargo.getPersonaList() == null) {
            cargo.setPersonaList(new ArrayList<Persona>());
        }
        if (cargo.getEvaluadorEvaluadoList() == null) {
            cargo.setEvaluadorEvaluadoList(new ArrayList<EvaluadorEvaluado>());
        }
        if (cargo.getPuestoGrupoList() == null) {
            cargo.setPuestoGrupoList(new ArrayList<PuestoGrupo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CargoPregunta> attachedCargoPreguntaList = new ArrayList<CargoPregunta>();
            for (CargoPregunta cargoPreguntaListCargoPreguntaToAttach : cargo.getCargoPreguntaList()) {
                cargoPreguntaListCargoPreguntaToAttach = em.getReference(cargoPreguntaListCargoPreguntaToAttach.getClass(), cargoPreguntaListCargoPreguntaToAttach.getCargoPregunta());
                attachedCargoPreguntaList.add(cargoPreguntaListCargoPreguntaToAttach);
            }
            cargo.setCargoPreguntaList(attachedCargoPreguntaList);
            List<EvaluadorCargo> attachedEvaluadorCargoList = new ArrayList<EvaluadorCargo>();
            for (EvaluadorCargo evaluadorCargoListEvaluadorCargoToAttach : cargo.getEvaluadorCargoList()) {
                evaluadorCargoListEvaluadorCargoToAttach = em.getReference(evaluadorCargoListEvaluadorCargoToAttach.getClass(), evaluadorCargoListEvaluadorCargoToAttach.getEvaluadorCargo());
                attachedEvaluadorCargoList.add(evaluadorCargoListEvaluadorCargoToAttach);
            }
            cargo.setEvaluadorCargoList(attachedEvaluadorCargoList);
            List<EvaluadorCargo> attachedEvaluadorCargoList1 = new ArrayList<EvaluadorCargo>();
            for (EvaluadorCargo evaluadorCargoList1EvaluadorCargoToAttach : cargo.getEvaluadorCargoList1()) {
                evaluadorCargoList1EvaluadorCargoToAttach = em.getReference(evaluadorCargoList1EvaluadorCargoToAttach.getClass(), evaluadorCargoList1EvaluadorCargoToAttach.getEvaluadorCargo());
                attachedEvaluadorCargoList1.add(evaluadorCargoList1EvaluadorCargoToAttach);
            }
            cargo.setEvaluadorCargoList1(attachedEvaluadorCargoList1);
            List<Persona> attachedPersonaList = new ArrayList<Persona>();
            for (Persona personaListPersonaToAttach : cargo.getPersonaList()) {
                personaListPersonaToAttach = em.getReference(personaListPersonaToAttach.getClass(), personaListPersonaToAttach.getPersona());
                attachedPersonaList.add(personaListPersonaToAttach);
            }
            cargo.setPersonaList(attachedPersonaList);
            List<EvaluadorEvaluado> attachedEvaluadorEvaluadoList = new ArrayList<EvaluadorEvaluado>();
            for (EvaluadorEvaluado evaluadorEvaluadoListEvaluadorEvaluadoToAttach : cargo.getEvaluadorEvaluadoList()) {
                evaluadorEvaluadoListEvaluadorEvaluadoToAttach = em.getReference(evaluadorEvaluadoListEvaluadorEvaluadoToAttach.getClass(), evaluadorEvaluadoListEvaluadorEvaluadoToAttach.getEvaluadorEvaluado());
                attachedEvaluadorEvaluadoList.add(evaluadorEvaluadoListEvaluadorEvaluadoToAttach);
            }
            cargo.setEvaluadorEvaluadoList(attachedEvaluadorEvaluadoList);
            List<PuestoGrupo> attachedPuestoGrupoList = new ArrayList<PuestoGrupo>();
            for (PuestoGrupo puestoGrupoListPuestoGrupoToAttach : cargo.getPuestoGrupoList()) {
                puestoGrupoListPuestoGrupoToAttach = em.getReference(puestoGrupoListPuestoGrupoToAttach.getClass(), puestoGrupoListPuestoGrupoToAttach.getPuestoGrupo());
                attachedPuestoGrupoList.add(puestoGrupoListPuestoGrupoToAttach);
            }
            cargo.setPuestoGrupoList(attachedPuestoGrupoList);
            em.persist(cargo);
            for (CargoPregunta cargoPreguntaListCargoPregunta : cargo.getCargoPreguntaList()) {
                Cargo oldCargoOfCargoPreguntaListCargoPregunta = cargoPreguntaListCargoPregunta.getCargo();
                cargoPreguntaListCargoPregunta.setCargo(cargo);
                cargoPreguntaListCargoPregunta = em.merge(cargoPreguntaListCargoPregunta);
                if (oldCargoOfCargoPreguntaListCargoPregunta != null) {
                    oldCargoOfCargoPreguntaListCargoPregunta.getCargoPreguntaList().remove(cargoPreguntaListCargoPregunta);
                    oldCargoOfCargoPreguntaListCargoPregunta = em.merge(oldCargoOfCargoPreguntaListCargoPregunta);
                }
            }
            for (EvaluadorCargo evaluadorCargoListEvaluadorCargo : cargo.getEvaluadorCargoList()) {
                Cargo oldCargoOfEvaluadorCargoListEvaluadorCargo = evaluadorCargoListEvaluadorCargo.getCargo();
                evaluadorCargoListEvaluadorCargo.setCargo(cargo);
                evaluadorCargoListEvaluadorCargo = em.merge(evaluadorCargoListEvaluadorCargo);
                if (oldCargoOfEvaluadorCargoListEvaluadorCargo != null) {
                    oldCargoOfEvaluadorCargoListEvaluadorCargo.getEvaluadorCargoList().remove(evaluadorCargoListEvaluadorCargo);
                    oldCargoOfEvaluadorCargoListEvaluadorCargo = em.merge(oldCargoOfEvaluadorCargoListEvaluadorCargo);
                }
            }
            for (EvaluadorCargo evaluadorCargoList1EvaluadorCargo : cargo.getEvaluadorCargoList1()) {
                Cargo oldEvaluadorOfEvaluadorCargoList1EvaluadorCargo = evaluadorCargoList1EvaluadorCargo.getEvaluador();
                evaluadorCargoList1EvaluadorCargo.setEvaluador(cargo);
                evaluadorCargoList1EvaluadorCargo = em.merge(evaluadorCargoList1EvaluadorCargo);
                if (oldEvaluadorOfEvaluadorCargoList1EvaluadorCargo != null) {
                    oldEvaluadorOfEvaluadorCargoList1EvaluadorCargo.getEvaluadorCargoList1().remove(evaluadorCargoList1EvaluadorCargo);
                    oldEvaluadorOfEvaluadorCargoList1EvaluadorCargo = em.merge(oldEvaluadorOfEvaluadorCargoList1EvaluadorCargo);
                }
            }
            for (Persona personaListPersona : cargo.getPersonaList()) {
                Cargo oldCargoOfPersonaListPersona = personaListPersona.getCargo();
                personaListPersona.setCargo(cargo);
                personaListPersona = em.merge(personaListPersona);
                if (oldCargoOfPersonaListPersona != null) {
                    oldCargoOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldCargoOfPersonaListPersona = em.merge(oldCargoOfPersonaListPersona);
                }
            }
            for (EvaluadorEvaluado evaluadorEvaluadoListEvaluadorEvaluado : cargo.getEvaluadorEvaluadoList()) {
                Cargo oldEvaluadorOfEvaluadorEvaluadoListEvaluadorEvaluado = evaluadorEvaluadoListEvaluadorEvaluado.getEvaluador();
                evaluadorEvaluadoListEvaluadorEvaluado.setEvaluador(cargo);
                evaluadorEvaluadoListEvaluadorEvaluado = em.merge(evaluadorEvaluadoListEvaluadorEvaluado);
                if (oldEvaluadorOfEvaluadorEvaluadoListEvaluadorEvaluado != null) {
                    oldEvaluadorOfEvaluadorEvaluadoListEvaluadorEvaluado.getEvaluadorEvaluadoList().remove(evaluadorEvaluadoListEvaluadorEvaluado);
                    oldEvaluadorOfEvaluadorEvaluadoListEvaluadorEvaluado = em.merge(oldEvaluadorOfEvaluadorEvaluadoListEvaluadorEvaluado);
                }
            }
            for (PuestoGrupo puestoGrupoListPuestoGrupo : cargo.getPuestoGrupoList()) {
                Cargo oldPuestoOfPuestoGrupoListPuestoGrupo = puestoGrupoListPuestoGrupo.getPuesto();
                puestoGrupoListPuestoGrupo.setPuesto(cargo);
                puestoGrupoListPuestoGrupo = em.merge(puestoGrupoListPuestoGrupo);
                if (oldPuestoOfPuestoGrupoListPuestoGrupo != null) {
                    oldPuestoOfPuestoGrupoListPuestoGrupo.getPuestoGrupoList().remove(puestoGrupoListPuestoGrupo);
                    oldPuestoOfPuestoGrupoListPuestoGrupo = em.merge(oldPuestoOfPuestoGrupoListPuestoGrupo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cargo cargo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo persistentCargo = em.find(Cargo.class, cargo.getCargo());
            List<CargoPregunta> cargoPreguntaListOld = persistentCargo.getCargoPreguntaList();
            List<CargoPregunta> cargoPreguntaListNew = cargo.getCargoPreguntaList();
            List<EvaluadorCargo> evaluadorCargoListOld = persistentCargo.getEvaluadorCargoList();
            List<EvaluadorCargo> evaluadorCargoListNew = cargo.getEvaluadorCargoList();
            List<EvaluadorCargo> evaluadorCargoList1Old = persistentCargo.getEvaluadorCargoList1();
            List<EvaluadorCargo> evaluadorCargoList1New = cargo.getEvaluadorCargoList1();
            List<Persona> personaListOld = persistentCargo.getPersonaList();
            List<Persona> personaListNew = cargo.getPersonaList();
            List<EvaluadorEvaluado> evaluadorEvaluadoListOld = persistentCargo.getEvaluadorEvaluadoList();
            List<EvaluadorEvaluado> evaluadorEvaluadoListNew = cargo.getEvaluadorEvaluadoList();
            List<PuestoGrupo> puestoGrupoListOld = persistentCargo.getPuestoGrupoList();
            List<PuestoGrupo> puestoGrupoListNew = cargo.getPuestoGrupoList();
            List<String> illegalOrphanMessages = null;
            for (CargoPregunta cargoPreguntaListOldCargoPregunta : cargoPreguntaListOld) {
                if (!cargoPreguntaListNew.contains(cargoPreguntaListOldCargoPregunta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CargoPregunta " + cargoPreguntaListOldCargoPregunta + " since its cargo field is not nullable.");
                }
            }
            for (EvaluadorCargo evaluadorCargoListOldEvaluadorCargo : evaluadorCargoListOld) {
                if (!evaluadorCargoListNew.contains(evaluadorCargoListOldEvaluadorCargo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvaluadorCargo " + evaluadorCargoListOldEvaluadorCargo + " since its cargo field is not nullable.");
                }
            }
            for (EvaluadorCargo evaluadorCargoList1OldEvaluadorCargo : evaluadorCargoList1Old) {
                if (!evaluadorCargoList1New.contains(evaluadorCargoList1OldEvaluadorCargo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvaluadorCargo " + evaluadorCargoList1OldEvaluadorCargo + " since its evaluador field is not nullable.");
                }
            }
            for (EvaluadorEvaluado evaluadorEvaluadoListOldEvaluadorEvaluado : evaluadorEvaluadoListOld) {
                if (!evaluadorEvaluadoListNew.contains(evaluadorEvaluadoListOldEvaluadorEvaluado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvaluadorEvaluado " + evaluadorEvaluadoListOldEvaluadorEvaluado + " since its evaluador field is not nullable.");
                }
            }
            for (PuestoGrupo puestoGrupoListOldPuestoGrupo : puestoGrupoListOld) {
                if (!puestoGrupoListNew.contains(puestoGrupoListOldPuestoGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PuestoGrupo " + puestoGrupoListOldPuestoGrupo + " since its puesto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CargoPregunta> attachedCargoPreguntaListNew = new ArrayList<CargoPregunta>();
            for (CargoPregunta cargoPreguntaListNewCargoPreguntaToAttach : cargoPreguntaListNew) {
                cargoPreguntaListNewCargoPreguntaToAttach = em.getReference(cargoPreguntaListNewCargoPreguntaToAttach.getClass(), cargoPreguntaListNewCargoPreguntaToAttach.getCargoPregunta());
                attachedCargoPreguntaListNew.add(cargoPreguntaListNewCargoPreguntaToAttach);
            }
            cargoPreguntaListNew = attachedCargoPreguntaListNew;
            cargo.setCargoPreguntaList(cargoPreguntaListNew);
            List<EvaluadorCargo> attachedEvaluadorCargoListNew = new ArrayList<EvaluadorCargo>();
            for (EvaluadorCargo evaluadorCargoListNewEvaluadorCargoToAttach : evaluadorCargoListNew) {
                evaluadorCargoListNewEvaluadorCargoToAttach = em.getReference(evaluadorCargoListNewEvaluadorCargoToAttach.getClass(), evaluadorCargoListNewEvaluadorCargoToAttach.getEvaluadorCargo());
                attachedEvaluadorCargoListNew.add(evaluadorCargoListNewEvaluadorCargoToAttach);
            }
            evaluadorCargoListNew = attachedEvaluadorCargoListNew;
            cargo.setEvaluadorCargoList(evaluadorCargoListNew);
            List<EvaluadorCargo> attachedEvaluadorCargoList1New = new ArrayList<EvaluadorCargo>();
            for (EvaluadorCargo evaluadorCargoList1NewEvaluadorCargoToAttach : evaluadorCargoList1New) {
                evaluadorCargoList1NewEvaluadorCargoToAttach = em.getReference(evaluadorCargoList1NewEvaluadorCargoToAttach.getClass(), evaluadorCargoList1NewEvaluadorCargoToAttach.getEvaluadorCargo());
                attachedEvaluadorCargoList1New.add(evaluadorCargoList1NewEvaluadorCargoToAttach);
            }
            evaluadorCargoList1New = attachedEvaluadorCargoList1New;
            cargo.setEvaluadorCargoList1(evaluadorCargoList1New);
            List<Persona> attachedPersonaListNew = new ArrayList<Persona>();
            for (Persona personaListNewPersonaToAttach : personaListNew) {
                personaListNewPersonaToAttach = em.getReference(personaListNewPersonaToAttach.getClass(), personaListNewPersonaToAttach.getPersona());
                attachedPersonaListNew.add(personaListNewPersonaToAttach);
            }
            personaListNew = attachedPersonaListNew;
            cargo.setPersonaList(personaListNew);
            List<EvaluadorEvaluado> attachedEvaluadorEvaluadoListNew = new ArrayList<EvaluadorEvaluado>();
            for (EvaluadorEvaluado evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach : evaluadorEvaluadoListNew) {
                evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach = em.getReference(evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach.getClass(), evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach.getEvaluadorEvaluado());
                attachedEvaluadorEvaluadoListNew.add(evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach);
            }
            evaluadorEvaluadoListNew = attachedEvaluadorEvaluadoListNew;
            cargo.setEvaluadorEvaluadoList(evaluadorEvaluadoListNew);
            List<PuestoGrupo> attachedPuestoGrupoListNew = new ArrayList<PuestoGrupo>();
            for (PuestoGrupo puestoGrupoListNewPuestoGrupoToAttach : puestoGrupoListNew) {
                puestoGrupoListNewPuestoGrupoToAttach = em.getReference(puestoGrupoListNewPuestoGrupoToAttach.getClass(), puestoGrupoListNewPuestoGrupoToAttach.getPuestoGrupo());
                attachedPuestoGrupoListNew.add(puestoGrupoListNewPuestoGrupoToAttach);
            }
            puestoGrupoListNew = attachedPuestoGrupoListNew;
            cargo.setPuestoGrupoList(puestoGrupoListNew);
            cargo = em.merge(cargo);
            for (CargoPregunta cargoPreguntaListNewCargoPregunta : cargoPreguntaListNew) {
                if (!cargoPreguntaListOld.contains(cargoPreguntaListNewCargoPregunta)) {
                    Cargo oldCargoOfCargoPreguntaListNewCargoPregunta = cargoPreguntaListNewCargoPregunta.getCargo();
                    cargoPreguntaListNewCargoPregunta.setCargo(cargo);
                    cargoPreguntaListNewCargoPregunta = em.merge(cargoPreguntaListNewCargoPregunta);
                    if (oldCargoOfCargoPreguntaListNewCargoPregunta != null && !oldCargoOfCargoPreguntaListNewCargoPregunta.equals(cargo)) {
                        oldCargoOfCargoPreguntaListNewCargoPregunta.getCargoPreguntaList().remove(cargoPreguntaListNewCargoPregunta);
                        oldCargoOfCargoPreguntaListNewCargoPregunta = em.merge(oldCargoOfCargoPreguntaListNewCargoPregunta);
                    }
                }
            }
            for (EvaluadorCargo evaluadorCargoListNewEvaluadorCargo : evaluadorCargoListNew) {
                if (!evaluadorCargoListOld.contains(evaluadorCargoListNewEvaluadorCargo)) {
                    Cargo oldCargoOfEvaluadorCargoListNewEvaluadorCargo = evaluadorCargoListNewEvaluadorCargo.getCargo();
                    evaluadorCargoListNewEvaluadorCargo.setCargo(cargo);
                    evaluadorCargoListNewEvaluadorCargo = em.merge(evaluadorCargoListNewEvaluadorCargo);
                    if (oldCargoOfEvaluadorCargoListNewEvaluadorCargo != null && !oldCargoOfEvaluadorCargoListNewEvaluadorCargo.equals(cargo)) {
                        oldCargoOfEvaluadorCargoListNewEvaluadorCargo.getEvaluadorCargoList().remove(evaluadorCargoListNewEvaluadorCargo);
                        oldCargoOfEvaluadorCargoListNewEvaluadorCargo = em.merge(oldCargoOfEvaluadorCargoListNewEvaluadorCargo);
                    }
                }
            }
            for (EvaluadorCargo evaluadorCargoList1NewEvaluadorCargo : evaluadorCargoList1New) {
                if (!evaluadorCargoList1Old.contains(evaluadorCargoList1NewEvaluadorCargo)) {
                    Cargo oldEvaluadorOfEvaluadorCargoList1NewEvaluadorCargo = evaluadorCargoList1NewEvaluadorCargo.getEvaluador();
                    evaluadorCargoList1NewEvaluadorCargo.setEvaluador(cargo);
                    evaluadorCargoList1NewEvaluadorCargo = em.merge(evaluadorCargoList1NewEvaluadorCargo);
                    if (oldEvaluadorOfEvaluadorCargoList1NewEvaluadorCargo != null && !oldEvaluadorOfEvaluadorCargoList1NewEvaluadorCargo.equals(cargo)) {
                        oldEvaluadorOfEvaluadorCargoList1NewEvaluadorCargo.getEvaluadorCargoList1().remove(evaluadorCargoList1NewEvaluadorCargo);
                        oldEvaluadorOfEvaluadorCargoList1NewEvaluadorCargo = em.merge(oldEvaluadorOfEvaluadorCargoList1NewEvaluadorCargo);
                    }
                }
            }
            for (Persona personaListOldPersona : personaListOld) {
                if (!personaListNew.contains(personaListOldPersona)) {
                    personaListOldPersona.setCargo(null);
                    personaListOldPersona = em.merge(personaListOldPersona);
                }
            }
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    Cargo oldCargoOfPersonaListNewPersona = personaListNewPersona.getCargo();
                    personaListNewPersona.setCargo(cargo);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldCargoOfPersonaListNewPersona != null && !oldCargoOfPersonaListNewPersona.equals(cargo)) {
                        oldCargoOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldCargoOfPersonaListNewPersona = em.merge(oldCargoOfPersonaListNewPersona);
                    }
                }
            }
            for (EvaluadorEvaluado evaluadorEvaluadoListNewEvaluadorEvaluado : evaluadorEvaluadoListNew) {
                if (!evaluadorEvaluadoListOld.contains(evaluadorEvaluadoListNewEvaluadorEvaluado)) {
                    Cargo oldEvaluadorOfEvaluadorEvaluadoListNewEvaluadorEvaluado = evaluadorEvaluadoListNewEvaluadorEvaluado.getEvaluador();
                    evaluadorEvaluadoListNewEvaluadorEvaluado.setEvaluador(cargo);
                    evaluadorEvaluadoListNewEvaluadorEvaluado = em.merge(evaluadorEvaluadoListNewEvaluadorEvaluado);
                    if (oldEvaluadorOfEvaluadorEvaluadoListNewEvaluadorEvaluado != null && !oldEvaluadorOfEvaluadorEvaluadoListNewEvaluadorEvaluado.equals(cargo)) {
                        oldEvaluadorOfEvaluadorEvaluadoListNewEvaluadorEvaluado.getEvaluadorEvaluadoList().remove(evaluadorEvaluadoListNewEvaluadorEvaluado);
                        oldEvaluadorOfEvaluadorEvaluadoListNewEvaluadorEvaluado = em.merge(oldEvaluadorOfEvaluadorEvaluadoListNewEvaluadorEvaluado);
                    }
                }
            }
            for (PuestoGrupo puestoGrupoListNewPuestoGrupo : puestoGrupoListNew) {
                if (!puestoGrupoListOld.contains(puestoGrupoListNewPuestoGrupo)) {
                    Cargo oldPuestoOfPuestoGrupoListNewPuestoGrupo = puestoGrupoListNewPuestoGrupo.getPuesto();
                    puestoGrupoListNewPuestoGrupo.setPuesto(cargo);
                    puestoGrupoListNewPuestoGrupo = em.merge(puestoGrupoListNewPuestoGrupo);
                    if (oldPuestoOfPuestoGrupoListNewPuestoGrupo != null && !oldPuestoOfPuestoGrupoListNewPuestoGrupo.equals(cargo)) {
                        oldPuestoOfPuestoGrupoListNewPuestoGrupo.getPuestoGrupoList().remove(puestoGrupoListNewPuestoGrupo);
                        oldPuestoOfPuestoGrupoListNewPuestoGrupo = em.merge(oldPuestoOfPuestoGrupoListNewPuestoGrupo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cargo.getCargo();
                if (findCargo(id) == null) {
                    throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.");
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
            Cargo cargo;
            try {
                cargo = em.getReference(Cargo.class, id);
                cargo.getCargo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CargoPregunta> cargoPreguntaListOrphanCheck = cargo.getCargoPreguntaList();
            for (CargoPregunta cargoPreguntaListOrphanCheckCargoPregunta : cargoPreguntaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cargo (" + cargo + ") cannot be destroyed since the CargoPregunta " + cargoPreguntaListOrphanCheckCargoPregunta + " in its cargoPreguntaList field has a non-nullable cargo field.");
            }
            List<EvaluadorCargo> evaluadorCargoListOrphanCheck = cargo.getEvaluadorCargoList();
            for (EvaluadorCargo evaluadorCargoListOrphanCheckEvaluadorCargo : evaluadorCargoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cargo (" + cargo + ") cannot be destroyed since the EvaluadorCargo " + evaluadorCargoListOrphanCheckEvaluadorCargo + " in its evaluadorCargoList field has a non-nullable cargo field.");
            }
            List<EvaluadorCargo> evaluadorCargoList1OrphanCheck = cargo.getEvaluadorCargoList1();
            for (EvaluadorCargo evaluadorCargoList1OrphanCheckEvaluadorCargo : evaluadorCargoList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cargo (" + cargo + ") cannot be destroyed since the EvaluadorCargo " + evaluadorCargoList1OrphanCheckEvaluadorCargo + " in its evaluadorCargoList1 field has a non-nullable evaluador field.");
            }
            List<EvaluadorEvaluado> evaluadorEvaluadoListOrphanCheck = cargo.getEvaluadorEvaluadoList();
            for (EvaluadorEvaluado evaluadorEvaluadoListOrphanCheckEvaluadorEvaluado : evaluadorEvaluadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cargo (" + cargo + ") cannot be destroyed since the EvaluadorEvaluado " + evaluadorEvaluadoListOrphanCheckEvaluadorEvaluado + " in its evaluadorEvaluadoList field has a non-nullable evaluador field.");
            }
            List<PuestoGrupo> puestoGrupoListOrphanCheck = cargo.getPuestoGrupoList();
            for (PuestoGrupo puestoGrupoListOrphanCheckPuestoGrupo : puestoGrupoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cargo (" + cargo + ") cannot be destroyed since the PuestoGrupo " + puestoGrupoListOrphanCheckPuestoGrupo + " in its puestoGrupoList field has a non-nullable puesto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Persona> personaList = cargo.getPersonaList();
            for (Persona personaListPersona : personaList) {
                personaListPersona.setCargo(null);
                personaListPersona = em.merge(personaListPersona);
            }
            em.remove(cargo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cargo> findCargoEntities() {
        return findCargoEntities(true, -1, -1);
    }

    public List<Cargo> findCargoEntities(int maxResults, int firstResult) {
        return findCargoEntities(false, maxResults, firstResult);
    }

    private List<Cargo> findCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargo.class));
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

    public Cargo findCargo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cargo> rt = cq.from(Cargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
