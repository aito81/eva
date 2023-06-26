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
 * @author santiago
 */
@Entity
@Table(name = "grupo")
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g"),
    @NamedQuery(name = "Grupo.findByGrupo", query = "SELECT g FROM Grupo g WHERE g.grupo = :grupo"),
    @NamedQuery(name = "Grupo.findByDescripcion", query = "SELECT g FROM Grupo g WHERE g.descripcion = :descripcion")})
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "grupo")
    private Integer grupo;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private List<Persona> personaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private List<GrupoPregunta> grupoPreguntaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private List<EvaluadorGrupo> evaluadorGrupoList;

    public Grupo() {
    }

    public Grupo(Integer grupo) {
        this.grupo = grupo;
    }

    public Grupo(Integer grupo, String descripcion) {
        this.grupo = grupo;
        this.descripcion = descripcion;
    }

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
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

    public List<GrupoPregunta> getGrupoPreguntaList() {
        return grupoPreguntaList;
    }

    public void setGrupoPreguntaList(List<GrupoPregunta> grupoPreguntaList) {
        this.grupoPreguntaList = grupoPreguntaList;
    }

    public List<EvaluadorGrupo> getEvaluadorGrupoList() {
        return evaluadorGrupoList;
    }

    public void setEvaluadorGrupoList(List<EvaluadorGrupo> evaluadorGrupoList) {
        this.evaluadorGrupoList = evaluadorGrupoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupo != null ? grupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.grupo == null && other.grupo != null) || (this.grupo != null && !this.grupo.equals(other.grupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.Grupo[ grupo=" + grupo + " ]";
    }
    
}
