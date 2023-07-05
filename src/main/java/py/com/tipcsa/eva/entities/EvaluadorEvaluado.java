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
@Table(name = "evaluador_evaluado")
@NamedQueries({
    @NamedQuery(name = "EvaluadorEvaluado.findAll", query = "SELECT e FROM EvaluadorEvaluado e"),
    @NamedQuery(name = "EvaluadorEvaluado.findByEvaluadorEvaluado", query = "SELECT e FROM EvaluadorEvaluado e WHERE e.evaluadorEvaluado = :evaluadorEvaluado")})
public class EvaluadorEvaluado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "evaluador_evaluado")
    private Integer evaluadorEvaluado;
    @JoinColumn(name = "evaluador", referencedColumnName = "cargo")
    @ManyToOne(optional = false)
    private Cargo evaluador;
    @JoinColumn(name = "evaluado", referencedColumnName = "persona")
    @ManyToOne(optional = false)
    private Persona evaluado;

    public EvaluadorEvaluado() {
    }

    public EvaluadorEvaluado(Integer evaluadorEvaluado) {
        this.evaluadorEvaluado = evaluadorEvaluado;
    }

    public Integer getEvaluadorEvaluado() {
        return evaluadorEvaluado;
    }

    public void setEvaluadorEvaluado(Integer evaluadorEvaluado) {
        this.evaluadorEvaluado = evaluadorEvaluado;
    }

    public Cargo getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(Cargo evaluador) {
        this.evaluador = evaluador;
    }

    public Persona getEvaluado() {
        return evaluado;
    }

    public void setEvaluado(Persona evaluado) {
        this.evaluado = evaluado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluadorEvaluado != null ? evaluadorEvaluado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluadorEvaluado)) {
            return false;
        }
        EvaluadorEvaluado other = (EvaluadorEvaluado) object;
        if ((this.evaluadorEvaluado == null && other.evaluadorEvaluado != null) || (this.evaluadorEvaluado != null && !this.evaluadorEvaluado.equals(other.evaluadorEvaluado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.EvaluadorEvaluado[ evaluadorEvaluado=" + evaluadorEvaluado + " ]";
    }
    
}
