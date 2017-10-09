/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jojstepersan
 */
@Entity
@Table(name = "pais")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c")
    , @NamedQuery(name = "Country.findByCodPais", query = "SELECT c FROM Country c WHERE c.codPais = :codPais")
    , @NamedQuery(name = "Country.findByNomPais", query = "SELECT c FROM Country c WHERE c.nomPais = :nomPais")
    , @NamedQuery(name = "Country.findByUbicacionX", query = "SELECT c FROM Country c WHERE c.ubicacionX = :ubicacionX")
    , @NamedQuery(name = "Country.findByUbicaionY", query = "SELECT c FROM Country c WHERE c.ubicaionY = :ubicaionY")})
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_pais")
    private Integer codPais;
    @Size(max = 100)
    @Column(name = "nom_pais")
    private String nomPais;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ubicacion_x")
    private Float ubicacionX;
    @Column(name = "ubicaion_y")
    private Float ubicaionY;
    @OneToMany(mappedBy = "codPais")
    private List<Port> portList;
    @OneToMany(mappedBy = "destino")
    private List<Load> loadList;
    @OneToMany(mappedBy = "origen")
    private List<Load> loadList1;

    public Country() {
    }

    public Country(Integer codPais) {
        this.codPais = codPais;
    }

    public Integer getCodPais() {
        return codPais;
    }

    public void setCodPais(Integer codPais) {
        this.codPais = codPais;
    }

    public String getNomPais() {
        return nomPais;
    }

    public void setNomPais(String nomPais) {
        this.nomPais = nomPais;
    }

    public Float getUbicacionX() {
        return ubicacionX;
    }

    public void setUbicacionX(Float ubicacionX) {
        this.ubicacionX = ubicacionX;
    }

    public Float getUbicaionY() {
        return ubicaionY;
    }

    public void setUbicaionY(Float ubicaionY) {
        this.ubicaionY = ubicaionY;
    }

    @XmlTransient
    public List<Port> getPortList() {
        return portList;
    }

    public void setPortList(List<Port> portList) {
        this.portList = portList;
    }

    @XmlTransient
    public List<Load> getLoadList() {
        return loadList;
    }

    public void setLoadList(List<Load> loadList) {
        this.loadList = loadList;
    }

    @XmlTransient
    public List<Load> getLoadList1() {
        return loadList1;
    }

    public void setLoadList1(List<Load> loadList1) {
        this.loadList1 = loadList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPais != null ? codPais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        if ((this.codPais == null && other.codPais != null) || (this.codPais != null && !this.codPais.equals(other.codPais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Country[ codPais=" + codPais + " ]";
    }
    
}
