/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "comanda")
@NamedQueries({
    @NamedQuery(name = "Comanda.findAll", query = "SELECT c FROM Comanda c"),
    @NamedQuery(name = "Comanda.findByIdComanda", query = "SELECT c FROM Comanda c WHERE c.idComanda = :idComanda"),
    @NamedQuery(name = "Comanda.findByFechaHoracomanda", query = "SELECT c FROM Comanda c WHERE c.fechaHoracomanda = :fechaHoracomanda"),
    @NamedQuery(name = "Comanda.findByEstadoComanda", query = "SELECT c FROM Comanda c WHERE c.estadoComanda = :estadoComanda"),
    @NamedQuery(name = "Comanda.findByTotalComanda", query = "SELECT c FROM Comanda c WHERE c.totalComanda = :totalComanda")})
public class Comanda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idComanda")
    private Integer idComanda;
    @Column(name = "fechaHora_comanda")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoracomanda;
    @Column(name = "estado_comanda")
    private String estadoComanda;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_comanda")
    private Float totalComanda;
    @OneToMany(mappedBy = "idComanda")
    private List<Venta> ventaList;
    @OneToMany(mappedBy = "idComanda")
    private List<Detallecomanda> detallecomandaList;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario idUsuario;

    public Comanda() {
    }

    public Comanda(Integer idComanda) {
        this.idComanda = idComanda;
    }

    public Comanda(Integer idComanda, Date fechaHoracomanda, String estadoComanda, Float totalComanda) {
        this.idComanda = idComanda;
        this.fechaHoracomanda = fechaHoracomanda;
        this.estadoComanda = estadoComanda;
        this.totalComanda = totalComanda;
    }
  
    public Integer getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(Integer idComanda) {
        this.idComanda = idComanda;
    }

    public Date getFechaHoracomanda() {
        return fechaHoracomanda;
    }

    public void setFechaHoracomanda(Date fechaHoracomanda) {
        this.fechaHoracomanda = fechaHoracomanda;
    }

    public String getEstadoComanda() {
        return estadoComanda;
    }

    public void setEstadoComanda(String estadoComanda) {
        this.estadoComanda = estadoComanda;
    }

    public Float getTotalComanda() {
        return totalComanda;
    }

    public void setTotalComanda(Float totalComanda) {
        this.totalComanda = totalComanda;
    }

    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    public List<Detallecomanda> getDetallecomandaList() {
        return detallecomandaList;
    }

    public void setDetallecomandaList(List<Detallecomanda> detallecomandaList) {
        this.detallecomandaList = detallecomandaList;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComanda != null ? idComanda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comanda)) {
            return false;
        }
        Comanda other = (Comanda) object;
        if ((this.idComanda == null && other.idComanda != null) || (this.idComanda != null && !this.idComanda.equals(other.idComanda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Comanda[ idComanda=" + idComanda + " ]";
    }
    
}
