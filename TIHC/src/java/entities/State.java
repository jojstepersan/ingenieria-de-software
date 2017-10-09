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
@Table(name = "estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "State.findAll", query = "SELECT s FROM State s")
    , @NamedQuery(name = "State.findByCodEstado", query = "SELECT s FROM State s WHERE s.codEstado = :codEstado")
    , @NamedQuery(name = "State.findByNomEstado", query = "SELECT s FROM State s WHERE s.nomEstado = :nomEstado")
    , @NamedQuery(name = "State.findByDescripcion", query = "SELECT s FROM State s WHERE s.descripcion = :descripcion")})
public class State implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_estado")
    private Integer codEstado;
    @Size(max = 60)
    @Column(name = "nom_estado")
    private String nomEstado;
    @Size(max = 400)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "codEstado")
    private List<Ship> shipList;

    public State() {
    }

    public State(Integer codEstado) {
        this.codEstado = codEstado;
    }

    public Integer getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Integer codEstado) {
        this.codEstado = codEstado;
    }

    public String getNomEstado() {
        return nomEstado;
    }

    public void setNomEstado(String nomEstado) {
        this.nomEstado = nomEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Ship> getShipList() {
        return shipList;
    }

    public void setShipList(List<Ship> shipList) {
        this.shipList = shipList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEstado != null ? codEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof State)) {
            return false;
        }
        State other = (State) object;
        if ((this.codEstado == null && other.codEstado != null) || (this.codEstado != null && !this.codEstado.equals(other.codEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.State[ codEstado=" + codEstado + " ]";
    }
    
}
