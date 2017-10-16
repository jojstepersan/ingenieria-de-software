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
    @NamedQuery(name = "Tripulante.findAll", query = "SELECT t FROM Tripulante t")
    , @NamedQuery(name = "Tripulante.findByCodEmpleado", query = "SELECT t FROM Tripulante t WHERE t.codEmpleado = :codEmpleado")
    , @NamedQuery(name = "Tripulante.findByNomEmpleado", query = "SELECT t FROM Tripulante t WHERE t.nomEmpleado = :nomEmpleado")
    , @NamedQuery(name = "Tripulante.findByApeEmpleado", query = "SELECT t FROM Tripulante t WHERE t.apeEmpleado = :apeEmpleado")})
public class Tripulante implements Serializable {

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
    private Barco codBarco;
    @JoinColumn(name = "tipo_empleado", referencedColumnName = "cod_tipo_empleado")
    @ManyToOne
    private TipoEmpleado tipoEmpleado;

    public Tripulante() {
    }

    public Tripulante(Integer codEmpleado) {
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

    public Barco getCodBarco() {
        return codBarco;
    }

    public void setCodBarco(Barco codBarco) {
        this.codBarco = codBarco;
    }

    public TipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
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
        if (!(object instanceof Tripulante)) {
            return false;
        }
        Tripulante other = (Tripulante) object;
        if ((this.codEmpleado == null && other.codEmpleado != null) || (this.codEmpleado != null && !this.codEmpleado.equals(other.codEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Tripulante[ codEmpleado=" + codEmpleado + " ]";
    }
    
}
