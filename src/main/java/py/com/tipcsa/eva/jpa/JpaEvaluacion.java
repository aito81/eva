package py.com.tipcsa.eva.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.vaadin.ui.Notification;

import py.com.tipcsa.eva.controllers.EvaluacionJpaController;
import py.com.tipcsa.eva.entities.Evaluacion;
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.GrupoPregunta;
import py.com.tipcsa.eva.entities.Persona;

public class JpaEvaluacion extends EvaluacionJpaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaEvaluacion(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	public List<Evaluacion> findEvaluacionByEvaluado(Persona evaluado){
		
		EntityManager em = getEntityManager();
		List<Evaluacion> listEvaluacion = null;
		try {
			String sqlQry = "select * from evaluacion e where e.evaluado = ?1";
			Query q = em.createNativeQuery(sqlQry, Evaluacion.class);
			q.setParameter(1, evaluado.getPersona());
			listEvaluacion = q.getResultList();
		} catch (Exception e) {
			Notification.show("Error al evaluaciones por evaluado >> "+ e.getMessage(), Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listEvaluacion;
	}

}
