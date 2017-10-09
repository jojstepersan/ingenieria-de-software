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
@Table(name = "puerto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Port.findAll", query = "SELECT p FROM Port p")
    , @NamedQuery(name = "Port.findByCodPuerto", query = "SELECT p FROM Port p WHERE p.codPuerto = :codPuerto")
    , @NamedQuery(name = "Port.findByNomPuerto", query = "SELECT p FROM Port p WHERE p.nomPuerto = :nomPuerto")})
public class Port implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_puerto")
    private Integer codPuerto;
    @Size(max = 70)
    @Column(name = "nom_puerto")
    private String nomPuerto;
    @JoinColumn(name = "cod_pais", referencedColumnName = "cod_pais")
    @ManyToOne
    private Country codPais;

    public Port() {
    }

    public Port(Integer codPuerto) {
        this.codPuerto = codPuerto;
    }

    public Integer getCodPuerto() {
        return codPuerto;
    }

    public void setCodPuerto(Integer codPuerto) {
        this.codPuerto = codPuerto;
    }

    public String getNomPuerto() {
        return nomPuerto;
    }

    public void setNomPuerto(String nomPuerto) {
        this.nomPuerto = nomPuerto;
    }

    public Country getCodPais() {
        return codPais;
    }

    public void setCodPais(Country codPais) {
        this.codPais = codPais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPuerto != null ? codPuerto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Port)) {
            return false;
        }
        Port other = (Port) object;
        if ((this.codPuerto == null && other.codPuerto != null) || (this.codPuerto != null && !this.codPuerto.equals(other.codPuerto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Port[ codPuerto=" + codPuerto + " ]";
    }
    
}
