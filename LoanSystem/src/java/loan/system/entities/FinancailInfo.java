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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author me
 */
@Entity
@Table(name = "FINANCAIL_INFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinancailInfo.findByClientNumber", query = "SELECT f FROM FinancailInfo f WHERE f.clientId.clientNumber= :clientNumber"),
    @NamedQuery(name = "FinancailInfo.findAll", query = "SELECT f FROM FinancailInfo f"),
    @NamedQuery(name = "FinancailInfo.findByFinancailId", query = "SELECT f FROM FinancailInfo f WHERE f.financailId = :financailId"),
    @NamedQuery(name = "FinancailInfo.findByLiable", query = "SELECT f FROM FinancailInfo f WHERE f.liable = :liable")})
public class FinancailInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FINANCAIL_ID")
    private Integer financailId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LIABLE")
    private boolean liable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "financailId")
    private Collection<Loaner> loanerCollection;
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "CLIENT_ID")
    @OneToOne(optional = false)
    private Client clientId;

    public FinancailInfo() {
    }

    public FinancailInfo(Integer financailId) {
        this.financailId = financailId;
    }

    public FinancailInfo(Integer financailId, boolean liable) {
        this.financailId = financailId;
        this.liable = liable;
    }

    public Integer getFinancailId() {
        return financailId;
    }

    public void setFinancailId(Integer financailId) {
        this.financailId = financailId;
    }

    public boolean getLiable() {
        return liable;
    }

    public void setLiable(boolean liable) {
        this.liable = liable;
    }

    @XmlTransient
    public Collection<Loaner> getLoanerCollection() {
        return loanerCollection;
    }

    public void setLoanerCollection(Collection<Loaner> loanerCollection) {
        this.loanerCollection = loanerCollection;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (financailId != null ? financailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinancailInfo)) {
            return false;
        }
        FinancailInfo other = (FinancailInfo) object;
        if ((this.financailId == null && other.financailId != null) || (this.financailId != null && !this.financailId.equals(other.financailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "loan.system.entities.FinancailInfo[ financailId=" + financailId + " ]";
    }
    
}
