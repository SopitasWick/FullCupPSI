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


public class Leche implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer idLeche;
    private String nombre;
    private Float precio;
    
    public Leche() {
    }
    
    public Leche(Integer idLeche) {
        this.idLeche = idLeche;
    }
    
    public Leche(Integer idLeche, String nombre, Float precio) {
        this.idLeche = idLeche;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    public Integer getIdLeche() {
        return idLeche;
    }
    
    public void setIdLeche(Integer idLeche) {
        this.idLeche = idLeche;
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
        hash += (idLeche != null ? idLeche.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Leche)) {
            return false;
        }
        Leche other = (Leche) object;
        if ((this.idLeche == null && other.idLeche != null) || 
            (this.idLeche != null && !this.idLeche.equals(other.idLeche))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Leche[ idLeche=" + idLeche + " ]";
    }

}
