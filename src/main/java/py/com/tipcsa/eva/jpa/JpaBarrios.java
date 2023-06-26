package py.com.tipcsa.eva.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.vaadin.ui.Notification;

import py.com.tipcsa.eva.controllers.BarrioJpaController;
import py.com.tipcsa.eva.entities.Barrio;
import py.com.tipcsa.eva.entities.Ciudad;
import py.com.tipcsa.eva.entities.Departamento;

public class JpaBarrios extends BarrioJpaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaBarrios(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public List<Barrio> findBarriosbyCiudad(Ciudad ciudad){
		
		EntityManager em = getEntityManager();
		List<Barrio> listBarrios = null;
		try {
			String sqlQry = " select * from barrio b where b.ciudad = ?1";
			Query q = em.createNativeQuery(sqlQry, Barrio.class);
			q.setParameter(1, ciudad.getCiudad());
			listBarrios = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al consultar barrios ", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listBarrios;
	}

}
