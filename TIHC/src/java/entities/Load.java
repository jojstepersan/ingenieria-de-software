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
    @NamedQuery(name = "Load.findAll", query = "SELECT l FROM Load l")
    , @NamedQuery(name = "Load.findByCodCarga", query = "SELECT l FROM Load l WHERE l.codCarga = :codCarga")
    , @NamedQuery(name = "Load.findByPeso", query = "SELECT l FROM Load l WHERE l.peso = :peso")
    , @NamedQuery(name = "Load.findByDescripcion", query = "SELECT l FROM Load l WHERE l.descripcion = :descripcion")})
public class Load implements Serializable {

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
    private Ship codBarco;
    @JoinColumn(name = "cod_contrato", referencedColumnName = "cod_contrato")
    @ManyToOne
    private Contract codContrato;
    @JoinColumn(name = "destino", referencedColumnName = "cod_pais")
    @ManyToOne
    private Country destino;
    @JoinColumn(name = "origen", referencedColumnName = "cod_pais")
    @ManyToOne
    private Country origen;
    @JoinColumn(name = "cod_tipo_carga", referencedColumnName = "cod_tipo_carga")
    @ManyToOne
    private TypeLoad codTipoCarga;

    public Load() {
    }

    public Load(Integer codCarga) {
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

    public Ship getCodBarco() {
        return codBarco;
    }

    public void setCodBarco(Ship codBarco) {
        this.codBarco = codBarco;
    }

    public Contract getCodContrato() {
        return codContrato;
    }

    public void setCodContrato(Contract codContrato) {
        this.codContrato = codContrato;
    }

    public Country getDestino() {
        return destino;
    }

    public void setDestino(Country destino) {
        this.destino = destino;
    }

    public Country getOrigen() {
        return origen;
    }

    public void setOrigen(Country origen) {
        this.origen = origen;
    }

    public TypeLoad getCodTipoCarga() {
        return codTipoCarga;
    }

    public void setCodTipoCarga(TypeLoad codTipoCarga) {
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
        if (!(object instanceof Load)) {
            return false;
        }
        Load other = (Load) object;
        if ((this.codCarga == null && other.codCarga != null) || (this.codCarga != null && !this.codCarga.equals(other.codCarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Load[ codCarga=" + codCarga + " ]";
    }
    
}
