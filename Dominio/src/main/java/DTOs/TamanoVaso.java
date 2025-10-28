/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author Orlando Leyva
 */


public class TamanoVaso implements Serializable {
     
    private static final long serialVersionUID = 1L;
    private Integer idTamanoVaso;
    private String nombre;
    private Float precio;
    
    public TamanoVaso() {
    }
    
    public TamanoVaso(Integer idTamanoVaso) {
        this.idTamanoVaso = idTamanoVaso;
    }
    
    public TamanoVaso(Integer idTamanoVaso, String nombre, Float precio) {
        this.idTamanoVaso = idTamanoVaso;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    public Integer getIdTamanoVaso() {
        return idTamanoVaso;
    }
    
    public void setIdTamanoVaso(Integer idTamanoVaso) {
        this.idTamanoVaso = idTamanoVaso;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Float getPrecio() {
        return precio;
    }
    
    public void setPrecio(Float precio) {
        this.precio = precio;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTamanoVaso != null ? idTamanoVaso.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TamanoVaso)) {
            return false;
        }
        TamanoVaso other = (TamanoVaso) object;
        if ((this.idTamanoVaso == null && other.idTamanoVaso != null) || 
            (this.idTamanoVaso != null && !this.idTamanoVaso.equals(other.idTamanoVaso))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "TamanoVaso[ idTamanoVaso=" + idTamanoVaso + " ]";
    }
}
