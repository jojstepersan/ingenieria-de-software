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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jojstepersan
 */
@Entity
@Table(name = "contrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contract.findAll", query = "SELECT c FROM Contract c")
    , @NamedQuery(name = "Contract.findByCodContrato", query = "SELECT c FROM Contract c WHERE c.codContrato = :codContrato")})
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_contrato")
    private Integer codContrato;
    @JoinColumn(name = "cod_cliente", referencedColumnName = "cod_cliente")
    @ManyToOne
    private Client codCliente;
    @OneToMany(mappedBy = "codContrato")
    private List<Load> loadList;

    public Contract() {
    }

    public Contract(Integer codContrato) {
        this.codContrato = codContrato;
    }

    public Integer getCodContrato() {
        return codContrato;
    }

    public void setCodContrato(Integer codContrato) {
        this.codContrato = codContrato;
    }

    public Client getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Client codCliente) {
        this.codCliente = codCliente;
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
        hash += (codContrato != null ? codContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contract)) {
            return false;
        }
        Contract other = (Contract) object;
        if ((this.codContrato == null && other.codContrato != null) || (this.codContrato != null && !this.codContrato.equals(other.codContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Contract[ codContrato=" + codContrato + " ]";
    }
    
}
