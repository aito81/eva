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
@Table(name = "cargo")
@NamedQueries({
    @NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c"),
    @NamedQuery(name = "Cargo.findByCargo", query = "SELECT c FROM Cargo c WHERE c.cargo = :cargo"),
    @NamedQuery(name = "Cargo.findByDescripcion", query = "SELECT c FROM Cargo c WHERE c.descripcion = :descripcion")})
public class Cargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cargo")
    private Integer cargo;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private List<CargoPregunta> cargoPreguntaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private List<EvaluadorCargo> evaluadorCargoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluador")
    private List<EvaluadorCargo> evaluadorCargoList1;
    @OneToMany(mappedBy = "cargo")
    private List<Persona> personaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluador")
    private List<EvaluadorEvaluado> evaluadorEvaluadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "puesto")
    private List<PuestoGrupo> puestoGrupoList;

    public Cargo() {
    }

    public Cargo(Integer cargo) {
        this.cargo = cargo;
    }

    public Cargo(Integer cargo, String descripcion) {
        this.cargo = cargo;
        this.descripcion = descripcion;
    }

    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<CargoPregunta> getCargoPreguntaList() {
        return cargoPreguntaList;
    }

    public void setCargoPreguntaList(List<CargoPregunta> cargoPreguntaList) {
        this.cargoPreguntaList = cargoPreguntaList;
    }

    public List<EvaluadorCargo> getEvaluadorCargoList() {
        return evaluadorCargoList;
    }

    public void setEvaluadorCargoList(List<EvaluadorCargo> evaluadorCargoList) {
        this.evaluadorCargoList = evaluadorCargoList;
    }

    public List<EvaluadorCargo> getEvaluadorCargoList1() {
        return evaluadorCargoList1;
    }

    public void setEvaluadorCargoList1(List<EvaluadorCargo> evaluadorCargoList1) {
        this.evaluadorCargoList1 = evaluadorCargoList1;
    }

    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    public List<EvaluadorEvaluado> getEvaluadorEvaluadoList() {
        return evaluadorEvaluadoList;
    }

    public void setEvaluadorEvaluadoList(List<EvaluadorEvaluado> evaluadorEvaluadoList) {
        this.evaluadorEvaluadoList = evaluadorEvaluadoList;
    }

    public List<PuestoGrupo> getPuestoGrupoList() {
        return puestoGrupoList;
    }

    public void setPuestoGrupoList(List<PuestoGrupo> puestoGrupoList) {
        this.puestoGrupoList = puestoGrupoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cargo != null ? cargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cargo)) {
            return false;
        }
        Cargo other = (Cargo) object;
        if ((this.cargo == null && other.cargo != null) || (this.cargo != null && !this.cargo.equals(other.cargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.Cargo[ cargo=" + cargo + " ]";
    }
    
}
