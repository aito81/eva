package py.com.tipcsa.eva.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.vaadin.ui.Notification;

import py.com.tipcsa.eva.controllers.GrupoJpaController;
import py.com.tipcsa.eva.entities.Cargo;
import py.com.tipcsa.eva.entities.Ciudad;
import py.com.tipcsa.eva.entities.Departamento;
import py.com.tipcsa.eva.entities.Grupo;
import py.com.tipcsa.eva.entities.Pregunta;

public class JpaGrupos extends GrupoJpaController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaGrupos(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	public Boolean findGrupoRepetido(String grupo){
		EntityManager em = getEntityManager();
		grupo = "'" + grupo + "'";
		Boolean repetido = false;
		try {
			String sql = " select * from grupo g where g.descripcion = " + grupo;
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
	
	public List<Grupo> findGruposNotbyCargo(Cargo cargo){
		
		EntityManager em = getEntityManager();
		List<Grupo> listgrupo = null;
		try {
			String sqlQry = " select * \n" + 
					"from grupo g \n" + 
					"where g.grupo not in (\n" + 
					"select eg.grupo from puesto_grupo eg where eg.puesto = ?1)";
			Query q = em.createNativeQuery(sqlQry, Grupo.class);
			q.setParameter(1, cargo.getCargo());
			listgrupo = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al grupos sin el cargo seleccionado", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listgrupo;
	}
	
	public List<Grupo> findGruposDistinctCargoPreguntaByCargo(Cargo cargo){
		
		EntityManager em = getEntityManager();
		List<Grupo> listgrupo = null;
		try {
			String sqlQry = " select distinct(g.*) from cargo_pregunta cp\n" + 
					"inner join grupo_pregunta gp on gp.pregunta = cp.pregunta\n" + 
					"inner join grupo g on g.grupo = gp.grupo\n" + 
					"where cp.cargo = ?1";
			Query q = em.createNativeQuery(sqlQry, Grupo.class);
			q.setParameter(1, cargo.getCargo());
			listgrupo = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al consultar grupos de preguntas por cargo", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listgrupo;
	}
	
	
	
	
	
	public Grupo findGruposPruguntaAndCargo(Cargo cargo, Pregunta pre){
		
		EntityManager em = getEntityManager();
		Grupo listgrupo = null;
		try {
			String sqlQry = " select g.* from cargo_pregunta cp\n" + 
					"inner join grupo_pregunta gp on gp.pregunta = cp.pregunta\n" + 
					"inner join grupo g on g.grupo = gp.grupo\n" + 
					"where cp.cargo = ?1 and cp.pregunta = ?2 ";
			Query q = em.createNativeQuery(sqlQry, Grupo.class);
			q.setParameter(1, cargo.getCargo());
			q.setParameter(2, pre.getPregunta());
			listgrupo = (Grupo) q.getSingleResult();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al consultar grupos por preguntas y cargos", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listgrupo;
	}
	
	
	
	
	
	
	
	
	

}
