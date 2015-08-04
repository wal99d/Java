/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author me
 */
@Entity
@Table(name = "DEPARTEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Departement.findAll", query = "SELECT d FROM Departement d"),
    @NamedQuery(name = "Departement.findByDeptId", query = "SELECT d FROM Departement d WHERE d.deptId = :deptId"),
    @NamedQuery(name = "Departement.findByDeptName", query = "SELECT d FROM Departement d WHERE d.deptName = :deptName"),
    @NamedQuery(name = "Departement.findByDeptScanner", query = "SELECT d FROM Departement d WHERE d.deptScanner = :deptScanner"),
    @NamedQuery(name = "Departement.findByIsEditable", query = "SELECT d FROM Departement d WHERE d.isEditable = :isEditable"),
    @NamedQuery(name = "Departement.findByDeptScannerFileName", query = "SELECT d FROM Departement d WHERE d.deptScannerFileName = :deptScannerFileName")})
public class Departement implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userDeptId")
    private Collection<User> userCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DEPT_ID")
    private Integer deptId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "DEPT_NAME")
    private String deptName;
    @Size(max = 45)
    @Column(name = "DEPT_SCANNER")
    private String deptScanner;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isEditable")
    private boolean isEditable;
    @Size(max = 45)
    @Column(name = "DEPT_SCANNER_FILE_NAME")
    private String deptScannerFileName;

    public Departement() {
    }

    public Departement(Integer deptId) {
        this.deptId = deptId;
    }

    public Departement(Integer deptId, String deptName, boolean isEditable) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.isEditable = isEditable;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptScanner() {
        return deptScanner;
    }

    public void setDeptScanner(String deptScanner) {
        this.deptScanner = deptScanner;
    }

    public boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public String getDeptScannerFileName() {
        return deptScannerFileName;
    }

    public void setDeptScannerFileName(String deptScannerFileName) {
        this.deptScannerFileName = deptScannerFileName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deptId != null ? deptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departement)) {
            return false;
        }
        Departement other = (Departement) object;
        if ((this.deptId == null && other.deptId != null) || (this.deptId != null && !this.deptId.equals(other.deptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "loan.system.entities.Departement[ deptId=" + deptId + " ]";
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }
    
}
