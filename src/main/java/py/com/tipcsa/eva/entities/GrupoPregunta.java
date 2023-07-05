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
@Table(name = "grupo_pregunta")
@NamedQueries({
    @NamedQuery(name = "GrupoPregunta.findAll", query = "SELECT g FROM GrupoPregunta g"),
    @NamedQuery(name = "GrupoPregunta.findByGrupoPregunta", query = "SELECT g FROM GrupoPregunta g WHERE g.grupoPregunta = :grupoPregunta")})
public class GrupoPregunta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "grupo_pregunta")
    private Integer grupoPregunta;
    @JoinColumn(name = "grupo", referencedColumnName = "grupo")
    @ManyToOne(optional = false)
    private Grupo grupo;
    @JoinColumn(name = "pregunta", referencedColumnName = "pregunta")
    @ManyToOne(optional = false)
    private Pregunta pregunta;

    public GrupoPregunta() {
    }

    public GrupoPregunta(Integer grupoPregunta) {
        this.grupoPregunta = grupoPregunta;
    }

    public Integer getGrupoPregunta() {
        return grupoPregunta;
    }

    public void setGrupoPregunta(Integer grupoPregunta) {
        this.grupoPregunta = grupoPregunta;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoPregunta != null ? grupoPregunta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoPregunta)) {
            return false;
        }
        GrupoPregunta other = (GrupoPregunta) object;
        if ((this.grupoPregunta == null && other.grupoPregunta != null) || (this.grupoPregunta != null && !this.grupoPregunta.equals(other.grupoPregunta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.GrupoPregunta[ grupoPregunta=" + grupoPregunta + " ]";
    }
    
}
