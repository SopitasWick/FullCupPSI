/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "promocionporcentaje")
@NamedQueries({
    @NamedQuery(name = "Promocionporcentaje.findAll", query = "SELECT p FROM Promocionporcentaje p"),
    @NamedQuery(name = "Promocionporcentaje.findByIdPromocionPorcentaje", query = "SELECT p FROM Promocionporcentaje p WHERE p.idPromocionPorcentaje = :idPromocionPorcentaje"),
    @NamedQuery(name = "Promocionporcentaje.findByDescripcion", query = "SELECT p FROM Promocionporcentaje p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Promocionporcentaje.findByPorcentaje", query = "SELECT p FROM Promocionporcentaje p WHERE p.porcentaje = :porcentaje"),
    @NamedQuery(name = "Promocionporcentaje.findByFechaCreacion", query = "SELECT p FROM Promocionporcentaje p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Promocionporcentaje.findByFechaInicio", query = "SELECT p FROM Promocionporcentaje p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Promocionporcentaje.findByFechaFin", query = "SELECT p FROM Promocionporcentaje p WHERE p.fechaFin = :fechaFin")})
public class Promocionporcentaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPromocionPorcentaje")
    private Integer idPromocionPorcentaje;
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "porcentaje")
    private Float porcentaje;
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne
    private Producto idProducto;

    public Promocionporcentaje() {
    }

    public Promocionporcentaje(Integer idPromocionPorcentaje) {
        this.idPromocionPorcentaje = idPromocionPorcentaje;
    }

    public Integer getIdPromocionPorcentaje() {
        return idPromocionPorcentaje;
    }

    public void setIdPromocionPorcentaje(Integer idPromocionPorcentaje) {
        this.idPromocionPorcentaje = idPromocionPorcentaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPromocionPorcentaje != null ? idPromocionPorcentaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promocionporcentaje)) {
            return false;
        }
        Promocionporcentaje other = (Promocionporcentaje) object;
        if ((this.idPromocionPorcentaje == null && other.idPromocionPorcentaje != null) || (this.idPromocionPorcentaje != null && !this.idPromocionPorcentaje.equals(other.idPromocionPorcentaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Promocionporcentaje[ idPromocionPorcentaje=" + idPromocionPorcentaje + " ]";
    }
    
}
