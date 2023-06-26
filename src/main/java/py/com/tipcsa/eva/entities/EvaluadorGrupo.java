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
@Table(name = "evaluador_grupo")
@NamedQueries({
    @NamedQuery(name = "EvaluadorGrupo.findAll", query = "SELECT e FROM EvaluadorGrupo e"),
    @NamedQuery(name = "EvaluadorGrupo.findByEvaluadorGrupo", query = "SELECT e FROM EvaluadorGrupo e WHERE e.evaluadorGrupo = :evaluadorGrupo")})
public class EvaluadorGrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "evaluador_grupo")
    private Integer evaluadorGrupo;
    @JoinColumn(name = "grupo", referencedColumnName = "grupo")
    @ManyToOne(optional = false)
    private Grupo grupo;
    @JoinColumn(name = "evaluador", referencedColumnName = "usuario")
    @ManyToOne(optional = false)
    private Usuario evaluador;

    public EvaluadorGrupo() {
    }

    public EvaluadorGrupo(Integer evaluadorGrupo) {
        this.evaluadorGrupo = evaluadorGrupo;
    }

    public Integer getEvaluadorGrupo() {
        return evaluadorGrupo;
    }

    public void setEvaluadorGrupo(Integer evaluadorGrupo) {
        this.evaluadorGrupo = evaluadorGrupo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Usuario getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(Usuario evaluador) {
        this.evaluador = evaluador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluadorGrupo != null ? evaluadorGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluadorGrupo)) {
            return false;
        }
        EvaluadorGrupo other = (EvaluadorGrupo) object;
        if ((this.evaluadorGrupo == null && other.evaluadorGrupo != null) || (this.evaluadorGrupo != null && !this.evaluadorGrupo.equals(other.evaluadorGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.EvaluadorGrupo[ evaluadorGrupo=" + evaluadorGrupo + " ]";
    }
    
}
