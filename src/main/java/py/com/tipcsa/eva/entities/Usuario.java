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
@Table(name = "usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuario.findByDescripcion", query = "SELECT u FROM Usuario u WHERE u.descripcion = :descripcion"),
    @NamedQuery(name = "Usuario.findByClave", query = "SELECT u FROM Usuario u WHERE u.clave = :clave"),
    @NamedQuery(name = "Usuario.findByActivo", query = "SELECT u FROM Usuario u WHERE u.activo = :activo")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usuario")
    private Integer usuario;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "clave")
    private String clave;
    @Basic(optional = false)
    @Column(name = "Activo")
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluado")
    private List<Evaluacion> evaluacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluador")
    private List<Evaluacion> evaluacionList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluado")
    private List<EvaluadorEvaluado> evaluadorEvaluadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluador")
    private List<EvaluadorEvaluado> evaluadorEvaluadoList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<PerfilUsuario> perfilUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluador")
    private List<EvaluadorGrupo> evaluadorGrupoList;
    @JoinColumn(name = "nivel_acceso", referencedColumnName = "nivel_acceso")
    @ManyToOne(optional = false)
    private NivelAcceso nivelAcceso;

    public Usuario() {
    }

    public Usuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Usuario(Integer usuario, String descripcion, String clave, boolean activo) {
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.clave = clave;
        this.activo = activo;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Evaluacion> getEvaluacionList() {
        return evaluacionList;
    }

    public void setEvaluacionList(List<Evaluacion> evaluacionList) {
        this.evaluacionList = evaluacionList;
    }

    public List<Evaluacion> getEvaluacionList1() {
        return evaluacionList1;
    }

    public void setEvaluacionList1(List<Evaluacion> evaluacionList1) {
        this.evaluacionList1 = evaluacionList1;
    }

    public List<EvaluadorEvaluado> getEvaluadorEvaluadoList() {
        return evaluadorEvaluadoList;
    }

    public void setEvaluadorEvaluadoList(List<EvaluadorEvaluado> evaluadorEvaluadoList) {
        this.evaluadorEvaluadoList = evaluadorEvaluadoList;
    }

    public List<EvaluadorEvaluado> getEvaluadorEvaluadoList1() {
        return evaluadorEvaluadoList1;
    }

    public void setEvaluadorEvaluadoList1(List<EvaluadorEvaluado> evaluadorEvaluadoList1) {
        this.evaluadorEvaluadoList1 = evaluadorEvaluadoList1;
    }

    public List<PerfilUsuario> getPerfilUsuarioList() {
        return perfilUsuarioList;
    }

    public void setPerfilUsuarioList(List<PerfilUsuario> perfilUsuarioList) {
        this.perfilUsuarioList = perfilUsuarioList;
    }

    public List<EvaluadorGrupo> getEvaluadorGrupoList() {
        return evaluadorGrupoList;
    }

    public void setEvaluadorGrupoList(List<EvaluadorGrupo> evaluadorGrupoList) {
        this.evaluadorGrupoList = evaluadorGrupoList;
    }

    public NivelAcceso getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(NivelAcceso nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.Usuario[ usuario=" + usuario + " ]";
    }
    
}
