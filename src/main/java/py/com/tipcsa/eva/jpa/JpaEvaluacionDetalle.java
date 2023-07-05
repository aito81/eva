package py.com.tipcsa.eva.jpa;

import javax.persistence.EntityManagerFactory;

import py.com.tipcsa.eva.controllers.EvaluacionDetalleJpaController;

public class JpaEvaluacionDetalle extends EvaluacionDetalleJpaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaEvaluacionDetalle(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

}
