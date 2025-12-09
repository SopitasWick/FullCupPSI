/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.Objects;

/**
 *
 * @author Sergio Arturo
 */
public class ExtraCantidadDTO {
    
    private String nombre;
    private float precioUnitario;
    private int cantidad;

    public ExtraCantidadDTO(String nombre, float precioUnitario, int cantidad) {
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.nombre);
        hash = 71 * hash + Float.floatToIntBits(this.precioUnitario);
        hash = 71 * hash + this.cantidad;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExtraCantidadDTO other = (ExtraCantidadDTO) obj;
        if (Float.floatToIntBits(this.precioUnitario) != Float.floatToIntBits(other.precioUnitario)) {
            return false;
        }
        if (this.cantidad != other.cantidad) {
            return false;
        }
        return Objects.equals(this.nombre, other.nombre);
    }
    
    
    
    
    
    
}
