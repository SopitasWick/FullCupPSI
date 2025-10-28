/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.io.Serializable;

/**
 *
 * @author Orlando Leyva
 */

public class ExtrasProductos implements Serializable {
   

   private static final long serialVersionUID = 1L;
    
    private Integer idExtraProducto;
    private String nombreExtra; 
    private Float precioExtra;
    private int cantidad;
    private Producto idProducto;
    private Categoria idCategoria;
    private TamanoVaso idTamanoVaso;
    private Leche idLeche;
    
    public ExtrasProductos() {
    }
    
    public ExtrasProductos(Integer idExtraProducto) {
        this.idExtraProducto = idExtraProducto;
    }
    
    public ExtrasProductos(Integer idExtraProducto, String nombreExtra, Float precioExtra, int cantidad) {
        this.idExtraProducto = idExtraProducto;
        this.nombreExtra = nombreExtra;
        this.precioExtra = precioExtra;
        this.cantidad = cantidad;
    }
    
    public Integer getIdExtraProducto() {
        return idExtraProducto;
    }
    
    public void setIdExtraProducto(Integer idExtraProducto) {
        this.idExtraProducto = idExtraProducto;
    }
    
    public String getNombreExtra() {
        return nombreExtra;
    }
    
    public void setNombreExtra(String nombreExtra) {
        this.nombreExtra = nombreExtra;
    }
    
    public Float getPrecioExtra() {
        return precioExtra;
    }
    
    public void setPrecioExtra(Float precioExtra) {
        this.precioExtra = precioExtra;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public Producto getIdProducto() {
        return idProducto;
    }
    
    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }
    
    public Categoria getIdCategoria() {
        return idCategoria;
    }
    
    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    public TamanoVaso getIdTamanoVaso() {
        return idTamanoVaso;
    }
    
    public void setIdTamanoVaso(TamanoVaso idTamanoVaso) {
        this.idTamanoVaso = idTamanoVaso;
    }
    
    public Leche getIdLeche() {
        return idLeche;
    }
    
    public void setIdLeche(Leche idLeche) {
        this.idLeche = idLeche;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExtraProducto != null ? idExtraProducto.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ExtrasProductos)) {
            return false;
        }
        ExtrasProductos other = (ExtrasProductos) object;
        if ((this.idExtraProducto == null && other.idExtraProducto != null) || 
            (this.idExtraProducto != null && !this.idExtraProducto.equals(other.idExtraProducto))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "ExtrasProductos[ idExtraProducto=" + idExtraProducto + " ]";
    }
}