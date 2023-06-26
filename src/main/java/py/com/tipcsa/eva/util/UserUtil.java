package py.com.tipcsa.eva.util;

import com.vaadin.server.VaadinSession;

import py.com.tipcsa.eva.entities.Usuario;
import py.com.tipcsa.eva.jpa.JpaUsuarios;


public class UserUtil {
	
	private static final String KEY = "currentuser";
	private static JpaUsuarios jpaUsuario = new JpaUsuarios(JpaUtil.getEntityManagerFactory());
	
	public static void setUsuario(Usuario user) {
        VaadinSession.getCurrent().setAttribute(KEY, user);
	}
	
	public static Usuario getUsuario() {
    	return (Usuario) VaadinSession.getCurrent().getAttribute(KEY); 
    }
	
	public static void set(Usuario user) {
        VaadinSession.getCurrent().setAttribute(KEY, user);
    }
	
	public static boolean isLoggedIn() {
        return getUsuario() != null;
    }

}
