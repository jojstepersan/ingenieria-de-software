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
@Table(name = "tipo_carga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeLoad.findAll", query = "SELECT t FROM TypeLoad t")
    , @NamedQuery(name = "TypeLoad.findByCodTipoCarga", query = "SELECT t FROM TypeLoad t WHERE t.codTipoCarga = :codTipoCarga")
    , @NamedQuery(name = "TypeLoad.findByNomTipoCarga", query = "SELECT t FROM TypeLoad t WHERE t.nomTipoCarga = :nomTipoCarga")})
public class TypeLoad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_tipo_carga")
    private Integer codTipoCarga;
    @Size(max = 100)
    @Column(name = "nom_tipo_carga")
    private String nomTipoCarga;
    @OneToMany(mappedBy = "codTipoCarga")
    private List<Load> loadList;

    public TypeLoad() {
    }

    public TypeLoad(Integer codTipoCarga) {
        this.codTipoCarga = codTipoCarga;
    }

    public Integer getCodTipoCarga() {
        return codTipoCarga;
    }

    public void setCodTipoCarga(Integer codTipoCarga) {
        this.codTipoCarga = codTipoCarga;
    }

    public String getNomTipoCarga() {
        return nomTipoCarga;
    }

    public void setNomTipoCarga(String nomTipoCarga) {
        this.nomTipoCarga = nomTipoCarga;
    }

    @XmlTransient
    public List<Load> getLoadList() {
        return loadList;
    }

    public void setLoadList(List<Load> loadList) {
        this.loadList = loadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codTipoCarga != null ? codTipoCarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeLoad)) {
            return false;
        }
        TypeLoad other = (TypeLoad) object;
        if ((this.codTipoCarga == null && other.codTipoCarga != null) || (this.codTipoCarga != null && !this.codTipoCarga.equals(other.codTipoCarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TypeLoad[ codTipoCarga=" + codTipoCarga + " ]";
    }
    
}
