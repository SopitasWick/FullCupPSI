/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Orlando Leyva
 */
@Entity 
@Table(name="extraProductos")
public class ExtrasProductos implements Serializable {
   

   private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idExtraProductos")
    private Integer idExtraProducto;
    
    @Column(name = "nombreExtra")
    private String nombreExtra;
    
    @Column(name = "precioExtra")
    private Float precioExtra;
    
    @Column(name = "cantidad")
    private int cantidad;
    
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne
    private Producto idProducto;
    
    @JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria")
    @ManyToOne
    private Categoria idCategoria;
    
    @JoinColumn(name = "tamañoVaso", referencedColumnName = "idTamanoVaso")
    @ManyToOne
    private TamanoVaso idTamanoVaso;
    
    @JoinColumn(name = "idLeche", referencedColumnName = "idLeche")
    @ManyToOne
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