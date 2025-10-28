/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.io.Serializable;

/**
 *
 * @author usuario
 */

public class Cajaefectivo implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer idCorte;
    private Character tipoCorte;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Float montoInicial;
    private Float montoFinal;
    private Float totalVentaEfectivo;
    private Float totalVentaTarjeta;
    private Short estado;
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
