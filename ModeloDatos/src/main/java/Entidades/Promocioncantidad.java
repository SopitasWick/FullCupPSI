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
@Table(name = "promocioncantidad")
@NamedQueries({
    @NamedQuery(name = "Promocioncantidad.findAll", query = "SELECT p FROM Promocioncantidad p"),
    @NamedQuery(name = "Promocioncantidad.findByIdPromocionCantidad", query = "SELECT p FROM Promocioncantidad p WHERE p.idPromocionCantidad = :idPromocionCantidad"),
    @NamedQuery(name = "Promocioncantidad.findByDescripcion", query = "SELECT p FROM Promocioncantidad p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Promocioncantidad.findByCantidadRequerida", query = "SELECT p FROM Promocioncantidad p WHERE p.cantidadRequerida = :cantidadRequerida"),
    @NamedQuery(name = "Promocioncantidad.findByCantidadGratis", query = "SELECT p FROM Promocioncantidad p WHERE p.cantidadGratis = :cantidadGratis"),
    @NamedQuery(name = "Promocioncantidad.findByFechaCreacion", query = "SELECT p FROM Promocioncantidad p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Promocioncantidad.findByFechaInicio", query = "SELECT p FROM Promocioncantidad p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Promocioncantidad.findByFechaFin", query = "SELECT p FROM Promocioncantidad p WHERE p.fechaFin = :fechaFin")})
public class Promocioncantidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPromocionCantidad")
    private Integer idPromocionCantidad;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "cantidadRequerida")
    private Integer cantidadRequerida;
    @Column(name = "cantidadGratis")
    private Integer cantidadGratis;
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

    public Promocioncantidad() {
    }

    public Promocioncantidad(Integer idPromocionCantidad) {
        this.idPromocionCantidad = idPromocionCantidad;
    }

    public Integer getIdPromocionCantidad() {
        return idPromocionCantidad;
    }

    public void setIdPromocionCantidad(Integer idPromocionCantidad) {
        this.idPromocionCantidad = idPromocionCantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidadRequerida() {
        return cantidadRequerida;
    }

    public void setCantidadRequerida(Integer cantidadRequerida) {
        this.cantidadRequerida = cantidadRequerida;
    }

    public Integer getCantidadGratis() {
        return cantidadGratis;
    }

    public void setCantidadGratis(Integer cantidadGratis) {
        this.cantidadGratis = cantidadGratis;
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
        hash += (idPromocionCantidad != null ? idPromocionCantidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promocioncantidad)) {
            return false;
        }
        Promocioncantidad other = (Promocioncantidad) object;
        if ((this.idPromocionCantidad == null && other.idPromocionCantidad != null) || (this.idPromocionCantidad != null && !this.idPromocionCantidad.equals(other.idPromocionCantidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Promocioncantidad[ idPromocionCantidad=" + idPromocionCantidad + " ]";
    }
    
}
