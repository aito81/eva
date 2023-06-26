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
import py.com.tipcsa.eva.entities.NivelAcceso;
import py.com.tipcsa.eva.entities.Evaluacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.tipcsa.eva.controllers.exceptions.IllegalOrphanException;
import py.com.tipcsa.eva.controllers.exceptions.NonexistentEntityException;
import py.com.tipcsa.eva.entities.EvaluadorEvaluado;
import py.com.tipcsa.eva.entities.PerfilUsuario;
import py.com.tipcsa.eva.entities.EvaluadorGrupo;
import py.com.tipcsa.eva.entities.Usuario;

/**
 *
 * @author santiago
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
        if (usuario.getEvaluacionList() == null) {
            usuario.setEvaluacionList(new ArrayList<Evaluacion>());
        }
        if (usuario.getEvaluacionList1() == null) {
            usuario.setEvaluacionList1(new ArrayList<Evaluacion>());
        }
        if (usuario.getEvaluadorEvaluadoList() == null) {
            usuario.setEvaluadorEvaluadoList(new ArrayList<EvaluadorEvaluado>());
        }
        if (usuario.getEvaluadorEvaluadoList1() == null) {
            usuario.setEvaluadorEvaluadoList1(new ArrayList<EvaluadorEvaluado>());
        }
        if (usuario.getPerfilUsuarioList() == null) {
            usuario.setPerfilUsuarioList(new ArrayList<PerfilUsuario>());
        }
        if (usuario.getEvaluadorGrupoList() == null) {
            usuario.setEvaluadorGrupoList(new ArrayList<EvaluadorGrupo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NivelAcceso nivelAcceso = usuario.getNivelAcceso();
            if (nivelAcceso != null) {
                nivelAcceso = em.getReference(nivelAcceso.getClass(), nivelAcceso.getNivelAcceso());
                usuario.setNivelAcceso(nivelAcceso);
            }
            List<Evaluacion> attachedEvaluacionList = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionListEvaluacionToAttach : usuario.getEvaluacionList()) {
                evaluacionListEvaluacionToAttach = em.getReference(evaluacionListEvaluacionToAttach.getClass(), evaluacionListEvaluacionToAttach.getEvaluacion());
                attachedEvaluacionList.add(evaluacionListEvaluacionToAttach);
            }
            usuario.setEvaluacionList(attachedEvaluacionList);
            List<Evaluacion> attachedEvaluacionList1 = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionList1EvaluacionToAttach : usuario.getEvaluacionList1()) {
                evaluacionList1EvaluacionToAttach = em.getReference(evaluacionList1EvaluacionToAttach.getClass(), evaluacionList1EvaluacionToAttach.getEvaluacion());
                attachedEvaluacionList1.add(evaluacionList1EvaluacionToAttach);
            }
            usuario.setEvaluacionList1(attachedEvaluacionList1);
            List<EvaluadorEvaluado> attachedEvaluadorEvaluadoList = new ArrayList<EvaluadorEvaluado>();
            for (EvaluadorEvaluado evaluadorEvaluadoListEvaluadorEvaluadoToAttach : usuario.getEvaluadorEvaluadoList()) {
                evaluadorEvaluadoListEvaluadorEvaluadoToAttach = em.getReference(evaluadorEvaluadoListEvaluadorEvaluadoToAttach.getClass(), evaluadorEvaluadoListEvaluadorEvaluadoToAttach.getEvaluadorEvaluado());
                attachedEvaluadorEvaluadoList.add(evaluadorEvaluadoListEvaluadorEvaluadoToAttach);
            }
            usuario.setEvaluadorEvaluadoList(attachedEvaluadorEvaluadoList);
            List<EvaluadorEvaluado> attachedEvaluadorEvaluadoList1 = new ArrayList<EvaluadorEvaluado>();
            for (EvaluadorEvaluado evaluadorEvaluadoList1EvaluadorEvaluadoToAttach : usuario.getEvaluadorEvaluadoList1()) {
                evaluadorEvaluadoList1EvaluadorEvaluadoToAttach = em.getReference(evaluadorEvaluadoList1EvaluadorEvaluadoToAttach.getClass(), evaluadorEvaluadoList1EvaluadorEvaluadoToAttach.getEvaluadorEvaluado());
                attachedEvaluadorEvaluadoList1.add(evaluadorEvaluadoList1EvaluadorEvaluadoToAttach);
            }
            usuario.setEvaluadorEvaluadoList1(attachedEvaluadorEvaluadoList1);
            List<PerfilUsuario> attachedPerfilUsuarioList = new ArrayList<PerfilUsuario>();
            for (PerfilUsuario perfilUsuarioListPerfilUsuarioToAttach : usuario.getPerfilUsuarioList()) {
                perfilUsuarioListPerfilUsuarioToAttach = em.getReference(perfilUsuarioListPerfilUsuarioToAttach.getClass(), perfilUsuarioListPerfilUsuarioToAttach.getPerfilUsuario());
                attachedPerfilUsuarioList.add(perfilUsuarioListPerfilUsuarioToAttach);
            }
            usuario.setPerfilUsuarioList(attachedPerfilUsuarioList);
            List<EvaluadorGrupo> attachedEvaluadorGrupoList = new ArrayList<EvaluadorGrupo>();
            for (EvaluadorGrupo evaluadorGrupoListEvaluadorGrupoToAttach : usuario.getEvaluadorGrupoList()) {
                evaluadorGrupoListEvaluadorGrupoToAttach = em.getReference(evaluadorGrupoListEvaluadorGrupoToAttach.getClass(), evaluadorGrupoListEvaluadorGrupoToAttach.getEvaluadorGrupo());
                attachedEvaluadorGrupoList.add(evaluadorGrupoListEvaluadorGrupoToAttach);
            }
            usuario.setEvaluadorGrupoList(attachedEvaluadorGrupoList);
            em.persist(usuario);
            if (nivelAcceso != null) {
                nivelAcceso.getUsuarioList().add(usuario);
                nivelAcceso = em.merge(nivelAcceso);
            }
            for (Evaluacion evaluacionListEvaluacion : usuario.getEvaluacionList()) {
                Usuario oldEvaluadoOfEvaluacionListEvaluacion = evaluacionListEvaluacion.getEvaluado();
                evaluacionListEvaluacion.setEvaluado(usuario);
                evaluacionListEvaluacion = em.merge(evaluacionListEvaluacion);
                if (oldEvaluadoOfEvaluacionListEvaluacion != null) {
                    oldEvaluadoOfEvaluacionListEvaluacion.getEvaluacionList().remove(evaluacionListEvaluacion);
                    oldEvaluadoOfEvaluacionListEvaluacion = em.merge(oldEvaluadoOfEvaluacionListEvaluacion);
                }
            }
            for (Evaluacion evaluacionList1Evaluacion : usuario.getEvaluacionList1()) {
                Usuario oldEvaluadorOfEvaluacionList1Evaluacion = evaluacionList1Evaluacion.getEvaluador();
                evaluacionList1Evaluacion.setEvaluador(usuario);
                evaluacionList1Evaluacion = em.merge(evaluacionList1Evaluacion);
                if (oldEvaluadorOfEvaluacionList1Evaluacion != null) {
                    oldEvaluadorOfEvaluacionList1Evaluacion.getEvaluacionList1().remove(evaluacionList1Evaluacion);
                    oldEvaluadorOfEvaluacionList1Evaluacion = em.merge(oldEvaluadorOfEvaluacionList1Evaluacion);
                }
            }
            for (EvaluadorEvaluado evaluadorEvaluadoListEvaluadorEvaluado : usuario.getEvaluadorEvaluadoList()) {
                Usuario oldEvaluadoOfEvaluadorEvaluadoListEvaluadorEvaluado = evaluadorEvaluadoListEvaluadorEvaluado.getEvaluado();
                evaluadorEvaluadoListEvaluadorEvaluado.setEvaluado(usuario);
                evaluadorEvaluadoListEvaluadorEvaluado = em.merge(evaluadorEvaluadoListEvaluadorEvaluado);
                if (oldEvaluadoOfEvaluadorEvaluadoListEvaluadorEvaluado != null) {
                    oldEvaluadoOfEvaluadorEvaluadoListEvaluadorEvaluado.getEvaluadorEvaluadoList().remove(evaluadorEvaluadoListEvaluadorEvaluado);
                    oldEvaluadoOfEvaluadorEvaluadoListEvaluadorEvaluado = em.merge(oldEvaluadoOfEvaluadorEvaluadoListEvaluadorEvaluado);
                }
            }
            for (EvaluadorEvaluado evaluadorEvaluadoList1EvaluadorEvaluado : usuario.getEvaluadorEvaluadoList1()) {
                Usuario oldEvaluadorOfEvaluadorEvaluadoList1EvaluadorEvaluado = evaluadorEvaluadoList1EvaluadorEvaluado.getEvaluador();
                evaluadorEvaluadoList1EvaluadorEvaluado.setEvaluador(usuario);
                evaluadorEvaluadoList1EvaluadorEvaluado = em.merge(evaluadorEvaluadoList1EvaluadorEvaluado);
                if (oldEvaluadorOfEvaluadorEvaluadoList1EvaluadorEvaluado != null) {
                    oldEvaluadorOfEvaluadorEvaluadoList1EvaluadorEvaluado.getEvaluadorEvaluadoList1().remove(evaluadorEvaluadoList1EvaluadorEvaluado);
                    oldEvaluadorOfEvaluadorEvaluadoList1EvaluadorEvaluado = em.merge(oldEvaluadorOfEvaluadorEvaluadoList1EvaluadorEvaluado);
                }
            }
            for (PerfilUsuario perfilUsuarioListPerfilUsuario : usuario.getPerfilUsuarioList()) {
                Usuario oldUsuarioOfPerfilUsuarioListPerfilUsuario = perfilUsuarioListPerfilUsuario.getUsuario();
                perfilUsuarioListPerfilUsuario.setUsuario(usuario);
                perfilUsuarioListPerfilUsuario = em.merge(perfilUsuarioListPerfilUsuario);
                if (oldUsuarioOfPerfilUsuarioListPerfilUsuario != null) {
                    oldUsuarioOfPerfilUsuarioListPerfilUsuario.getPerfilUsuarioList().remove(perfilUsuarioListPerfilUsuario);
                    oldUsuarioOfPerfilUsuarioListPerfilUsuario = em.merge(oldUsuarioOfPerfilUsuarioListPerfilUsuario);
                }
            }
            for (EvaluadorGrupo evaluadorGrupoListEvaluadorGrupo : usuario.getEvaluadorGrupoList()) {
                Usuario oldEvaluadorOfEvaluadorGrupoListEvaluadorGrupo = evaluadorGrupoListEvaluadorGrupo.getEvaluador();
                evaluadorGrupoListEvaluadorGrupo.setEvaluador(usuario);
                evaluadorGrupoListEvaluadorGrupo = em.merge(evaluadorGrupoListEvaluadorGrupo);
                if (oldEvaluadorOfEvaluadorGrupoListEvaluadorGrupo != null) {
                    oldEvaluadorOfEvaluadorGrupoListEvaluadorGrupo.getEvaluadorGrupoList().remove(evaluadorGrupoListEvaluadorGrupo);
                    oldEvaluadorOfEvaluadorGrupoListEvaluadorGrupo = em.merge(oldEvaluadorOfEvaluadorGrupoListEvaluadorGrupo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuario());
            NivelAcceso nivelAccesoOld = persistentUsuario.getNivelAcceso();
            NivelAcceso nivelAccesoNew = usuario.getNivelAcceso();
            List<Evaluacion> evaluacionListOld = persistentUsuario.getEvaluacionList();
            List<Evaluacion> evaluacionListNew = usuario.getEvaluacionList();
            List<Evaluacion> evaluacionList1Old = persistentUsuario.getEvaluacionList1();
            List<Evaluacion> evaluacionList1New = usuario.getEvaluacionList1();
            List<EvaluadorEvaluado> evaluadorEvaluadoListOld = persistentUsuario.getEvaluadorEvaluadoList();
            List<EvaluadorEvaluado> evaluadorEvaluadoListNew = usuario.getEvaluadorEvaluadoList();
            List<EvaluadorEvaluado> evaluadorEvaluadoList1Old = persistentUsuario.getEvaluadorEvaluadoList1();
            List<EvaluadorEvaluado> evaluadorEvaluadoList1New = usuario.getEvaluadorEvaluadoList1();
            List<PerfilUsuario> perfilUsuarioListOld = persistentUsuario.getPerfilUsuarioList();
            List<PerfilUsuario> perfilUsuarioListNew = usuario.getPerfilUsuarioList();
            List<EvaluadorGrupo> evaluadorGrupoListOld = persistentUsuario.getEvaluadorGrupoList();
            List<EvaluadorGrupo> evaluadorGrupoListNew = usuario.getEvaluadorGrupoList();
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
            for (EvaluadorEvaluado evaluadorEvaluadoList1OldEvaluadorEvaluado : evaluadorEvaluadoList1Old) {
                if (!evaluadorEvaluadoList1New.contains(evaluadorEvaluadoList1OldEvaluadorEvaluado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvaluadorEvaluado " + evaluadorEvaluadoList1OldEvaluadorEvaluado + " since its evaluador field is not nullable.");
                }
            }
            for (PerfilUsuario perfilUsuarioListOldPerfilUsuario : perfilUsuarioListOld) {
                if (!perfilUsuarioListNew.contains(perfilUsuarioListOldPerfilUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PerfilUsuario " + perfilUsuarioListOldPerfilUsuario + " since its usuario field is not nullable.");
                }
            }
            for (EvaluadorGrupo evaluadorGrupoListOldEvaluadorGrupo : evaluadorGrupoListOld) {
                if (!evaluadorGrupoListNew.contains(evaluadorGrupoListOldEvaluadorGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvaluadorGrupo " + evaluadorGrupoListOldEvaluadorGrupo + " since its evaluador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (nivelAccesoNew != null) {
                nivelAccesoNew = em.getReference(nivelAccesoNew.getClass(), nivelAccesoNew.getNivelAcceso());
                usuario.setNivelAcceso(nivelAccesoNew);
            }
            List<Evaluacion> attachedEvaluacionListNew = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionListNewEvaluacionToAttach : evaluacionListNew) {
                evaluacionListNewEvaluacionToAttach = em.getReference(evaluacionListNewEvaluacionToAttach.getClass(), evaluacionListNewEvaluacionToAttach.getEvaluacion());
                attachedEvaluacionListNew.add(evaluacionListNewEvaluacionToAttach);
            }
            evaluacionListNew = attachedEvaluacionListNew;
            usuario.setEvaluacionList(evaluacionListNew);
            List<Evaluacion> attachedEvaluacionList1New = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionList1NewEvaluacionToAttach : evaluacionList1New) {
                evaluacionList1NewEvaluacionToAttach = em.getReference(evaluacionList1NewEvaluacionToAttach.getClass(), evaluacionList1NewEvaluacionToAttach.getEvaluacion());
                attachedEvaluacionList1New.add(evaluacionList1NewEvaluacionToAttach);
            }
            evaluacionList1New = attachedEvaluacionList1New;
            usuario.setEvaluacionList1(evaluacionList1New);
            List<EvaluadorEvaluado> attachedEvaluadorEvaluadoListNew = new ArrayList<EvaluadorEvaluado>();
            for (EvaluadorEvaluado evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach : evaluadorEvaluadoListNew) {
                evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach = em.getReference(evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach.getClass(), evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach.getEvaluadorEvaluado());
                attachedEvaluadorEvaluadoListNew.add(evaluadorEvaluadoListNewEvaluadorEvaluadoToAttach);
            }
            evaluadorEvaluadoListNew = attachedEvaluadorEvaluadoListNew;
            usuario.setEvaluadorEvaluadoList(evaluadorEvaluadoListNew);
            List<EvaluadorEvaluado> attachedEvaluadorEvaluadoList1New = new ArrayList<EvaluadorEvaluado>();
            for (EvaluadorEvaluado evaluadorEvaluadoList1NewEvaluadorEvaluadoToAttach : evaluadorEvaluadoList1New) {
                evaluadorEvaluadoList1NewEvaluadorEvaluadoToAttach = em.getReference(evaluadorEvaluadoList1NewEvaluadorEvaluadoToAttach.getClass(), evaluadorEvaluadoList1NewEvaluadorEvaluadoToAttach.getEvaluadorEvaluado());
                attachedEvaluadorEvaluadoList1New.add(evaluadorEvaluadoList1NewEvaluadorEvaluadoToAttach);
            }
            evaluadorEvaluadoList1New = attachedEvaluadorEvaluadoList1New;
            usuario.setEvaluadorEvaluadoList1(evaluadorEvaluadoList1New);
            List<PerfilUsuario> attachedPerfilUsuarioListNew = new ArrayList<PerfilUsuario>();
            for (PerfilUsuario perfilUsuarioListNewPerfilUsuarioToAttach : perfilUsuarioListNew) {
                perfilUsuarioListNewPerfilUsuarioToAttach = em.getReference(perfilUsuarioListNewPerfilUsuarioToAttach.getClass(), perfilUsuarioListNewPerfilUsuarioToAttach.getPerfilUsuario());
                attachedPerfilUsuarioListNew.add(perfilUsuarioListNewPerfilUsuarioToAttach);
            }
            perfilUsuarioListNew = attachedPerfilUsuarioListNew;
            usuario.setPerfilUsuarioList(perfilUsuarioListNew);
            List<EvaluadorGrupo> attachedEvaluadorGrupoListNew = new ArrayList<EvaluadorGrupo>();
            for (EvaluadorGrupo evaluadorGrupoListNewEvaluadorGrupoToAttach : evaluadorGrupoListNew) {
                evaluadorGrupoListNewEvaluadorGrupoToAttach = em.getReference(evaluadorGrupoListNewEvaluadorGrupoToAttach.getClass(), evaluadorGrupoListNewEvaluadorGrupoToAttach.getEvaluadorGrupo());
                attachedEvaluadorGrupoListNew.add(evaluadorGrupoListNewEvaluadorGrupoToAttach);
            }
            evaluadorGrupoListNew = attachedEvaluadorGrupoListNew;
            usuario.setEvaluadorGrupoList(evaluadorGrupoListNew);
            usuario = em.merge(usuario);
            if (nivelAccesoOld != null && !nivelAccesoOld.equals(nivelAccesoNew)) {
                nivelAccesoOld.getUsuarioList().remove(usuario);
                nivelAccesoOld = em.merge(nivelAccesoOld);
            }
            if (nivelAccesoNew != null && !nivelAccesoNew.equals(nivelAccesoOld)) {
                nivelAccesoNew.getUsuarioList().add(usuario);
                nivelAccesoNew = em.merge(nivelAccesoNew);
            }
            for (Evaluacion evaluacionListNewEvaluacion : evaluacionListNew) {
                if (!evaluacionListOld.contains(evaluacionListNewEvaluacion)) {
                    Usuario oldEvaluadoOfEvaluacionListNewEvaluacion = evaluacionListNewEvaluacion.getEvaluado();
                    evaluacionListNewEvaluacion.setEvaluado(usuario);
                    evaluacionListNewEvaluacion = em.merge(evaluacionListNewEvaluacion);
                    if (oldEvaluadoOfEvaluacionListNewEvaluacion != null && !oldEvaluadoOfEvaluacionListNewEvaluacion.equals(usuario)) {
                        oldEvaluadoOfEvaluacionListNewEvaluacion.getEvaluacionList().remove(evaluacionListNewEvaluacion);
                        oldEvaluadoOfEvaluacionListNewEvaluacion = em.merge(oldEvaluadoOfEvaluacionListNewEvaluacion);
                    }
                }
            }
            for (Evaluacion evaluacionList1NewEvaluacion : evaluacionList1New) {
                if (!evaluacionList1Old.contains(evaluacionList1NewEvaluacion)) {
                    Usuario oldEvaluadorOfEvaluacionList1NewEvaluacion = evaluacionList1NewEvaluacion.getEvaluador();
                    evaluacionList1NewEvaluacion.setEvaluador(usuario);
                    evaluacionList1NewEvaluacion = em.merge(evaluacionList1NewEvaluacion);
                    if (oldEvaluadorOfEvaluacionList1NewEvaluacion != null && !oldEvaluadorOfEvaluacionList1NewEvaluacion.equals(usuario)) {
                        oldEvaluadorOfEvaluacionList1NewEvaluacion.getEvaluacionList1().remove(evaluacionList1NewEvaluacion);
                        oldEvaluadorOfEvaluacionList1NewEvaluacion = em.merge(oldEvaluadorOfEvaluacionList1NewEvaluacion);
                    }
                }
            }
            for (EvaluadorEvaluado evaluadorEvaluadoListNewEvaluadorEvaluado : evaluadorEvaluadoListNew) {
                if (!evaluadorEvaluadoListOld.contains(evaluadorEvaluadoListNewEvaluadorEvaluado)) {
                    Usuario oldEvaluadoOfEvaluadorEvaluadoListNewEvaluadorEvaluado = evaluadorEvaluadoListNewEvaluadorEvaluado.getEvaluado();
                    evaluadorEvaluadoListNewEvaluadorEvaluado.setEvaluado(usuario);
                    evaluadorEvaluadoListNewEvaluadorEvaluado = em.merge(evaluadorEvaluadoListNewEvaluadorEvaluado);
                    if (oldEvaluadoOfEvaluadorEvaluadoListNewEvaluadorEvaluado != null && !oldEvaluadoOfEvaluadorEvaluadoListNewEvaluadorEvaluado.equals(usuario)) {
                        oldEvaluadoOfEvaluadorEvaluadoListNewEvaluadorEvaluado.getEvaluadorEvaluadoList().remove(evaluadorEvaluadoListNewEvaluadorEvaluado);
                        oldEvaluadoOfEvaluadorEvaluadoListNewEvaluadorEvaluado = em.merge(oldEvaluadoOfEvaluadorEvaluadoListNewEvaluadorEvaluado);
                    }
                }
            }
            for (EvaluadorEvaluado evaluadorEvaluadoList1NewEvaluadorEvaluado : evaluadorEvaluadoList1New) {
                if (!evaluadorEvaluadoList1Old.contains(evaluadorEvaluadoList1NewEvaluadorEvaluado)) {
                    Usuario oldEvaluadorOfEvaluadorEvaluadoList1NewEvaluadorEvaluado = evaluadorEvaluadoList1NewEvaluadorEvaluado.getEvaluador();
                    evaluadorEvaluadoList1NewEvaluadorEvaluado.setEvaluador(usuario);
                    evaluadorEvaluadoList1NewEvaluadorEvaluado = em.merge(evaluadorEvaluadoList1NewEvaluadorEvaluado);
                    if (oldEvaluadorOfEvaluadorEvaluadoList1NewEvaluadorEvaluado != null && !oldEvaluadorOfEvaluadorEvaluadoList1NewEvaluadorEvaluado.equals(usuario)) {
                        oldEvaluadorOfEvaluadorEvaluadoList1NewEvaluadorEvaluado.getEvaluadorEvaluadoList1().remove(evaluadorEvaluadoList1NewEvaluadorEvaluado);
                        oldEvaluadorOfEvaluadorEvaluadoList1NewEvaluadorEvaluado = em.merge(oldEvaluadorOfEvaluadorEvaluadoList1NewEvaluadorEvaluado);
                    }
                }
            }
            for (PerfilUsuario perfilUsuarioListNewPerfilUsuario : perfilUsuarioListNew) {
                if (!perfilUsuarioListOld.contains(perfilUsuarioListNewPerfilUsuario)) {
                    Usuario oldUsuarioOfPerfilUsuarioListNewPerfilUsuario = perfilUsuarioListNewPerfilUsuario.getUsuario();
                    perfilUsuarioListNewPerfilUsuario.setUsuario(usuario);
                    perfilUsuarioListNewPerfilUsuario = em.merge(perfilUsuarioListNewPerfilUsuario);
                    if (oldUsuarioOfPerfilUsuarioListNewPerfilUsuario != null && !oldUsuarioOfPerfilUsuarioListNewPerfilUsuario.equals(usuario)) {
                        oldUsuarioOfPerfilUsuarioListNewPerfilUsuario.getPerfilUsuarioList().remove(perfilUsuarioListNewPerfilUsuario);
                        oldUsuarioOfPerfilUsuarioListNewPerfilUsuario = em.merge(oldUsuarioOfPerfilUsuarioListNewPerfilUsuario);
                    }
                }
            }
            for (EvaluadorGrupo evaluadorGrupoListNewEvaluadorGrupo : evaluadorGrupoListNew) {
                if (!evaluadorGrupoListOld.contains(evaluadorGrupoListNewEvaluadorGrupo)) {
                    Usuario oldEvaluadorOfEvaluadorGrupoListNewEvaluadorGrupo = evaluadorGrupoListNewEvaluadorGrupo.getEvaluador();
                    evaluadorGrupoListNewEvaluadorGrupo.setEvaluador(usuario);
                    evaluadorGrupoListNewEvaluadorGrupo = em.merge(evaluadorGrupoListNewEvaluadorGrupo);
                    if (oldEvaluadorOfEvaluadorGrupoListNewEvaluadorGrupo != null && !oldEvaluadorOfEvaluadorGrupoListNewEvaluadorGrupo.equals(usuario)) {
                        oldEvaluadorOfEvaluadorGrupoListNewEvaluadorGrupo.getEvaluadorGrupoList().remove(evaluadorGrupoListNewEvaluadorGrupo);
                        oldEvaluadorOfEvaluadorGrupoListNewEvaluadorGrupo = em.merge(oldEvaluadorOfEvaluadorGrupoListNewEvaluadorGrupo);
                    }
                }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<Evaluacion> evaluacionListOrphanCheck = usuario.getEvaluacionList();
            for (Evaluacion evaluacionListOrphanCheckEvaluacion : evaluacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Evaluacion " + evaluacionListOrphanCheckEvaluacion + " in its evaluacionList field has a non-nullable evaluado field.");
            }
            List<Evaluacion> evaluacionList1OrphanCheck = usuario.getEvaluacionList1();
            for (Evaluacion evaluacionList1OrphanCheckEvaluacion : evaluacionList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Evaluacion " + evaluacionList1OrphanCheckEvaluacion + " in its evaluacionList1 field has a non-nullable evaluador field.");
            }
            List<EvaluadorEvaluado> evaluadorEvaluadoListOrphanCheck = usuario.getEvaluadorEvaluadoList();
            for (EvaluadorEvaluado evaluadorEvaluadoListOrphanCheckEvaluadorEvaluado : evaluadorEvaluadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the EvaluadorEvaluado " + evaluadorEvaluadoListOrphanCheckEvaluadorEvaluado + " in its evaluadorEvaluadoList field has a non-nullable evaluado field.");
            }
            List<EvaluadorEvaluado> evaluadorEvaluadoList1OrphanCheck = usuario.getEvaluadorEvaluadoList1();
            for (EvaluadorEvaluado evaluadorEvaluadoList1OrphanCheckEvaluadorEvaluado : evaluadorEvaluadoList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the EvaluadorEvaluado " + evaluadorEvaluadoList1OrphanCheckEvaluadorEvaluado + " in its evaluadorEvaluadoList1 field has a non-nullable evaluador field.");
            }
            List<PerfilUsuario> perfilUsuarioListOrphanCheck = usuario.getPerfilUsuarioList();
            for (PerfilUsuario perfilUsuarioListOrphanCheckPerfilUsuario : perfilUsuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the PerfilUsuario " + perfilUsuarioListOrphanCheckPerfilUsuario + " in its perfilUsuarioList field has a non-nullable usuario field.");
            }
            List<EvaluadorGrupo> evaluadorGrupoListOrphanCheck = usuario.getEvaluadorGrupoList();
            for (EvaluadorGrupo evaluadorGrupoListOrphanCheckEvaluadorGrupo : evaluadorGrupoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the EvaluadorGrupo " + evaluadorGrupoListOrphanCheckEvaluadorGrupo + " in its evaluadorGrupoList field has a non-nullable evaluador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            NivelAcceso nivelAcceso = usuario.getNivelAcceso();
            if (nivelAcceso != null) {
                nivelAcceso.getUsuarioList().remove(usuario);
                nivelAcceso = em.merge(nivelAcceso);
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
