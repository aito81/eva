package py.com.tipcsa.eva.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.vaadin.ui.Notification;

import py.com.tipcsa.eva.controllers.EvaluadorCargoJpaController;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.EvaluadorCargo;


public class JpaEvaluadorCargo extends EvaluadorCargoJpaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaEvaluadorCargo(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public List<EvaluadorCargo> findEvaluadorCargobyCargo(Cargo cargo){
		
		EntityManager em = getEntityManager();
		List<EvaluadorCargo> listEvaluadorCargo = null;
		try {
			String sqlQry ="select * from evaluador_cargo ec where ec.evaluador = ?1 ";
			Query q = em.createNativeQuery(sqlQry, EvaluadorCargo.class);
			q.setParameter(1, cargo.getCargo());
			listEvaluadorCargo = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al consultar cargo por evaluador ", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listEvaluadorCargo;
	}

}
