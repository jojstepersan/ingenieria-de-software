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
    @NamedQuery(name = "Ship.findAll", query = "SELECT s FROM Ship s")
    , @NamedQuery(name = "Ship.findByCodBarco", query = "SELECT s FROM Ship s WHERE s.codBarco = :codBarco")
    , @NamedQuery(name = "Ship.findByFechaAdquisicion", query = "SELECT s FROM Ship s WHERE s.fechaAdquisicion = :fechaAdquisicion")
    , @NamedQuery(name = "Ship.findByFechaUltimoMantenimiento", query = "SELECT s FROM Ship s WHERE s.fechaUltimoMantenimiento = :fechaUltimoMantenimiento")})
public class Ship implements Serializable {

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
    private List<crewman> crewmanList;
    @JoinColumn(name = "cod_estado", referencedColumnName = "cod_estado")
    @ManyToOne
    private State codEstado;
    @OneToMany(mappedBy = "codBarco")
    private List<Load> loadList;

    public Ship() {
    }

    public Ship(Integer codBarco) {
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
    public List<crewman> getCrewmanList() {
        return crewmanList;
    }

    public void setCrewmanList(List<crewman> crewmanList) {
        this.crewmanList = crewmanList;
    }

    public State getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(State codEstado) {
        this.codEstado = codEstado;
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
        hash += (codBarco != null ? codBarco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ship)) {
            return false;
        }
        Ship other = (Ship) object;
        if ((this.codBarco == null && other.codBarco != null) || (this.codBarco != null && !this.codBarco.equals(other.codBarco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ship[ codBarco=" + codBarco + " ]";
    }
    
}
