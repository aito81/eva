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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author santiago
 */
@Entity
@Table(name = "ciudad")
@NamedQueries({
    @NamedQuery(name = "Ciudad.findAll", query = "SELECT c FROM Ciudad c"),
    @NamedQuery(name = "Ciudad.findByCiudad", query = "SELECT c FROM Ciudad c WHERE c.ciudad = :ciudad"),
    @NamedQuery(name = "Ciudad.findByDepartamento", query = "SELECT c FROM Ciudad c WHERE c.departamento = :departamento"),
    @NamedQuery(name = "Ciudad.findByDescripcion", query = "SELECT c FROM Ciudad c WHERE c.descripcion = :descripcion")})
public class Ciudad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ciudad")
    private Integer ciudad;
    @Basic(optional = false)
    @Column(name = "departamento")
    private int departamento;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;

    public Ciudad() {
    }

    public Ciudad(Integer ciudad) {
        this.ciudad = ciudad;
    }

    public Ciudad(Integer ciudad, int departamento, String descripcion) {
        this.ciudad = ciudad;
        this.departamento = departamento;
        this.descripcion = descripcion;
    }

    public Integer getCiudad() {
        return ciudad;
    }

    public void setCiudad(Integer ciudad) {
        this.ciudad = ciudad;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciudad != null ? ciudad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciudad)) {
            return false;
        }
        Ciudad other = (Ciudad) object;
        if ((this.ciudad == null && other.ciudad != null) || (this.ciudad != null && !this.ciudad.equals(other.ciudad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.Ciudad[ ciudad=" + ciudad + " ]";
    }
    
}
