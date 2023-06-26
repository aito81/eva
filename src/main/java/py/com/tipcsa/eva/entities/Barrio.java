/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.tipcsa.eva.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author santiago
 */
@Entity
@Table(name = "barrio")
@NamedQueries({
    @NamedQuery(name = "Barrio.findAll", query = "SELECT b FROM Barrio b"),
    @NamedQuery(name = "Barrio.findByBarrio", query = "SELECT b FROM Barrio b WHERE b.barrio = :barrio"),
    @NamedQuery(name = "Barrio.findByCiudad", query = "SELECT b FROM Barrio b WHERE b.ciudad = :ciudad"),
    @NamedQuery(name = "Barrio.findByDescripcion", query = "SELECT b FROM Barrio b WHERE b.descripcion = :descripcion")})
public class Barrio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "barrio")
    private Integer barrio;
    @Basic(optional = false)
    @Column(name = "ciudad")
    private int ciudad;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "barrio")
    private List<Persona> personaList;

    public Barrio() {
    }

    public Barrio(Integer barrio) {
        this.barrio = barrio;
    }

    public Barrio(Integer barrio, int ciudad, String descripcion) {
        this.barrio = barrio;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
    }

    public Integer getBarrio() {
        return barrio;
    }

    public void setBarrio(Integer barrio) {
        this.barrio = barrio;
    }

    public int getCiudad() {
        return ciudad;
    }

    public void setCiudad(int ciudad) {
        this.ciudad = ciudad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (barrio != null ? barrio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Barrio)) {
            return false;
        }
        Barrio other = (Barrio) object;
        if ((this.barrio == null && other.barrio != null) || (this.barrio != null && !this.barrio.equals(other.barrio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.Barrio[ barrio=" + barrio + " ]";
    }
    
}
