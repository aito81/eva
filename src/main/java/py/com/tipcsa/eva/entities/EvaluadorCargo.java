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
@Table(name = "evaluador_cargo")
@NamedQueries({
    @NamedQuery(name = "EvaluadorCargo.findAll", query = "SELECT e FROM EvaluadorCargo e"),
    @NamedQuery(name = "EvaluadorCargo.findByEvaluadorCargo", query = "SELECT e FROM EvaluadorCargo e WHERE e.evaluadorCargo = :evaluadorCargo")})
public class EvaluadorCargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "evaluador_cargo")
    private Integer evaluadorCargo;
    @JoinColumn(name = "cargo", referencedColumnName = "cargo")
    @ManyToOne(optional = false)
    private Cargo cargo;
    @JoinColumn(name = "evaluador", referencedColumnName = "cargo")
    @ManyToOne(optional = false)
    private Cargo evaluador;

    public EvaluadorCargo() {
    }

    public EvaluadorCargo(Integer evaluadorCargo) {
        this.evaluadorCargo = evaluadorCargo;
    }

    public Integer getEvaluadorCargo() {
        return evaluadorCargo;
    }

    public void setEvaluadorCargo(Integer evaluadorCargo) {
        this.evaluadorCargo = evaluadorCargo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Cargo getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(Cargo evaluador) {
        this.evaluador = evaluador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluadorCargo != null ? evaluadorCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluadorCargo)) {
            return false;
        }
        EvaluadorCargo other = (EvaluadorCargo) object;
        if ((this.evaluadorCargo == null && other.evaluadorCargo != null) || (this.evaluadorCargo != null && !this.evaluadorCargo.equals(other.evaluadorCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.EvaluadorCargo[ evaluadorCargo=" + evaluadorCargo + " ]";
    }
    
}
