package py.com.tipcsa.eva.jpa;

import javax.persistence.EntityManagerFactory;

import py.com.tipcsa.eva.controllers.ValorJpaController;

public class JpaValor extends ValorJpaController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaValor(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

}
