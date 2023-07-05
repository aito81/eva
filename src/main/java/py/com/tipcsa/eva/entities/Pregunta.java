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
@Table(name = "pregunta")
@NamedQueries({
    @NamedQuery(name = "Pregunta.findAll", query = "SELECT p FROM Pregunta p"),
    @NamedQuery(name = "Pregunta.findByPregunta", query = "SELECT p FROM Pregunta p WHERE p.pregunta = :pregunta"),
    @NamedQuery(name = "Pregunta.findByDescripcion", query = "SELECT p FROM Pregunta p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Pregunta.findByActivo", query = "SELECT p FROM Pregunta p WHERE p.activo = :activo")})
public class Pregunta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pregunta")
    private Integer pregunta;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pregunta")
    private List<CargoPregunta> cargoPreguntaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pregunta")
    private List<GrupoPregunta> grupoPreguntaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pregunta")
    private List<EvaluacionDetalle> evaluacionDetalleList;

    public Pregunta() {
    }

    public Pregunta(Integer pregunta) {
        this.pregunta = pregunta;
    }

    public Pregunta(Integer pregunta, String descripcion, boolean activo) {
        this.pregunta = pregunta;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public Integer getPregunta() {
        return pregunta;
    }

    public void setPregunta(Integer pregunta) {
        this.pregunta = pregunta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<CargoPregunta> getCargoPreguntaList() {
        return cargoPreguntaList;
    }

    public void setCargoPreguntaList(List<CargoPregunta> cargoPreguntaList) {
        this.cargoPreguntaList = cargoPreguntaList;
    }

    public List<GrupoPregunta> getGrupoPreguntaList() {
        return grupoPreguntaList;
    }

    public void setGrupoPreguntaList(List<GrupoPregunta> grupoPreguntaList) {
        this.grupoPreguntaList = grupoPreguntaList;
    }

    public List<EvaluacionDetalle> getEvaluacionDetalleList() {
        return evaluacionDetalleList;
    }

    public void setEvaluacionDetalleList(List<EvaluacionDetalle> evaluacionDetalleList) {
        this.evaluacionDetalleList = evaluacionDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pregunta != null ? pregunta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pregunta)) {
            return false;
        }
        Pregunta other = (Pregunta) object;
        if ((this.pregunta == null && other.pregunta != null) || (this.pregunta != null && !this.pregunta.equals(other.pregunta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.Pregunta[ pregunta=" + pregunta + " ]";
    }
    
}
