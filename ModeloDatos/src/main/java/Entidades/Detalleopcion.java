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
@Table(name = "detalleopcion")
@NamedQueries({
    @NamedQuery(name = "Detalleopcion.findAll", query = "SELECT d FROM Detalleopcion d"),
    @NamedQuery(name = "Detalleopcion.findByIdDetalleOpcion", query = "SELECT d FROM Detalleopcion d WHERE d.idDetalleOpcion = :idDetalleOpcion")})
public class Detalleopcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idDetalleOpcion")
    private Integer idDetalleOpcion;
    @JoinColumn(name = "idDetalleComanda", referencedColumnName = "idDetalleComanda")
    @ManyToOne(optional = false)
    private Detallecomanda idDetalleComanda;
    @JoinColumn(name = "idValorOpcion", referencedColumnName = "idValorOpcion")
    @ManyToOne(optional = false)
    private Valoropcion idValorOpcion;

    public Detalleopcion() {
    }

    public Detalleopcion(Integer idDetalleOpcion) {
        this.idDetalleOpcion = idDetalleOpcion;
    }

    public Integer getIdDetalleOpcion() {
        return idDetalleOpcion;
    }

    public void setIdDetalleOpcion(Integer idDetalleOpcion) {
        this.idDetalleOpcion = idDetalleOpcion;
    }

    public Detallecomanda getIdDetalleComanda() {
        return idDetalleComanda;
    }

    public void setIdDetalleComanda(Detallecomanda idDetalleComanda) {
        this.idDetalleComanda = idDetalleComanda;
    }

    public Valoropcion getIdValorOpcion() {
        return idValorOpcion;
    }

    public void setIdValorOpcion(Valoropcion idValorOpcion) {
        this.idValorOpcion = idValorOpcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleOpcion != null ? idDetalleOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detalleopcion)) {
            return false;
        }
        Detalleopcion other = (Detalleopcion) object;
        if ((this.idDetalleOpcion == null && other.idDetalleOpcion != null) || (this.idDetalleOpcion != null && !this.idDetalleOpcion.equals(other.idDetalleOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Detalleopcion[ idDetalleOpcion=" + idDetalleOpcion + " ]";
    }
    
}
