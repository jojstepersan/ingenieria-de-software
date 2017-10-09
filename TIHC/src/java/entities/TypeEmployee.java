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
    @NamedQuery(name = "TypeEmployee.findAll", query = "SELECT t FROM TypeEmployee t")
    , @NamedQuery(name = "TypeEmployee.findByCodTipoEmpleado", query = "SELECT t FROM TypeEmployee t WHERE t.codTipoEmpleado = :codTipoEmpleado")
    , @NamedQuery(name = "TypeEmployee.findByNomTipoEmpleado", query = "SELECT t FROM TypeEmployee t WHERE t.nomTipoEmpleado = :nomTipoEmpleado")})
public class TypeEmployee implements Serializable {

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
    private List<crewman> crewmanList;
    @OneToMany(mappedBy = "codTipoEmpleado")
    private List<EmployeeUser> employeeUserList;

    public TypeEmployee() {
    }

    public TypeEmployee(Integer codTipoEmpleado) {
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
    public List<crewman> getCrewmanList() {
        return crewmanList;
    }

    public void setCrewmanList(List<crewman> crewmanList) {
        this.crewmanList = crewmanList;
    }

    @XmlTransient
    public List<EmployeeUser> getEmployeeUserList() {
        return employeeUserList;
    }

    public void setEmployeeUserList(List<EmployeeUser> employeeUserList) {
        this.employeeUserList = employeeUserList;
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
        if (!(object instanceof TypeEmployee)) {
            return false;
        }
        TypeEmployee other = (TypeEmployee) object;
        if ((this.codTipoEmpleado == null && other.codTipoEmpleado != null) || (this.codTipoEmpleado != null && !this.codTipoEmpleado.equals(other.codTipoEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TypeEmployee[ codTipoEmpleado=" + codTipoEmpleado + " ]";
    }
    
}
