package py.com.tipcsa.eva.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.vaadin.ui.Notification;

import py.com.tipcsa.eva.controllers.PreguntaJpaController;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.Pregunta;

public class JpaPreguntas extends PreguntaJpaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaPreguntas(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	
	public List<Pregunta> findPreguntasNotbyGrupo(Grupo grupo){
		
		EntityManager em = getEntityManager();
		List<Pregunta> listPreguntas = null;
		try {
			String sqlQry = "select p.* from pregunta p \n" + 
					"where p.pregunta not in (select gp.pregunta from \n" + 
					"						 grupo_pregunta gp where gp.grupo = ?1)";
			Query q = em.createNativeQuery(sqlQry, Pregunta.class);
			q.setParameter(1, grupo.getGrupo());
			listPreguntas = q.getResultList();
		} catch (Exception e) {
			Notification.show("Error al buscar preguntas desvinculadas de un grupo >> " + e.getMessage(), Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listPreguntas;
	}
	
	public List<Pregunta> findPreguntasNotInGrupo(){
		
		EntityManager em = getEntityManager();
		List<Pregunta> listPreguntas = null;
		try {
			String sqlQry = "select p.* from pregunta p \n" + 
					"where p.pregunta not in (select gp.pregunta from \n" + 
					"						 grupo_pregunta gp )";
			Query q = em.createNativeQuery(sqlQry, Pregunta.class);
//			q.setParameter(1, grupo.getGrupo());
			listPreguntas = q.getResultList();
		} catch (Exception e) {
			Notification.show("Error al buscar preguntas desvinculadas de un grupo >> " + e.getMessage(), Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listPreguntas;
	}
	
	
	

}
