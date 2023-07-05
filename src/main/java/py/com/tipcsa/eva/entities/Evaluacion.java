/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.tipcsa.eva.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "evaluacion")
@NamedQueries({
    @NamedQuery(name = "Evaluacion.findAll", query = "SELECT e FROM Evaluacion e"),
    @NamedQuery(name = "Evaluacion.findByEvaluacion", query = "SELECT e FROM Evaluacion e WHERE e.evaluacion = :evaluacion"),
    @NamedQuery(name = "Evaluacion.findByPuntaje", query = "SELECT e FROM Evaluacion e WHERE e.puntaje = :puntaje"),
    @NamedQuery(name = "Evaluacion.findByPuntajeTotal", query = "SELECT e FROM Evaluacion e WHERE e.puntajeTotal = :puntajeTotal"),
    @NamedQuery(name = "Evaluacion.findByNota", query = "SELECT e FROM Evaluacion e WHERE e.nota = :nota"),
    @NamedQuery(name = "Evaluacion.findByFecha", query = "SELECT e FROM Evaluacion e WHERE e.fecha = :fecha")})
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
    @Basic(optional = false)
    @Column(name = "puntaje_total")
    private double puntajeTotal;
    @Column(name = "nota")
    private String nota;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "periodo", referencedColumnName = "periodo")
    @ManyToOne
    private Periodo periodo;
    @JoinColumn(name = "evaluado", referencedColumnName = "persona")
    @ManyToOne(optional = false)
    private Persona evaluado;
    @JoinColumn(name = "evaluador", referencedColumnName = "persona")
    @ManyToOne(optional = false)
    private Persona evaluador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluacion")
    private List<EvaluacionDetalle> evaluacionDetalleList;

    public Evaluacion() {
    }

    public Evaluacion(Integer evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Evaluacion(Integer evaluacion, double puntaje, double puntajeTotal) {
        this.evaluacion = evaluacion;
        this.puntaje = puntaje;
        this.puntajeTotal = puntajeTotal;
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

    public double getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(double puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Persona getEvaluado() {
        return evaluado;
    }

    public void setEvaluado(Persona evaluado) {
        this.evaluado = evaluado;
    }

    public Persona getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(Persona evaluador) {
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
