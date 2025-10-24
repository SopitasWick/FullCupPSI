/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Orlando Leyva
 */


@Entity
@Table(name = "leche")
public class Leche implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "idLeche")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLeche;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "precio")
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
