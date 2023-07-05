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
@Table(name = "puesto_grupo")
@NamedQueries({
    @NamedQuery(name = "PuestoGrupo.findAll", query = "SELECT p FROM PuestoGrupo p"),
    @NamedQuery(name = "PuestoGrupo.findByPuestoGrupo", query = "SELECT p FROM PuestoGrupo p WHERE p.puestoGrupo = :puestoGrupo")})
public class PuestoGrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "puesto_grupo")
    private Integer puestoGrupo;
    @JoinColumn(name = "puesto", referencedColumnName = "cargo")
    @ManyToOne(optional = false)
    private Cargo puesto;
    @JoinColumn(name = "grupo", referencedColumnName = "grupo")
    @ManyToOne(optional = false)
    private Grupo grupo;

    public PuestoGrupo() {
    }

    public PuestoGrupo(Integer puestoGrupo) {
        this.puestoGrupo = puestoGrupo;
    }

    public Integer getPuestoGrupo() {
        return puestoGrupo;
    }

    public void setPuestoGrupo(Integer puestoGrupo) {
        this.puestoGrupo = puestoGrupo;
    }

    public Cargo getPuesto() {
        return puesto;
    }

    public void setPuesto(Cargo puesto) {
        this.puesto = puesto;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (puestoGrupo != null ? puestoGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PuestoGrupo)) {
            return false;
        }
        PuestoGrupo other = (PuestoGrupo) object;
        if ((this.puestoGrupo == null && other.puestoGrupo != null) || (this.puestoGrupo != null && !this.puestoGrupo.equals(other.puestoGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.PuestoGrupo[ puestoGrupo=" + puestoGrupo + " ]";
    }
    
}
