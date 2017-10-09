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
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p")
    , @NamedQuery(name = "Pais.findByCodPais", query = "SELECT p FROM Pais p WHERE p.codPais = :codPais")
    , @NamedQuery(name = "Pais.findByNomPais", query = "SELECT p FROM Pais p WHERE p.nomPais = :nomPais")
    , @NamedQuery(name = "Pais.findByUbicacionX", query = "SELECT p FROM Pais p WHERE p.ubicacionX = :ubicacionX")
    , @NamedQuery(name = "Pais.findByUbicaionY", query = "SELECT p FROM Pais p WHERE p.ubicaionY = :ubicaionY")})
public class Pais implements Serializable {

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
    private List<Puerto> puertoList;
    @OneToMany(mappedBy = "destino")
    private List<Carga> cargaList;
    @OneToMany(mappedBy = "origen")
    private List<Carga> cargaList1;

    public Pais() {
    }

    public Pais(Integer codPais) {
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
    public List<Puerto> getPuertoList() {
        return puertoList;
    }

    public void setPuertoList(List<Puerto> puertoList) {
        this.puertoList = puertoList;
    }

    @XmlTransient
    public List<Carga> getCargaList() {
        return cargaList;
    }

    public void setCargaList(List<Carga> cargaList) {
        this.cargaList = cargaList;
    }

    @XmlTransient
    public List<Carga> getCargaList1() {
        return cargaList1;
    }

    public void setCargaList1(List<Carga> cargaList1) {
        this.cargaList1 = cargaList1;
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
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.codPais == null && other.codPais != null) || (this.codPais != null && !this.codPais.equals(other.codPais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pais[ codPais=" + codPais + " ]";
    }
    
}
