/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jojstepersan
 */
@Entity
@Table(name = "barco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Barco.findAll", query = "SELECT b FROM Barco b")
    , @NamedQuery(name = "Barco.findByCodBarco", query = "SELECT b FROM Barco b WHERE b.codBarco = :codBarco")
    , @NamedQuery(name = "Barco.findByFechaAdquisicion", query = "SELECT b FROM Barco b WHERE b.fechaAdquisicion = :fechaAdquisicion")
    , @NamedQuery(name = "Barco.findByFechaUltimoMantenimiento", query = "SELECT b FROM Barco b WHERE b.fechaUltimoMantenimiento = :fechaUltimoMantenimiento")})
public class Barco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_barco")
    private Integer codBarco;
    @Column(name = "fecha_adquisicion")
    @Temporal(TemporalType.DATE)
    private Date fechaAdquisicion;
    @Column(name = "fecha_ultimo_mantenimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaUltimoMantenimiento;
    @OneToMany(mappedBy = "codBarco")
    private List<Tripulante> tripulanteList;
    @JoinColumn(name = "cod_estado", referencedColumnName = "cod_estado")
    @ManyToOne
    private Estado codEstado;
    @OneToMany(mappedBy = "codBarco")
    private List<Carga> cargaList;

    public Barco() {
    }

    public Barco(Integer codBarco) {
        this.codBarco = codBarco;
    }

    public Integer getCodBarco() {
        return codBarco;
    }

    public void setCodBarco(Integer codBarco) {
        this.codBarco = codBarco;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public Date getFechaUltimoMantenimiento() {
        return fechaUltimoMantenimiento;
    }

    public void setFechaUltimoMantenimiento(Date fechaUltimoMantenimiento) {
        this.fechaUltimoMantenimiento = fechaUltimoMantenimiento;
    }

    @XmlTransient
    public List<Tripulante> getTripulanteList() {
        return tripulanteList;
    }

    public void setTripulanteList(List<Tripulante> tripulanteList) {
        this.tripulanteList = tripulanteList;
    }

    public Estado getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Estado codEstado) {
        this.codEstado = codEstado;
    }

    @XmlTransient
    public List<Carga> getCargaList() {
        return cargaList;
    }

    public void setCargaList(List<Carga> cargaList) {
        this.cargaList = cargaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codBarco != null ? codBarco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Barco)) {
            return false;
        }
        Barco other = (Barco) object;
        if ((this.codBarco == null && other.codBarco != null) || (this.codBarco != null && !this.codBarco.equals(other.codBarco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Barco[ codBarco=" + codBarco + " ]";
    }
    
}
