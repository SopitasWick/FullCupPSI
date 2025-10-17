/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "promocion")
@NamedQueries({
    @NamedQuery(name = "Promocion.findAll", query = "SELECT p FROM Promocion p"),
    @NamedQuery(name = "Promocion.findByIdPromocion", query = "SELECT p FROM Promocion p WHERE p.idPromocion = :idPromocion"),
    @NamedQuery(name = "Promocion.findByDescripcion", query = "SELECT p FROM Promocion p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Promocion.findByCantidadRequerida", query = "SELECT p FROM Promocion p WHERE p.cantidadRequerida = :cantidadRequerida"),
    @NamedQuery(name = "Promocion.findByFechaCreacion", query = "SELECT p FROM Promocion p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Promocion.findByFechaInicio", query = "SELECT p FROM Promocion p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Promocion.findByFechaFin", query = "SELECT p FROM Promocion p WHERE p.fechaFin = :fechaFin")})
public class Promocion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPromocion")
    private Integer idPromocion;
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidadRequerida")
    private Float cantidadRequerida;
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
    @OneToMany(mappedBy = "idPromocion")
    private List<Promocioncantidad> promocioncantidadList;
    @OneToMany(mappedBy = "idPromocion")
    private List<Promocionporcentaje> promocionporcentajeList;

    public Promocion() {
    }

    public Promocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getCantidadRequerida() {
        return cantidadRequerida;
    }

    public void setCantidadRequerida(Float cantidadRequerida) {
        this.cantidadRequerida = cantidadRequerida;
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

    public List<Promocioncantidad> getPromocioncantidadList() {
        return promocioncantidadList;
    }

    public void setPromocioncantidadList(List<Promocioncantidad> promocioncantidadList) {
        this.promocioncantidadList = promocioncantidadList;
    }

    public List<Promocionporcentaje> getPromocionporcentajeList() {
        return promocionporcentajeList;
    }

    public void setPromocionporcentajeList(List<Promocionporcentaje> promocionporcentajeList) {
        this.promocionporcentajeList = promocionporcentajeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPromocion != null ? idPromocion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promocion)) {
            return false;
        }
        Promocion other = (Promocion) object;
        if ((this.idPromocion == null && other.idPromocion != null) || (this.idPromocion != null && !this.idPromocion.equals(other.idPromocion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Promocion[ idPromocion=" + idPromocion + " ]";
    }
    
}
