package py.com.tipcsa.eva.jpa;

import javax.persistence.EntityManagerFactory;

import py.com.tipcsa.eva.controllers.NivelAccesoJpaController;
import py.com.tipcsa.eva.entities.NivelAcceso;

public class JpaNivelAcceso extends NivelAccesoJpaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaNivelAcceso(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

}
