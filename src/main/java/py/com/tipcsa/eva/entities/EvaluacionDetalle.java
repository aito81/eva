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
 * @author santiago
 */
@Entity
@Table(name = "evaluacion_detalle")
@NamedQueries({
    @NamedQuery(name = "EvaluacionDetalle.findAll", query = "SELECT e FROM EvaluacionDetalle e"),
    @NamedQuery(name = "EvaluacionDetalle.findByEvaluacionDetalle", query = "SELECT e FROM EvaluacionDetalle e WHERE e.evaluacionDetalle = :evaluacionDetalle")})
public class EvaluacionDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "evaluacion_detalle")
    private Integer evaluacionDetalle;
    @JoinColumn(name = "evaluacion", referencedColumnName = "evaluacion")
    @ManyToOne(optional = false)
    private Evaluacion evaluacion;
    @JoinColumn(name = "pregunta", referencedColumnName = "pregunta")
    @ManyToOne(optional = false)
    private Pregunta pregunta;
    @JoinColumn(name = "valor", referencedColumnName = "valor")
    @ManyToOne(optional = false)
    private Valor valor;

    public EvaluacionDetalle() {
    }

    public EvaluacionDetalle(Integer evaluacionDetalle) {
        this.evaluacionDetalle = evaluacionDetalle;
    }

    public Integer getEvaluacionDetalle() {
        return evaluacionDetalle;
    }

    public void setEvaluacionDetalle(Integer evaluacionDetalle) {
        this.evaluacionDetalle = evaluacionDetalle;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public Valor getValor() {
        return valor;
    }

    public void setValor(Valor valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacionDetalle != null ? evaluacionDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionDetalle)) {
            return false;
        }
        EvaluacionDetalle other = (EvaluacionDetalle) object;
        if ((this.evaluacionDetalle == null && other.evaluacionDetalle != null) || (this.evaluacionDetalle != null && !this.evaluacionDetalle.equals(other.evaluacionDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.EvaluacionDetalle[ evaluacionDetalle=" + evaluacionDetalle + " ]";
    }
    
}
