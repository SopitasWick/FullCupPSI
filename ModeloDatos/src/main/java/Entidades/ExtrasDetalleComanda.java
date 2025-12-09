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
 * @author Sergio Arturo
 */
@Entity
@Table(name = "extrasDetalleComanda")
public class ExtrasDetalleComanda implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idExtrasDetalleComanda")
    private Integer idExtrasDetalleComanda;
    
    @ManyToOne 
    @JoinColumn(name = "idDetalleComanda", nullable = false)
    private Detallecomanda detalleComanda; // Renombrado a 'detalleComanda' por convención
    
    @ManyToOne
    @JoinColumn(name = "idExtra", nullable = false)
    private Extra extra; // Renombrado a 'extra' por convención

    
    
    public ExtrasDetalleComanda() {
    }

    public ExtrasDetalleComanda(Integer idExtrasDetalleComanda, Detallecomanda detalleComanda, Extra extra) {
        this.idExtrasDetalleComanda = idExtrasDetalleComanda;
        this.detalleComanda = detalleComanda;
        this.extra = extra;
    }

    public ExtrasDetalleComanda(Detallecomanda detalleComanda, Extra extra) {
        this.detalleComanda = detalleComanda;
        this.extra = extra;
    }

    public Integer getIdExtrasDetalleComanda() {
        return idExtrasDetalleComanda;
    }

    public void setIdExtrasDetalleComanda(Integer idExtrasDetalleComanda) {
        this.idExtrasDetalleComanda = idExtrasDetalleComanda;
    }

    public Detallecomanda getDetalleComanda() {
        return detalleComanda;
    }

    public void setDetalleComanda(Detallecomanda detalleComanda) {
        this.detalleComanda = detalleComanda;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "ExtrasDetalleComanda{" + "idExtrasDetalleComanda=" + idExtrasDetalleComanda + ", detalleComanda=" + detalleComanda + ", extra=" + extra + '}';
    }


      
    
}
