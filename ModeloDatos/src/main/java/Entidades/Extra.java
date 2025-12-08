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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Sergio Arturo
 */
@Entity
@Table(name = "Extra")
public class Extra implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idExtra")
    private Integer idExtra;
    
    @Column(name = "nombreExtra")
    private String nombreExtra;
    
    @Column(name = "tipoExtra")
    private String tipoExtra;
    
    @Column(name = "precio")
    private Float precio;
    
    @Column(name = "estado")
    private String estado;
    
    @ManyToMany(mappedBy = "extras")
    private List<Producto> productos;

    
    
    public Extra() {
    }

    public Extra(Integer idExtra, String nombreExtra, String tipoExtra, Float precio, String estado) {
        this.idExtra = idExtra;
        this.nombreExtra = nombreExtra;
        this.tipoExtra = tipoExtra;
        this.precio = precio;
        this.estado = estado;
    }

    public Extra(String nombreExtra, String tipoExtra, Float precio, String estado) {
        this.nombreExtra = nombreExtra;
        this.tipoExtra = tipoExtra;
        this.precio = precio;
        this.estado = estado;
    }
    

    public Integer getIdExtra() {
        return idExtra;
    }

    public void setIdExtra(Integer idExtra) {
        this.idExtra = idExtra;
    }

    public String getNombreExtra() {
        return nombreExtra;
    }

    public void setNombreExtra(String nombreExtra) {
        this.nombreExtra = nombreExtra;
    }

    public String getTipoExtra() {
        return tipoExtra;
    }

    public void setTipoExtra(String tipoExtra) {
        this.tipoExtra = tipoExtra;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    

    @Override
    public String toString() {
        return "Extra{" + "idExtra=" + idExtra + ", nombreExtra=" + nombreExtra + ", tipoExtra=" + tipoExtra + ", precio=" + precio + ", estado=" + estado + '}';
    }
    

}
