package py.com.tipcsa.eva.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;



import com.vaadin.ui.Notification;

import py.com.tipcsa.eva.controllers.PersonaJpaController;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.Persona;


public class JpaPersonas extends PersonaJpaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public JpaPersonas(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	public List<Persona> findPersonasSinUser(){
		EntityManager em = getEntityManager();
		List<Persona> listPer = null;
		try {
			String sqlQry= " select * from persona p where p.persona not in (select u.persona from usuario u) "
					+ " and p.vigente = true ";
			Query q = em.createNativeQuery(sqlQry, Persona.class);
			listPer = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}finally {
			em.close();
		}
		return listPer;
	}
	
	
	public List<Persona> findPersonasActiva(){
		EntityManager em = getEntityManager();
		List<Persona> listPer = null;
		try {
			String sqlQry= " select * from persona p where p.vigente = true";
			Query q = em.createNativeQuery(sqlQry, Persona.class);
			listPer = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}finally {
			em.close();
		}
		return listPer;
	}
	
	
	
	public Boolean findCodigoHumanoRepetido(int nro){
		EntityManager em = getEntityManager();
		//List<MarcasVehiculos> listMarcas = null;
		Boolean repetido = false;
		try {
			String sql = " select * from persona p where p.codigo_humano = ?1  ";
			Query q = em.createNativeQuery(sql);
			q.setParameter(1, nro);
			//listMarcas = q.getResultList();
			/*if (!listMarcas.isEmpty()) {
				repetido = true;
			}*/
			
			try {
				if( !q.getSingleResult().toString().equals("0")){
					repetido = true;
				}
			} catch (NoResultException e) {
				repetido = false;
			}
			
	
			
		} catch (Exception e) {
			
			Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}
		return repetido;
	}
	
	
	public Boolean findCedulaRepetido(String nro){
		EntityManager em = getEntityManager();
		//List<MarcasVehiculos> listMarcas = null;
		Boolean repetido = false;
		try {
			String sql = " select * from persona p where p.nro_documento = " + nro;
			Query q = em.createNativeQuery(sql);
			//q.setParameter(1, nro);
			//listMarcas = q.getResultList();
			/*if (!listMarcas.isEmpty()) {
				repetido = true;
			}*/
			
			try {
				if( !q.getSingleResult().toString().equals("0")){
					repetido = true;
				}
			} catch (NoResultException e) {
				repetido = false;
			}
			
	
			
		} catch (Exception e) {
			
			Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}
		return repetido;
	}
	
	
	
	public List<Persona> findPersonaByCargo(Cargo cargo){
		
		EntityManager em = getEntityManager();
		List<Persona> listPersona = null;
		try {
			String sqlQry = " select * from persona p where p.cargo = ?1 ";
			Query q = em.createNativeQuery(sqlQry, Persona.class);
			q.setParameter(1, cargo.getCargo());
			listPersona = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error personas por cargo", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listPersona;
	}
	
	
	public List<Persona> findPersonaWithCargoByEvaluador(Cargo evaluador){
		
		EntityManager em = getEntityManager();
		List<Persona> listPersona = null;
		try {
			String sqlQry = " select * from persona p where p.cargo in (select ec.cargo from \n" + 
					"										  evaluador_cargo ec where ec.evaluador = ?1) ";
			Query q = em.createNativeQuery(sqlQry, Persona.class);
			q.setParameter(1, evaluador.getCargo());
			listPersona = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error con cargo por evaluador ", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listPersona;
	}
	
	
	
	public List<Persona> findEvaluadorByCargo(Cargo cargo){
		
		EntityManager em = getEntityManager();
		List<Persona> listPersona = null;
		try {
			String sqlQry = " select * from persona p where p.cargo = (select distinct ec.evaluador from persona p \n" + 
					"inner join evaluador_cargo ec on ec.cargo = p.cargo\n" + 
					"where p.cargo = ?1)";
			Query q = em.createNativeQuery(sqlQry, Persona.class);
			q.setParameter(1, cargo.getCargo());
			listPersona = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error evaluador por cargo", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
	
		return listPersona;
	
	}

}
