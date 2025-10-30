/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "valoropcion")
@NamedQueries({
    @NamedQuery(name = "Valoropcion.findAll", query = "SELECT v FROM Valoropcion v"),
    @NamedQuery(name = "Valoropcion.findByIdValorOpcion", query = "SELECT v FROM Valoropcion v WHERE v.idValorOpcion = :idValorOpcion"),
    @NamedQuery(name = "Valoropcion.findByNombreValor", query = "SELECT v FROM Valoropcion v WHERE v.nombreValor = :nombreValor"),
    @NamedQuery(name = "Valoropcion.findByCosteAdicional", query = "SELECT v FROM Valoropcion v WHERE v.costeAdicional = :costeAdicional")})
public class Valoropcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idValorOpcion")
    private Integer idValorOpcion;
    @Column(name = "nombreValor")
    private String nombreValor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costeAdicional")
    private Float costeAdicional;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idValorOpcion")
    private List<Detalleopcion> detalleopcionList;
    @JoinColumn(name = "idOpcionProducto", referencedColumnName = "idOpcionProducto")
    @ManyToOne(optional = false)
    private Opcionproducto idOpcionProducto;

    public Valoropcion() {
    }

    public Valoropcion(Integer idValorOpcion) {
        this.idValorOpcion = idValorOpcion;
    }

    public Integer getIdValorOpcion() {
        return idValorOpcion;
    }

    public void setIdValorOpcion(Integer idValorOpcion) {
        this.idValorOpcion = idValorOpcion;
    }

    public String getNombreValor() {
        return nombreValor;
    }

    public void setNombreValor(String nombreValor) {
        this.nombreValor = nombreValor;
    }

    public Float getCosteAdicional() {
        return costeAdicional;
    }

    public void setCosteAdicional(Float costeAdicional) {
        this.costeAdicional = costeAdicional;
    }

    public List<Detalleopcion> getDetalleopcionList() {
        return detalleopcionList;
    }

    public void setDetalleopcionList(List<Detalleopcion> detalleopcionList) {
        this.detalleopcionList = detalleopcionList;
    }

    public Opcionproducto getIdOpcionProducto() {
        return idOpcionProducto;
    }

    public void setIdOpcionProducto(Opcionproducto idOpcionProducto) {
        this.idOpcionProducto = idOpcionProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idValorOpcion != null ? idValorOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Valoropcion)) {
            return false;
        }
        Valoropcion other = (Valoropcion) object;
        if ((this.idValorOpcion == null && other.idValorOpcion != null) || (this.idValorOpcion != null && !this.idValorOpcion.equals(other.idValorOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Valoropcion[ idValorOpcion=" + idValorOpcion + " ]";
    }
    
}
