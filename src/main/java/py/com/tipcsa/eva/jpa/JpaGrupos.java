package py.com.tipcsa.eva.jpa;

import javax.persistence.EntityManagerFactory;

import py.com.tipcsa.eva.controllers.GrupoJpaController;

public class JpaGrupos extends GrupoJpaController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaGrupos(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

}
