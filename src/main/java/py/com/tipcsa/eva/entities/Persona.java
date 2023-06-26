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
@Table(name = "persona")
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findByPersona", query = "SELECT p FROM Persona p WHERE p.persona = :persona"),
    @NamedQuery(name = "Persona.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Persona.findByApellido", query = "SELECT p FROM Persona p WHERE p.apellido = :apellido"),
    @NamedQuery(name = "Persona.findByDireccion", query = "SELECT p FROM Persona p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Persona.findByCodigoHumano", query = "SELECT p FROM Persona p WHERE p.codigoHumano = :codigoHumano"),
    @NamedQuery(name = "Persona.findByNroDocumento", query = "SELECT p FROM Persona p WHERE p.nroDocumento = :nroDocumento"),
    @NamedQuery(name = "Persona.findByCorreoPersonal", query = "SELECT p FROM Persona p WHERE p.correoPersonal = :correoPersonal"),
    @NamedQuery(name = "Persona.findByCorreoCorporativo", query = "SELECT p FROM Persona p WHERE p.correoCorporativo = :correoCorporativo"),
    @NamedQuery(name = "Persona.findByFoto", query = "SELECT p FROM Persona p WHERE p.foto = :foto"),
    @NamedQuery(name = "Persona.findByFotoDocumento", query = "SELECT p FROM Persona p WHERE p.fotoDocumento = :fotoDocumento"),
    @NamedQuery(name = "Persona.findByHuellaId", query = "SELECT p FROM Persona p WHERE p.huellaId = :huellaId"),
    @NamedQuery(name = "Persona.findByTelefono", query = "SELECT p FROM Persona p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Persona.findByVigente", query = "SELECT p FROM Persona p WHERE p.vigente = :vigente")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "persona")
    private Integer persona;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "codigo_humano")
    private int codigoHumano;
    @Basic(optional = false)
    @Column(name = "nro_documento")
    private int nroDocumento;
    @Column(name = "correo_personal")
    private String correoPersonal;
    @Column(name = "correo_corporativo")
    private String correoCorporativo;
    @Column(name = "foto")
    private String foto;
    @Column(name = "foto_documento")
    private String fotoDocumento;
    @Column(name = "huella_id")
    private Integer huellaId;
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "vigente")
    private boolean vigente;
    @JoinColumn(name = "area", referencedColumnName = "area")
    @ManyToOne
    private Area area;
    @JoinColumn(name = "barrio", referencedColumnName = "barrio")
    @ManyToOne
    private Barrio barrio;
    @JoinColumn(name = "cargo", referencedColumnName = "cargo")
    @ManyToOne(optional = false)
    private Cargo cargo;
    @JoinColumn(name = "grupo", referencedColumnName = "grupo")
    @ManyToOne(optional = false)
    private Grupo grupo;
    @JoinColumn(name = "sucursal", referencedColumnName = "sucursal")
    @ManyToOne(optional = false)
    private Sucursal sucursal;

    public Persona() {
    }

    public Persona(Integer persona) {
        this.persona = persona;
    }

    public Persona(Integer persona, String nombre, String apellido, int codigoHumano, int nroDocumento, boolean vigente) {
        this.persona = persona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.codigoHumano = codigoHumano;
        this.nroDocumento = nroDocumento;
        this.vigente = vigente;
    }

    public Integer getPersona() {
        return persona;
    }

    public void setPersona(Integer persona) {
        this.persona = persona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCodigoHumano() {
        return codigoHumano;
    }

    public void setCodigoHumano(int codigoHumano) {
        this.codigoHumano = codigoHumano;
    }

    public int getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(int nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getCorreoPersonal() {
        return correoPersonal;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    public String getCorreoCorporativo() {
        return correoCorporativo;
    }

    public void setCorreoCorporativo(String correoCorporativo) {
        this.correoCorporativo = correoCorporativo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFotoDocumento() {
        return fotoDocumento;
    }

    public void setFotoDocumento(String fotoDocumento) {
        this.fotoDocumento = fotoDocumento;
    }

    public Integer getHuellaId() {
        return huellaId;
    }

    public void setHuellaId(Integer huellaId) {
        this.huellaId = huellaId;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean getVigente() {
        return vigente;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (persona != null ? persona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.persona == null && other.persona != null) || (this.persona != null && !this.persona.equals(other.persona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.tipcsa.eva.entities.Persona[ persona=" + persona + " ]";
    }
    
}
