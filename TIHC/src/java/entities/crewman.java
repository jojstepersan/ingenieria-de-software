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
@Table(name = "tripulante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "crewman.findAll", query = "SELECT c FROM crewman c")
    , @NamedQuery(name = "crewman.findByCodEmpleado", query = "SELECT c FROM crewman c WHERE c.codEmpleado = :codEmpleado")
    , @NamedQuery(name = "crewman.findByNomEmpleado", query = "SELECT c FROM crewman c WHERE c.nomEmpleado = :nomEmpleado")
    , @NamedQuery(name = "crewman.findByApeEmpleado", query = "SELECT c FROM crewman c WHERE c.apeEmpleado = :apeEmpleado")})
public class crewman implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_empleado")
    private Integer codEmpleado;
    @Size(max = 60)
    @Column(name = "nom_empleado")
    private String nomEmpleado;
    @Size(max = 60)
    @Column(name = "ape_empleado")
    private String apeEmpleado;
    @JoinColumn(name = "cod_barco", referencedColumnName = "cod_barco")
    @ManyToOne
    private Ship codBarco;
    @JoinColumn(name = "tipo_empleado", referencedColumnName = "cod_tipo_empleado")
    @ManyToOne
    private TypeEmployee tipoEmpleado;

    public crewman() {
    }

    public crewman(Integer codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public Integer getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(Integer codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public String getNomEmpleado() {
        return nomEmpleado;
    }

    public void setNomEmpleado(String nomEmpleado) {
        this.nomEmpleado = nomEmpleado;
    }

    public String getApeEmpleado() {
        return apeEmpleado;
    }

    public void setApeEmpleado(String apeEmpleado) {
        this.apeEmpleado = apeEmpleado;
    }

    public Ship getCodBarco() {
        return codBarco;
    }

    public void setCodBarco(Ship codBarco) {
        this.codBarco = codBarco;
    }

    public TypeEmployee getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(TypeEmployee tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpleado != null ? codEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof crewman)) {
            return false;
        }
        crewman other = (crewman) object;
        if ((this.codEmpleado == null && other.codEmpleado != null) || (this.codEmpleado != null && !this.codEmpleado.equals(other.codEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.crewman[ codEmpleado=" + codEmpleado + " ]";
    }
    
}
