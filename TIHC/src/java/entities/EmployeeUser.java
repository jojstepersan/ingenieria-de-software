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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeeUser.findAll", query = "SELECT e FROM EmployeeUser e")
    , @NamedQuery(name = "EmployeeUser.findByCodUsuario", query = "SELECT e FROM EmployeeUser e WHERE e.codUsuario = :codUsuario")
    , @NamedQuery(name = "EmployeeUser.findByNomUsuario", query = "SELECT e FROM EmployeeUser e WHERE e.nomUsuario = :nomUsuario")
    , @NamedQuery(name = "EmployeeUser.findByApeUsuario", query = "SELECT e FROM EmployeeUser e WHERE e.apeUsuario = :apeUsuario")
    , @NamedQuery(name = "EmployeeUser.findByUsername", query = "SELECT e FROM EmployeeUser e WHERE e.username = :username")
    , @NamedQuery(name = "EmployeeUser.findByPass", query = "SELECT e FROM EmployeeUser e WHERE e.pass = :pass")})
public class EmployeeUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_usuario")
    private Integer codUsuario;
    @Size(max = 100)
    @Column(name = "nom_usuario")
    private String nomUsuario;
    @Size(max = 100)
    @Column(name = "ape_usuario")
    private String apeUsuario;
    @Size(max = 100)
    @Column(name = "username")
    private String username;
    @Size(max = 200)
    @Column(name = "pass")
    private String pass;
    @JoinColumn(name = "cod_tipo_empleado", referencedColumnName = "cod_tipo_empleado")
    @ManyToOne
    private TypeEmployee codTipoEmpleado;

    public EmployeeUser() {
    }

    public EmployeeUser(Integer codUsuario) {
        this.codUsuario = codUsuario;
    }

    public Integer getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Integer codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getApeUsuario() {
        return apeUsuario;
    }

    public void setApeUsuario(String apeUsuario) {
        this.apeUsuario = apeUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public TypeEmployee getCodTipoEmpleado() {
        return codTipoEmpleado;
    }

    public void setCodTipoEmpleado(TypeEmployee codTipoEmpleado) {
        this.codTipoEmpleado = codTipoEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codUsuario != null ? codUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeUser)) {
            return false;
        }
        EmployeeUser other = (EmployeeUser) object;
        if ((this.codUsuario == null && other.codUsuario != null) || (this.codUsuario != null && !this.codUsuario.equals(other.codUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EmployeeUser[ codUsuario=" + codUsuario + " ]";
    }
    
}
