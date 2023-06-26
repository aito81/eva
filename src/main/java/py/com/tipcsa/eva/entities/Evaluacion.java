/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.tipcsa.eva.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author santiago
 */
@Entity
@Table(name = "evaluacion")
@NamedQueries({
    @NamedQuery(name = "Evaluacion.findAll", query = "SELECT e FROM Evaluacion e"),
    @NamedQuery(name = "Evaluacion.findByEvaluacion", query = "SELECT e FROM Evaluacion e WHERE e.evaluacion = :evaluacion"),
    @NamedQuery(name = "Evaluacion.findByPuntaje", query = "SELECT e FROM Evaluacion e WHERE e.puntaje = :puntaje")})
public class Evaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "evaluacion")
    private Integer evaluacion;
    @Basic(optional = false)
    @Column(name = "puntaje")
    private double puntaje;
    @JoinColumn(name = "evaluado", referencedColumnName = "usuario")
    @ManyToOne(optional = false)
    private Usuario evaluado;
    @JoinColumn(name = "evaluador", referencedColumnName = "usuario")
    @ManyToOne(optional = false)
    private Usuario evaluador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluacion")
    private List<EvaluacionDetalle> evaluacionDetalleList;

    public Evaluacion() {
    }

    public Evaluacion(Integer evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Evaluacion(Integer evaluacion, double puntaje) {
        this.evaluacion = evaluacion;
        this.puntaje = puntaje;
    }

    public Integer getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Integer evaluacion) {
        this.evaluacion = evaluacion;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public Usuario getEvaluado() {
        return evaluado;
    }

    public void setEvaluado(Usuario evaluado) {
        this.evaluado = evaluado;
    }

    public Usuario getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(Usuario evaluador) {
        this.evaluador = evaluador;
    }

    public List<EvaluacionDetalle> getEvaluacionDetalleList() {
        return evaluacionDetalleList;
    }

    public void setEvaluacionDetalleList(List<EvaluacionDetalle> evaluacionDetalleList) {
        this.evaluacionDetalleList = evaluacionDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacion != null ? evaluacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evaluacion)) {
            return false;
        }
        Evaluacion other = (Evaluacion) object;
        if ((this.evaluacion == null && other.evaluacion != null) || (this.evaluacion != null && !this.evaluacion.equals(other.evaluacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.Evaluacion[ evaluacion=" + evaluacion + " ]";
    }
    
}
