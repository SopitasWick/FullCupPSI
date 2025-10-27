/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.io.Serializable;

/**
 *
 * @author usuario
 */
public class Promocioncantidad implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idPromocionCantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Float cantidadGratis;
    private Promocion idPromocion;

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

    public Float getCantidadGratis() {
        return cantidadGratis;
    }

    public void setCantidadGratis(Float cantidadGratis) {
        this.cantidadGratis = cantidadGratis;
    }

    public Promocion getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Promocion idPromocion) {
        this.idPromocion = idPromocion;
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
