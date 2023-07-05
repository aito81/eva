package py.com.tipcsa.eva.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.vaadin.ui.Notification;

import py.com.tipcsa.eva.controllers.GrupoPreguntaJpaController;

import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.GrupoPregunta;

public class JpaGrupoPregunta extends GrupoPreguntaJpaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaGrupoPregunta(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	public List<GrupoPregunta> findGrupoPreguntabyGrupo(Grupo grupo){
		
		EntityManager em = getEntityManager();
		List<GrupoPregunta> listGrupoPregunta = null;
		try {
			String sqlQry = " select * from grupo_pregunta gp\n" + 
					"where gp.grupo = ?1";
			Query q = em.createNativeQuery(sqlQry, GrupoPregunta.class);
			q.setParameter(1, grupo.getGrupo());
			listGrupoPregunta = q.getResultList();
		} catch (Exception e) {
			Notification.show("Error al buscar preguntas por grupo >> "+ e.getMessage(), Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listGrupoPregunta;
	}
	
	
}
