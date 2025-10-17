/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "promocionporcentaje")
@NamedQueries({
    @NamedQuery(name = "Promocionporcentaje.findAll", query = "SELECT p FROM Promocionporcentaje p"),
    @NamedQuery(name = "Promocionporcentaje.findByIdPromocionPorcentaje", query = "SELECT p FROM Promocionporcentaje p WHERE p.idPromocionPorcentaje = :idPromocionPorcentaje"),
    @NamedQuery(name = "Promocionporcentaje.findByPorcentajeGratis", query = "SELECT p FROM Promocionporcentaje p WHERE p.porcentajeGratis = :porcentajeGratis")})
public class Promocionporcentaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPromocionPorcentaje")
    private Integer idPromocionPorcentaje;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "porcentajeGratis")
    private Float porcentajeGratis;
    @JoinColumn(name = "idPromocion", referencedColumnName = "idPromocion")
    @ManyToOne
    private Promocion idPromocion;

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

    public Float getPorcentajeGratis() {
        return porcentajeGratis;
    }

    public void setPorcentajeGratis(Float porcentajeGratis) {
        this.porcentajeGratis = porcentajeGratis;
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
