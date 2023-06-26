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
@Table(name = "perfil_usuario")
@NamedQueries({
    @NamedQuery(name = "PerfilUsuario.findAll", query = "SELECT p FROM PerfilUsuario p"),
    @NamedQuery(name = "PerfilUsuario.findByPerfilUsuario", query = "SELECT p FROM PerfilUsuario p WHERE p.perfilUsuario = :perfilUsuario")})
public class PerfilUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "perfil_usuario")
    private Integer perfilUsuario;
    @JoinColumn(name = "perfil", referencedColumnName = "perfil")
    @ManyToOne(optional = false)
    private Perfil perfil;
    @JoinColumn(name = "usuario", referencedColumnName = "usuario")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public PerfilUsuario() {
    }

    public PerfilUsuario(Integer perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }

    public Integer getPerfilUsuario() {
        return perfilUsuario;
    }

    public void setPerfilUsuario(Integer perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perfilUsuario != null ? perfilUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerfilUsuario)) {
            return false;
        }
        PerfilUsuario other = (PerfilUsuario) object;
        if ((this.perfilUsuario == null && other.perfilUsuario != null) || (this.perfilUsuario != null && !this.perfilUsuario.equals(other.perfilUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.PerfilUsuario[ perfilUsuario=" + perfilUsuario + " ]";
    }
    
}
