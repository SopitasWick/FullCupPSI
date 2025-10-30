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
@Table(name = "productoopcion")
@NamedQueries({
    @NamedQuery(name = "Productoopcion.findAll", query = "SELECT p FROM Productoopcion p"),
    @NamedQuery(name = "Productoopcion.findByIdProductoOpcion", query = "SELECT p FROM Productoopcion p WHERE p.idProductoOpcion = :idProductoOpcion")})
public class Productoopcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idProductoOpcion")
    private Integer idProductoOpcion;
    @JoinColumn(name = "idOpcionProducto", referencedColumnName = "idOpcionProducto")
    @ManyToOne(optional = false)
    private Opcionproducto idOpcionProducto;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne(optional = false)
    private Producto idProducto;

    public Productoopcion() {
    }

    public Productoopcion(Integer idProductoOpcion) {
        this.idProductoOpcion = idProductoOpcion;
    }

    public Integer getIdProductoOpcion() {
        return idProductoOpcion;
    }

    public void setIdProductoOpcion(Integer idProductoOpcion) {
        this.idProductoOpcion = idProductoOpcion;
    }

    public Opcionproducto getIdOpcionProducto() {
        return idOpcionProducto;
    }

    public void setIdOpcionProducto(Opcionproducto idOpcionProducto) {
        this.idOpcionProducto = idOpcionProducto;
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
        hash += (idProductoOpcion != null ? idProductoOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productoopcion)) {
            return false;
        }
        Productoopcion other = (Productoopcion) object;
        if ((this.idProductoOpcion == null && other.idProductoOpcion != null) || (this.idProductoOpcion != null && !this.idProductoOpcion.equals(other.idProductoOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Productoopcion[ idProductoOpcion=" + idProductoOpcion + " ]";
    }
    
}
