package py.com.tipcsa.eva.beans;

import py.com.tipcsa.eva.entities.Grupo;

public class GrupoAcumuladorContador {
	
	private Grupo grupo;
	private int contador;
	private Double acumulador;
	
	
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public int getContador() {
		return contador;
	}
	public void setContador(int contador) {
		this.contador = contador;
	}
	public Double getAcumulador() {
		return acumulador;
	}
	public void setAcumulador(Double acumulador) {
		this.acumulador = acumulador;
	}
}
