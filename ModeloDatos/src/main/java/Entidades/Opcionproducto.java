/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "opcionproducto")
@NamedQueries({
    @NamedQuery(name = "Opcionproducto.findAll", query = "SELECT o FROM Opcionproducto o"),
    @NamedQuery(name = "Opcionproducto.findByIdOpcionProducto", query = "SELECT o FROM Opcionproducto o WHERE o.idOpcionProducto = :idOpcionProducto"),
    @NamedQuery(name = "Opcionproducto.findByNombreOpcion", query = "SELECT o FROM Opcionproducto o WHERE o.nombreOpcion = :nombreOpcion"),
    @NamedQuery(name = "Opcionproducto.findByEsMultiple", query = "SELECT o FROM Opcionproducto o WHERE o.esMultiple = :esMultiple")})
public class Opcionproducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idOpcionProducto")
    private Integer idOpcionProducto;
    @Basic(optional = false)
    @Column(name = "nombreOpcion")
    private String nombreOpcion;
    @Basic(optional = false)
    @Column(name = "esMultiple")
    private boolean esMultiple;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOpcionProducto")
    private List<Productoopcion> productoopcionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOpcionProducto")
    private List<Valoropcion> valoropcionList;

    public Opcionproducto() {
    }

    public Opcionproducto(Integer idOpcionProducto) {
        this.idOpcionProducto = idOpcionProducto;
    }

    public Opcionproducto(Integer idOpcionProducto, String nombreOpcion, boolean esMultiple) {
        this.idOpcionProducto = idOpcionProducto;
        this.nombreOpcion = nombreOpcion;
        this.esMultiple = esMultiple;
    }

    public Integer getIdOpcionProducto() {
        return idOpcionProducto;
    }

    public void setIdOpcionProducto(Integer idOpcionProducto) {
        this.idOpcionProducto = idOpcionProducto;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }

    public boolean getEsMultiple() {
        return esMultiple;
    }

    public void setEsMultiple(boolean esMultiple) {
        this.esMultiple = esMultiple;
    }

    public List<Productoopcion> getProductoopcionList() {
        return productoopcionList;
    }

    public void setProductoopcionList(List<Productoopcion> productoopcionList) {
        this.productoopcionList = productoopcionList;
    }

    public List<Valoropcion> getValoropcionList() {
        return valoropcionList;
    }

    public void setValoropcionList(List<Valoropcion> valoropcionList) {
        this.valoropcionList = valoropcionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOpcionProducto != null ? idOpcionProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Opcionproducto)) {
            return false;
        }
        Opcionproducto other = (Opcionproducto) object;
        if ((this.idOpcionProducto == null && other.idOpcionProducto != null) || (this.idOpcionProducto != null && !this.idOpcionProducto.equals(other.idOpcionProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Opcionproducto[ idOpcionProducto=" + idOpcionProducto + " ]";
    }
    
}
