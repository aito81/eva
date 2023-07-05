package py.com.tipcsa.eva.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.vaadin.ui.Notification;

import py.com.tipcsa.eva.controllers.AreaJpaController;

public class JpaArea extends AreaJpaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaArea(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	public Boolean findAreaRepetido(String area){
		EntityManager em = getEntityManager();
		area = "'" + area + "'";
		Boolean repetido = false;
		try {
			String sql = " select * from area a where a.descripcion = " + area;
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

}
