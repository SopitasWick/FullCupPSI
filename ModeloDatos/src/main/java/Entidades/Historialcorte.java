/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author ruben
 */
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "historial_cortes")
public class Historialcorte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_caja")
    private Integer idCaja;

    @Column(name = "tipo_corte")
    private char tipoCorte; // 'X' o 'Z'

    @Column(name = "monto_inicial")
    private float montoInicial;

    @Column(name = "monto_final")
    private float montoFinal;

    @Column(name = "diferencia")
    private float diferencia;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_hora")
    private Date fechaHora;

    public Historialcorte() {
    }

    public Historialcorte(Integer idCaja, char tipoCorte, float montoInicial,
            float montoFinal, float diferencia, Date fechaHora) {
        this.idCaja = idCaja;
        this.tipoCorte = tipoCorte;
        this.montoInicial = montoInicial;
        this.montoFinal = montoFinal;
        this.diferencia = diferencia;
        this.fechaHora = fechaHora;
    }

    // ----- GETTERS & SETTERS -----
    public Integer getId() {
        return id;
    }

    public Integer getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }

    public char getTipoCorte() {
        return tipoCorte;
    }

    public void setTipoCorte(char tipoCorte) {
        this.tipoCorte = tipoCorte;
    }

    public float getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(float montoInicial) {
        this.montoInicial = montoInicial;
    }

    public float getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(float montoFinal) {
        this.montoFinal = montoFinal;
    }

    public float getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(float diferencia) {
        this.diferencia = diferencia;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
}
