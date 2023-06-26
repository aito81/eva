package py.com.tipcsa.eva.jpa;

import javax.persistence.EntityManagerFactory;

import py.com.tipcsa.eva.controllers.CargoJpaController;

public class JpaCargo extends CargoJpaController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaCargo(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

}
