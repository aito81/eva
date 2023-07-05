package py.com.tipcsa.eva.beans;

import py.com.tipcsa.eva.entities.Evaluacion;

public class BeanEvaluacionCabecera {

	private Evaluacion evaluacion;
	
	public double getPuntajeTotal() {
		return puntajeTotal;
	}

	public void setPuntajeTotal(double puntajeTotal) {
		this.puntajeTotal = puntajeTotal;
	}

	public String getNota() {
		return Nota;
	}

	public void setNota(String nota) {
		Nota = nota;
	}

	private double puntajeTotal;
	
	private String Nota;
	
}
