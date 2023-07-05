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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "nivel_acceso")
@NamedQueries({
    @NamedQuery(name = "NivelAcceso.findAll", query = "SELECT n FROM NivelAcceso n"),
    @NamedQuery(name = "NivelAcceso.findByNivelAcceso", query = "SELECT n FROM NivelAcceso n WHERE n.nivelAcceso = :nivelAcceso"),
    @NamedQuery(name = "NivelAcceso.findByDescripcion", query = "SELECT n FROM NivelAcceso n WHERE n.descripcion = :descripcion")})
public class NivelAcceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "nivel_acceso")
    private Integer nivelAcceso;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nivelAcceso")
    private List<Usuario> usuarioList;

    public NivelAcceso() {
    }

    public NivelAcceso(Integer nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    public NivelAcceso(Integer nivelAcceso, String descripcion) {
        this.nivelAcceso = nivelAcceso;
        this.descripcion = descripcion;
    }

    public Integer getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(Integer nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nivelAcceso != null ? nivelAcceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NivelAcceso)) {
            return false;
        }
        NivelAcceso other = (NivelAcceso) object;
        if ((this.nivelAcceso == null && other.nivelAcceso != null) || (this.nivelAcceso != null && !this.nivelAcceso.equals(other.nivelAcceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.NivelAcceso[ nivelAcceso=" + nivelAcceso + " ]";
    }
    
}
