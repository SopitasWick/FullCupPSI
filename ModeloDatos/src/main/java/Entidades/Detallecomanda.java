/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "detallecomanda")
@NamedQueries({
    @NamedQuery(name = "Detallecomanda.findAll", query = "SELECT d FROM Detallecomanda d"),
    @NamedQuery(name = "Detallecomanda.findByIdDetalleComanda", query = "SELECT d FROM Detallecomanda d WHERE d.idDetalleComanda = :idDetalleComanda"),
    @NamedQuery(name = "Detallecomanda.findByDetalleComandaByComanda", query = "SELECT d FROM Detallecomanda d WHERE d.idComanda = :idComanda"),
    @NamedQuery(name = "Detallecomanda.findByCaintidaddetalleComanda", query = "SELECT d FROM Detallecomanda d WHERE d.caintidaddetalleComanda = :caintidaddetalleComanda"),
    @NamedQuery(name = "Detallecomanda.findByNotadetalleComanda", query = "SELECT d FROM Detallecomanda d WHERE d.notadetalleComanda = :notadetalleComanda"),
    @NamedQuery(name = "Detallecomanda.findBySubTotaldetalleComanda", query = "SELECT d FROM Detallecomanda d WHERE d.subTotaldetalleComanda = :subTotaldetalleComanda")})
public class Detallecomanda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idDetalleComanda")
    private Integer idDetalleComanda;
    @Column(name = "caintidad_detalleComanda")
    private Integer caintidaddetalleComanda;
    @Column(name = "nota_detalleComanda")
    private String notadetalleComanda;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "subTotal_detalleComanda")
    private Float subTotaldetalleComanda;
    @JoinColumn(name = "idComanda", referencedColumnName = "idComanda")
    @ManyToOne
    private Comanda idComanda;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne
    private Producto idProducto;

    public Detallecomanda() {
    }

    public Detallecomanda(Integer idDetalleComanda) {
        this.idDetalleComanda = idDetalleComanda;
    }

    public Detallecomanda(Integer idDetalleComanda, Integer caintidaddetalleComanda, Float subTotaldetalleComanda, Comanda idComanda, Producto idProducto) {
        this.idDetalleComanda = idDetalleComanda;
        this.caintidaddetalleComanda = caintidaddetalleComanda;
        this.subTotaldetalleComanda = subTotaldetalleComanda;
        this.idComanda = idComanda;
        this.idProducto = idProducto;
    }
    

    public Integer getIdDetalleComanda() {
        return idDetalleComanda;
    }

    public void setIdDetalleComanda(Integer idDetalleComanda) {
        this.idDetalleComanda = idDetalleComanda;
    }

    public Integer getCaintidaddetalleComanda() {
        return caintidaddetalleComanda;
    }

    public void setCaintidaddetalleComanda(Integer caintidaddetalleComanda) {
        this.caintidaddetalleComanda = caintidaddetalleComanda;
    }

    public String getNotadetalleComanda() {
        return notadetalleComanda;
    }

    public void setNotadetalleComanda(String notadetalleComanda) {
        this.notadetalleComanda = notadetalleComanda;
    }

    public Float getSubTotaldetalleComanda() {
        return subTotaldetalleComanda;
    }

    public void setSubTotaldetalleComanda(Float subTotaldetalleComanda) {
        this.subTotaldetalleComanda = subTotaldetalleComanda;
    }

    public Comanda getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(Comanda idComanda) {
        this.idComanda = idComanda;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleComanda != null ? idDetalleComanda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detallecomanda)) {
            return false;
        }
        Detallecomanda other = (Detallecomanda) object;
        if ((this.idDetalleComanda == null && other.idDetalleComanda != null) || (this.idDetalleComanda != null && !this.idDetalleComanda.equals(other.idDetalleComanda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Detallecomanda[ idDetalleComanda=" + idDetalleComanda + " ]";
    }
    
}
