package py.com.tipcsa.eva.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.vaadin.ui.Notification;

import py.com.tipcsa.eva.controllers.UsuarioJpaController;
import py.com.tipcsa.eva.entities.Usuario;
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
	
	
	
	
	
	public Boolean findUsuarioRepetido(String usuario){
		EntityManager em = getEntityManager();
		usuario = "'" + usuario + "'";
		Boolean repetido = false;
		try {
			String sql = " select * from usuario u where u.descripcion = " + usuario;
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
	
	
	public Usuario findUsuarioByUser(String user) {
    	EntityManager em = getEntityManager();
    	Usuario usuario = null;
    	try {
    		
    		
            javax.persistence.criteria.CriteriaBuilder cb = em.getCriteriaBuilder();
            javax.persistence.criteria.CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            javax.persistence.criteria.Root<Usuario> u = cq.from(Usuario.class);
            cq.where(cb.equal(u.get("descripcion"), user));
            Query q = em.createQuery(cq).setHint("eclipselink.refresh", true).setMaxResults(1);
            usuario = (Usuario) q.getSingleResult();
    	} catch (Exception ex) {
    		// retornara null
        } finally {
            em.close();
        }
    	
    	return usuario;
    }
	
	
	
	
	
	

}
