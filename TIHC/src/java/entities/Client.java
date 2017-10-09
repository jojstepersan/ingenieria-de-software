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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
    , @NamedQuery(name = "Client.findByCodCliente", query = "SELECT c FROM Client c WHERE c.codCliente = :codCliente")
    , @NamedQuery(name = "Client.findByNomCliente", query = "SELECT c FROM Client c WHERE c.nomCliente = :nomCliente")
    , @NamedQuery(name = "Client.findByApeCliente", query = "SELECT c FROM Client c WHERE c.apeCliente = :apeCliente")})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_cliente")
    private Integer codCliente;
    @Size(max = 60)
    @Column(name = "nom_cliente")
    private String nomCliente;
    @Size(max = 60)
    @Column(name = "ape_cliente")
    private String apeCliente;
    @OneToMany(mappedBy = "codCliente")
    private List<Contract> contractList;

    public Client() {
    }

    public Client(Integer codCliente) {
        this.codCliente = codCliente;
    }

    public Integer getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Integer codCliente) {
        this.codCliente = codCliente;
    }

    public String getNomCliente() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente = nomCliente;
    }

    public String getApeCliente() {
        return apeCliente;
    }

    public void setApeCliente(String apeCliente) {
        this.apeCliente = apeCliente;
    }

    @XmlTransient
    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCliente != null ? codCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.codCliente == null && other.codCliente != null) || (this.codCliente != null && !this.codCliente.equals(other.codCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Client[ codCliente=" + codCliente + " ]";
    }
    
}
