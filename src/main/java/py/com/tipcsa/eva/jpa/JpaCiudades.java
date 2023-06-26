package py.com.tipcsa.eva.jpa;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.vaadin.ui.Notification;

import py.com.tipcsa.eva.controllers.CiudadJpaController;
import py.com.tipcsa.eva.entities.Ciudad;
import py.com.tipcsa.eva.entities.Departamento;

public class JpaCiudades extends CiudadJpaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaCiudades(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	public List<Ciudad> findCiudadesbyDepto(Departamento depto){
		
		EntityManager em = getEntityManager();
		List<Ciudad> listCiudades = null;
		try {
			String sqlQry = " select * from ciudad c where c.departamento = ?1";
			Query q = em.createNativeQuery(sqlQry, Ciudad.class);
			q.setParameter(1, depto.getDepartamento());
			listCiudades = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al consultar ciudades ", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listCiudades;
	}
	
	
	
	
	

}
