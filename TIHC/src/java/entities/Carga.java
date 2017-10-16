/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jojstepersan
 */
@Entity
@Table(name = "carga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carga.findAll", query = "SELECT c FROM Carga c")
    , @NamedQuery(name = "Carga.findByCodCarga", query = "SELECT c FROM Carga c WHERE c.codCarga = :codCarga")
    , @NamedQuery(name = "Carga.findByPeso", query = "SELECT c FROM Carga c WHERE c.peso = :peso")
    , @NamedQuery(name = "Carga.findByDescripcion", query = "SELECT c FROM Carga c WHERE c.descripcion = :descripcion")})
public class Carga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_carga")
    private Integer codCarga;
    @Column(name = "peso")
    private Integer peso;
    @Size(max = 400)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "cod_barco", referencedColumnName = "cod_barco")
    @ManyToOne
    private Barco codBarco;
    @JoinColumn(name = "cod_contrato", referencedColumnName = "cod_contrato")
    @ManyToOne
    private Contrato codContrato;
    @JoinColumn(name = "destino", referencedColumnName = "cod_pais")
    @ManyToOne
    private Pais destino;
    @JoinColumn(name = "origen", referencedColumnName = "cod_pais")
    @ManyToOne
    private Pais origen;
    @JoinColumn(name = "cod_tipo_carga", referencedColumnName = "cod_tipo_carga")
    @ManyToOne
    private TipoCarga codTipoCarga;

    public Carga() {
    }

    public Carga(Integer codCarga) {
        this.codCarga = codCarga;
    }

    public Integer getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Integer codCarga) {
        this.codCarga = codCarga;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Barco getCodBarco() {
        return codBarco;
    }

    public void setCodBarco(Barco codBarco) {
        this.codBarco = codBarco;
    }

    public Contrato getCodContrato() {
        return codContrato;
    }

    public void setCodContrato(Contrato codContrato) {
        this.codContrato = codContrato;
    }

    public Pais getDestino() {
        return destino;
    }

    public void setDestino(Pais destino) {
        this.destino = destino;
    }

    public Pais getOrigen() {
        return origen;
    }

    public void setOrigen(Pais origen) {
        this.origen = origen;
    }

    public TipoCarga getCodTipoCarga() {
        return codTipoCarga;
    }

    public void setCodTipoCarga(TipoCarga codTipoCarga) {
        this.codTipoCarga = codTipoCarga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCarga != null ? codCarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carga)) {
            return false;
        }
        Carga other = (Carga) object;
        if ((this.codCarga == null && other.codCarga != null) || (this.codCarga != null && !this.codCarga.equals(other.codCarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Carga[ codCarga=" + codCarga + " ]";
    }
    
}
