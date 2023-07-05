/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.tipcsa.eva.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "cargo_pregunta")
@NamedQueries({
    @NamedQuery(name = "CargoPregunta.findAll", query = "SELECT c FROM CargoPregunta c"),
    @NamedQuery(name = "CargoPregunta.findByCargoPregunta", query = "SELECT c FROM CargoPregunta c WHERE c.cargoPregunta = :cargoPregunta"),
    @NamedQuery(name = "CargoPregunta.findByPeso", query = "SELECT c FROM CargoPregunta c WHERE c.peso = :peso")})
public class CargoPregunta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cargo_pregunta")
    private Integer cargoPregunta;
    @Basic(optional = false)
    @Column(name = "peso")
    private double peso;
    @JoinColumn(name = "cargo", referencedColumnName = "cargo")
    @ManyToOne(optional = false)
    private Cargo cargo;
    @JoinColumn(name = "pregunta", referencedColumnName = "pregunta")
    @ManyToOne(optional = false)
    private Pregunta pregunta;

    public CargoPregunta() {
    }

    public CargoPregunta(Integer cargoPregunta) {
        this.cargoPregunta = cargoPregunta;
    }

    public CargoPregunta(Integer cargoPregunta, double peso) {
        this.cargoPregunta = cargoPregunta;
        this.peso = peso;
    }

    public Integer getCargoPregunta() {
        return cargoPregunta;
    }

    public void setCargoPregunta(Integer cargoPregunta) {
        this.cargoPregunta = cargoPregunta;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cargoPregunta != null ? cargoPregunta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CargoPregunta)) {
            return false;
        }
        CargoPregunta other = (CargoPregunta) object;
        if ((this.cargoPregunta == null && other.cargoPregunta != null) || (this.cargoPregunta != null && !this.cargoPregunta.equals(other.cargoPregunta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.CargoPregunta[ cargoPregunta=" + cargoPregunta + " ]";
    }
    
}
