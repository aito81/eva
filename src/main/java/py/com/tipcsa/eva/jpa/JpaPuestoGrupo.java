package py.com.tipcsa.eva.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.vaadin.ui.Notification;


import py.com.tipcsa.eva.controllers.PuestoGrupoJpaController;
import py.com.tipcsa.eva.entities.Cargo;



import py.com.tipcsa.eva.entities.PuestoGrupo;

public class JpaPuestoGrupo extends PuestoGrupoJpaController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaPuestoGrupo(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public List<PuestoGrupo> findEvaluadorGrupobyCargo(Cargo cargo){
		
		EntityManager em = getEntityManager();
		List<PuestoGrupo> listEvaluadorGrupo = null;
		try {
			String sqlQry = " select * from puesto_grupo eg \n" + 
					"where eg.puesto = ?1";
			Query q = em.createNativeQuery(sqlQry, PuestoGrupo.class);
			q.setParameter(1, cargo.getCargo());
			listEvaluadorGrupo = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al consultar grupos por puesto ", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
		
		
		return listEvaluadorGrupo;
	}

	

}
