/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author usuario
 */

public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idProducto;
    private String nombreProducto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Float precioProducto;
    private Integer cantidadDisponibleproducto;
    private String unidadMedidaproducto;
    private List<Promocion> promocionList;
    private List<Detallecomanda> detallecomandaList;
    private Categoria idCategoria;
    private List<ExtrasProductos> extrasProductosList;

    public List<ExtrasProductos> getExtrasProductosList() {
        return extrasProductosList;
    }

    public void setExtrasProductosList(List<ExtrasProductos> extrasProductosList) {
        this.extrasProductosList = extrasProductosList;
    }

    public Producto() {
    }

    public Producto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Producto(Integer idProducto, String nombre, float precio) {
        this.idProducto = idProducto;
        this.nombreProducto = nombre;
        this.precioProducto = precio;
    }

    public Producto(Integer idProducto, String nombreProducto, Float precioProducto, Integer cantidadDisponibleproducto, String unidadMedidaproducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.cantidadDisponibleproducto = cantidadDisponibleproducto;
        this.unidadMedidaproducto = unidadMedidaproducto;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Float getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(Float precioProducto) {
        this.precioProducto = precioProducto;
    }

    public Integer getCantidadDisponibleproducto() {
        return cantidadDisponibleproducto;
    }

    public void setCantidadDisponibleproducto(Integer cantidadDisponibleproducto) {
        this.cantidadDisponibleproducto = cantidadDisponibleproducto;
    }

    public String getUnidadMedidaproducto() {
        return unidadMedidaproducto;
    }

    public void setUnidadMedidaproducto(String unidadMedidaproducto) {
        this.unidadMedidaproducto = unidadMedidaproducto;
    }

    public List<Promocion> getPromocionList() {
        return promocionList;
    }

    public void setPromocionList(List<Promocion> promocionList) {
        this.promocionList = promocionList;
    }

    public List<Detallecomanda> getDetallecomandaList() {
        return detallecomandaList;
    }

    public void setDetallecomandaList(List<Detallecomanda> detallecomandaList) {
        this.detallecomandaList = detallecomandaList;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Producto[ idProducto=" + idProducto + " ]";
    }

}
