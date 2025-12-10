/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "cajaefectivo")
@NamedQueries({
    @NamedQuery(name = "Cajaefectivo.findAll", query = "SELECT c FROM Cajaefectivo c"),
    @NamedQuery(name = "Cajaefectivo.findByIdCorte", query = "SELECT c FROM Cajaefectivo c WHERE c.idCorte = :idCorte"),
    @NamedQuery(name = "Cajaefectivo.findByTipoCorte", query = "SELECT c FROM Cajaefectivo c WHERE c.tipoCorte = :tipoCorte"),
    @NamedQuery(name = "Cajaefectivo.findByMontoInicial", query = "SELECT c FROM Cajaefectivo c WHERE c.montoInicial = :montoInicial"),
    @NamedQuery(name = "Cajaefectivo.findByMontoFinal", query = "SELECT c FROM Cajaefectivo c WHERE c.montoFinal = :montoFinal"),
    @NamedQuery(name = "Cajaefectivo.findByTotalVentaEfectivo", query = "SELECT c FROM Cajaefectivo c WHERE c.totalVentaEfectivo = :totalVentaEfectivo"),
    @NamedQuery(name = "Cajaefectivo.findByTotalVentaTarjeta", query = "SELECT c FROM Cajaefectivo c WHERE c.totalVentaTarjeta = :totalVentaTarjeta"),
    @NamedQuery(name = "Cajaefectivo.findByEstado", query = "SELECT c FROM Cajaefectivo c WHERE c.estado = :estado")})
public class Cajaefectivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCorte")
    private Integer idCorte;
    @Column(name = "tipoCorte")
    private Character tipoCorte;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montoInicial")
    private Float montoInicial;
    @Column(name = "montoFinal")
    private Float montoFinal;
    @Column(name = "totalVentaEfectivo")
    private Float totalVentaEfectivo;
    @Column(name = "totalVentaTarjeta")
    private Float totalVentaTarjeta;
    @Column(name = "estado")
    private Short estado;

    // ⭐ FECHA Y HORA DE CORTE
    @Column(name = "fechaHoraCorte")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCorte;

    // ⭐ Diferencia al final
    @Column(name = "diferencia")
    private Float diferencia;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario idUsuario;

    public Cajaefectivo() {
    }

    public Cajaefectivo(Integer idCorte) {
        this.idCorte = idCorte;
    }

    public Integer getIdCorte() {
        return idCorte;
    }

    public void setIdCorte(Integer idCorte) {
        this.idCorte = idCorte;
    }

    public Character getTipoCorte() {
        return tipoCorte;
    }

    public void setTipoCorte(Character tipoCorte) {
        this.tipoCorte = tipoCorte;
    }

    public Float getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(Float montoInicial) {
        this.montoInicial = montoInicial;
    }

    public Float getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(Float montoFinal) {
        this.montoFinal = montoFinal;
    }

    public Float getTotalVentaEfectivo() {
        return totalVentaEfectivo;
    }

    public void setTotalVentaEfectivo(Float totalVentaEfectivo) {
        this.totalVentaEfectivo = totalVentaEfectivo;
    }

    public Date getFechaHoraCorte() {
        return fechaHoraCorte;
    }

    public void setFechaHoraCorte(Date fechaHoraCorte) {
        this.fechaHoraCorte = fechaHoraCorte;
    }

    public Float getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(Float diferencia) {
        this.diferencia = diferencia;
    }

    public Float getTotalVentaTarjeta() {
        return totalVentaTarjeta;
    }

    public void setTotalVentaTarjeta(Float totalVentaTarjeta) {
        this.totalVentaTarjeta = totalVentaTarjeta;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
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
        hash += (idCorte != null ? idCorte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cajaefectivo)) {
            return false;
        }
        Cajaefectivo other = (Cajaefectivo) object;
        if ((this.idCorte == null && other.idCorte != null) || (this.idCorte != null && !this.idCorte.equals(other.idCorte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Cajaefectivo[ idCorte=" + idCorte + " ]";
    }

}
