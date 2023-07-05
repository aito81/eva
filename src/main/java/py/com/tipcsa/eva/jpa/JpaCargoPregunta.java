package py.com.tipcsa.eva.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.vaadin.ui.Notification;

import py.com.tipcsa.eva.controllers.CargoPreguntaJpaController;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.CargoPregunta;
import py.com.tipcsa.eva.entities.EvaluadorCargo;
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.Pregunta;
import py.com.tipcsa.eva.entities.Usuario;

public class JpaCargoPregunta extends CargoPreguntaJpaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaCargoPregunta(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public List<CargoPregunta> findPreguntaByCargo(Cargo cargo){
		
		EntityManager em = getEntityManager();
		List<CargoPregunta> listCargoPregunta = null;
		try {
			String sqlQry =" select * from cargo_pregunta cp where cp.cargo = ?1 ";
			Query q = em.createNativeQuery(sqlQry, CargoPregunta.class);
			q.setParameter(1, cargo.getCargo());
			listCargoPregunta = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al consultar pregunta por cargo ", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listCargoPregunta;
		
	}
	
	
	public List<Pregunta> findPreguntaByNotCargo(Cargo cargo){
		
		EntityManager em = getEntityManager();
		List<Pregunta> listPregunta = null;
		try {
			String sqlQry = "select p.* from pregunta p where p.pregunta \n" + 
					"	not in (select cp.pregunta from cargo_pregunta cp \n" + 
					"			where cp.cargo = ?1)";
			Query q = em.createNativeQuery(sqlQry, Pregunta.class);
			q.setParameter(1, cargo.getCargo());
			listPregunta = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al buscar preguntas sin cargo seleccionado", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listPregunta;
	}
	
	public List<Pregunta> findPreguntaByNotCargoInGrupo(Cargo cargo){
		
		EntityManager em = getEntityManager();
		List<Pregunta> listPregunta = null;
		try {
			String sqlQry = "select p.* from pregunta p where p.pregunta \n" + 
					"					not in (select cp.pregunta from cargo_pregunta cp \n" + 
					"							where cp.cargo = ?1) \n" + 
					"							and p.pregunta in (select gp.pregunta \n" + 
					"							from grupo_pregunta gp)";
			Query q = em.createNativeQuery(sqlQry, Pregunta.class);
			q.setParameter(1, cargo.getCargo());
			listPregunta = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al buscar preguntas sin cargo seleccionado", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listPregunta;
	}
	
	
	
	
	
	
	public CargoPregunta findCargoPreguntaByCargoPregunta(Cargo cargo, Pregunta pregun){
		
		EntityManager em = getEntityManager();
		CargoPregunta cargoPre = null;
		try {
			String sqlQry = "select * from cargo_pregunta cp where cp.cargo = ?1 and cp.pregunta = ?2 ";
			Query q = em.createNativeQuery(sqlQry, CargoPregunta.class);
			q.setParameter(1, cargo.getCargo());
			q.setParameter(2, pregun.getPregunta());
			
			cargoPre = (CargoPregunta)q.getSingleResult();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al buscar preguntas sin cargo seleccionado", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return cargoPre;
	}

	

}
