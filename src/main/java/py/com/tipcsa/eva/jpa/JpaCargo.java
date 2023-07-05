package py.com.tipcsa.eva.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.vaadin.ui.Notification;

import py.com.tipcsa.eva.controllers.CargoJpaController;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.Grupo;

public class JpaCargo extends CargoJpaController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaCargo(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public Boolean findCargoRepetido(String cargo){
		EntityManager em = getEntityManager();
		cargo = "'" + cargo + "'";
		Boolean repetido = false;
		try {
			String sql = " select * from cargo c where c.descripcion =  " + cargo;
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
	
	
	
	
	public List<Cargo> findCargoNot(Cargo cargo){
		
		EntityManager em = getEntityManager();
		List<Cargo> listCargo = null;
		try {
			String sqlQry = " select * FROM cargo c where c.cargo != ?1";
			Query q = em.createNativeQuery(sqlQry, Cargo.class);
			q.setParameter(1, cargo.getCargo());
			listCargo = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al buscar cargo", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listCargo;
	}
	
	public List<Cargo> findCargoNotInEvaluador(Cargo evaluador){
		
		EntityManager em = getEntityManager();
		List<Cargo> listCargo = null;
		try {
			String sqlQry = " select * from cargo c where c.cargo not in (\n" + 
					"	select ec.cargo from evaluador_cargo ec where ec.evaluador = ?1) \n" + 
					"	and c.cargo != ?1";
			Query q = em.createNativeQuery(sqlQry, Cargo.class);
			q.setParameter(1, evaluador.getCargo());
			listCargo = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al buscar cargo distinto al evaluador", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listCargo;
	}
	
	
	
	

}
