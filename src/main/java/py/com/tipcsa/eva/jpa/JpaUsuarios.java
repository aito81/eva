package py.com.tipcsa.eva.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import py.com.tipcsa.eva.controllers.UsuarioJpaController;
import py.com.tipcsa.eva.util.Crypto;

public class JpaUsuarios extends UsuarioJpaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaUsuarios(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	
	public Boolean login(String user, String pass){
		EntityManager em = getEntityManager();
		Boolean adelante = false;
		String password = "'" + Crypto.encriptarPass(pass)+ "'";
		password = pass;
		try {
			String sql = "select * from usuario where descripcion = ?1 and clave = ?2" ;
			Query q = em.createNativeQuery(sql);
			q.setParameter(1, user);
			q.setParameter(2, password);
			if (    !(q.getSingleResult().equals(null))   ) {
				if( !q.getSingleResult().toString().equals("0")){
					adelante = true;
				}
			}
			
			
		} catch (Exception e) {
			//Notification.show("Error al buscar datos " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}
		return adelante;
	}

}
