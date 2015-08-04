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
@Table(name = "CLIENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "client.getLast", query = "SELECT c FROM Client c ORDER BY c.clientNumber DESC"),
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findByClientId", query = "SELECT c FROM Client c WHERE c.clientId = :clientId"),
    @NamedQuery(name = "Client.findByClientNumber", query = "SELECT c FROM Client c WHERE c.clientNumber = :clientNumber")})
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CLIENT_ID")
    private Integer clientId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLIENT_NUMBER")
    private int clientNumber;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Collection<EmploymentInfo> employmentInfoCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Collection<PersonalInfo> personalInfoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reqClientId")
    private Collection<Request> requestCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Collection<FinancailInfo> financailInfoCollection;

    public Client() {
    }

    public Client(Integer clientId) {
        this.clientId = clientId;
    }

    public Client(Integer clientId, int clientNumber) {
        this.clientId = clientId;
        this.clientNumber = clientNumber;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    @XmlTransient
    public Collection<EmploymentInfo> getEmploymentInfoCollection() {
        return employmentInfoCollection;
    }

    public void setEmploymentInfoCollection(Collection<EmploymentInfo> employmentInfoCollection) {
        this.employmentInfoCollection = employmentInfoCollection;
    }

    @XmlTransient
    public Collection<PersonalInfo> getPersonalInfoCollection() {
        return personalInfoCollection;
    }

    public void setPersonalInfoCollection(Collection<PersonalInfo> personalInfoCollection) {
        this.personalInfoCollection = personalInfoCollection;
    }

    @XmlTransient
    public Collection<Request> getRequestCollection() {
        return requestCollection;
    }

    public void setRequestCollection(Collection<Request> requestCollection) {
        this.requestCollection = requestCollection;
    }

    @XmlTransient
    public Collection<FinancailInfo> getFinancailInfoCollection() {
        return financailInfoCollection;
    }

    public void setFinancailInfoCollection(Collection<FinancailInfo> financailInfoCollection) {
        this.financailInfoCollection = financailInfoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientId != null ? clientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.clientId == null && other.clientId != null) || (this.clientId != null && !this.clientId.equals(other.clientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "loan.system.entities.Client[ clientId=" + clientId + " ]";
    }
    
}
