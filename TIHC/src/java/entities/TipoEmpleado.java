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
@Table(name = "tipo_empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEmpleado.findAll", query = "SELECT t FROM TipoEmpleado t")
    , @NamedQuery(name = "TipoEmpleado.findByCodTipoEmpleado", query = "SELECT t FROM TipoEmpleado t WHERE t.codTipoEmpleado = :codTipoEmpleado")
    , @NamedQuery(name = "TipoEmpleado.findByNomTipoEmpleado", query = "SELECT t FROM TipoEmpleado t WHERE t.nomTipoEmpleado = :nomTipoEmpleado")})
public class TipoEmpleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_tipo_empleado")
    private Integer codTipoEmpleado;
    @Size(max = 60)
    @Column(name = "nom_tipo_empleado")
    private String nomTipoEmpleado;
    @OneToMany(mappedBy = "tipoEmpleado")
    private List<Tripulante> tripulanteList;
    @OneToMany(mappedBy = "codTipoEmpleado")
    private List<Usuario> usuarioList;

    public TipoEmpleado() {
    }

    public TipoEmpleado(Integer codTipoEmpleado) {
        this.codTipoEmpleado = codTipoEmpleado;
    }

    public Integer getCodTipoEmpleado() {
        return codTipoEmpleado;
    }

    public void setCodTipoEmpleado(Integer codTipoEmpleado) {
        this.codTipoEmpleado = codTipoEmpleado;
    }

    public String getNomTipoEmpleado() {
        return nomTipoEmpleado;
    }

    public void setNomTipoEmpleado(String nomTipoEmpleado) {
        this.nomTipoEmpleado = nomTipoEmpleado;
    }

    @XmlTransient
    public List<Tripulante> getTripulanteList() {
        return tripulanteList;
    }

    public void setTripulanteList(List<Tripulante> tripulanteList) {
        this.tripulanteList = tripulanteList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codTipoEmpleado != null ? codTipoEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEmpleado)) {
            return false;
        }
        TipoEmpleado other = (TipoEmpleado) object;
        if ((this.codTipoEmpleado == null && other.codTipoEmpleado != null) || (this.codTipoEmpleado != null && !this.codTipoEmpleado.equals(other.codTipoEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TipoEmpleado[ codTipoEmpleado=" + codTipoEmpleado + " ]";
    }
    
}
