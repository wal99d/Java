/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author me
 */
@Entity
@Table(name = "PERSONAL_INFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonalInfo.findByClientNumber", query = "SELECT p FROM PersonalInfo p WHERE p.clientId.clientNumber = :clientNumber" ),
    @NamedQuery(name = "PersonalInfo.findAll", query = "SELECT p FROM PersonalInfo p"),
    @NamedQuery(name = "PersonalInfo.findByPersonalInfoId", query = "SELECT p FROM PersonalInfo p WHERE p.personalInfoId = :personalInfoId"),
    @NamedQuery(name = "PersonalInfo.findByName", query = "SELECT p FROM PersonalInfo p WHERE p.name = :name"),
    @NamedQuery(name = "PersonalInfo.findByGender", query = "SELECT p FROM PersonalInfo p WHERE p.gender = :gender"),
    @NamedQuery(name = "PersonalInfo.findByJob", query = "SELECT p FROM PersonalInfo p WHERE p.job = :job"),
    @NamedQuery(name = "PersonalInfo.findByGovId", query = "SELECT p FROM PersonalInfo p WHERE p.govId = :govId"),
    @NamedQuery(name = "PersonalInfo.findByIdIssuer", query = "SELECT p FROM PersonalInfo p WHERE p.idIssuer = :idIssuer"),
    @NamedQuery(name = "PersonalInfo.findByIdIssueDate", query = "SELECT p FROM PersonalInfo p WHERE p.idIssueDate = :idIssueDate"),
    @NamedQuery(name = "PersonalInfo.findByIdIssueExpireDate", query = "SELECT p FROM PersonalInfo p WHERE p.idIssueExpireDate = :idIssueExpireDate"),
    @NamedQuery(name = "PersonalInfo.findByBirthday", query = "SELECT p FROM PersonalInfo p WHERE p.birthday = :birthday"),
    @NamedQuery(name = "PersonalInfo.findByStatus", query = "SELECT p FROM PersonalInfo p WHERE p.status = :status"),
    @NamedQuery(name = "PersonalInfo.findByDependent", query = "SELECT p FROM PersonalInfo p WHERE p.dependent = :dependent"),
    @NamedQuery(name = "PersonalInfo.findByCrId", query = "SELECT p FROM PersonalInfo p WHERE p.crId = :crId"),
    @NamedQuery(name = "PersonalInfo.findByCrIssuer", query = "SELECT p FROM PersonalInfo p WHERE p.crIssuer = :crIssuer"),
    @NamedQuery(name = "PersonalInfo.findByCrIssueDate", query = "SELECT p FROM PersonalInfo p WHERE p.crIssueDate = :crIssueDate"),
    @NamedQuery(name = "PersonalInfo.findByPobox", query = "SELECT p FROM PersonalInfo p WHERE p.pobox = :pobox"),
    @NamedQuery(name = "PersonalInfo.findByCity", query = "SELECT p FROM PersonalInfo p WHERE p.city = :city"),
    @NamedQuery(name = "PersonalInfo.findByPostalcode", query = "SELECT p FROM PersonalInfo p WHERE p.postalcode = :postalcode"),
    @NamedQuery(name = "PersonalInfo.findByWassel", query = "SELECT p FROM PersonalInfo p WHERE p.wassel = :wassel"),
    @NamedQuery(name = "PersonalInfo.findByTel", query = "SELECT p FROM PersonalInfo p WHERE p.tel = :tel"),
    @NamedQuery(name = "PersonalInfo.findByMobile", query = "SELECT p FROM PersonalInfo p WHERE p.mobile = :mobile"),
    @NamedQuery(name = "PersonalInfo.findByResedentType", query = "SELECT p FROM PersonalInfo p WHERE p.resedentType = :resedentType"),
    @NamedQuery(name = "PersonalInfo.findByResedentRentAmount", query = "SELECT p FROM PersonalInfo p WHERE p.resedentRentAmount = :resedentRentAmount"),
    @NamedQuery(name = "PersonalInfo.findByResedentCity", query = "SELECT p FROM PersonalInfo p WHERE p.resedentCity = :resedentCity"),
    @NamedQuery(name = "PersonalInfo.findByResedentDistrict", query = "SELECT p FROM PersonalInfo p WHERE p.resedentDistrict = :resedentDistrict"),
    @NamedQuery(name = "PersonalInfo.findByResedentStreet", query = "SELECT p FROM PersonalInfo p WHERE p.resedentStreet = :resedentStreet"),
    @NamedQuery(name = "PersonalInfo.findByResedentNumber", query = "SELECT p FROM PersonalInfo p WHERE p.resedentNumber = :resedentNumber")})
public class PersonalInfo implements Serializable {
    @Lob
    @Size(max = 65535)
    @Column(name = "CLIENT_NOTES")
    private String clientNotes;
    @Size(max = 45)
    @Column(name = "otherStatus")
    private String otherStatus;
    @Size(max = 45)
    @Column(name = "otherJob")
    private String otherJob;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PERSONAL_INFO_ID")
    private Integer personalInfoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "GENDER")
    private String gender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "JOB")
    private String job;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GOV_ID")
    private int govId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ID_ISSUER")
    private String idIssuer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ISSUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date idIssueDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ISSUE_EXPIRE_DATE")
    @Temporal(TemporalType.DATE)
    private Date idIssueExpireDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BIRTHDAY")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "STATUS")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DEPENDENT")
    private int dependent;
    @Column(name = "CR_ID")
    private Integer crId;
    @Size(max = 45)
    @Column(name = "CR_ISSUER")
    private String crIssuer;
    @Column(name = "CR_ISSUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date crIssueDate;
    @Column(name = "POBOX")
    private Integer pobox;
    @Size(max = 45)
    @Column(name = "CITY")
    private String city;
    @Column(name = "POSTALCODE")
    private Integer postalcode;
    @Size(max = 45)
    @Column(name = "WASSEL")
    private String wassel;
    @Column(name = "TEL")
    private Integer tel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MOBILE")
    private int mobile;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "RESEDENT_TYPE")
    private String resedentType;
    @Column(name = "RESEDENT_RENT_AMOUNT")
    private Integer resedentRentAmount;
    @Size(max = 45)
    @Column(name = "RESEDENT_CITY")
    private String resedentCity;
    @Size(max = 45)
    @Column(name = "RESEDENT_DISTRICT")
    private String resedentDistrict;
    @Size(max = 45)
    @Column(name = "RESEDENT_STREET")
    private String resedentStreet;
    @Column(name = "RESEDENT_NUMBER")
    private Integer resedentNumber;
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "CLIENT_ID")
    @OneToOne(optional = false)
    private Client clientId;

    public PersonalInfo() {
    }

    public PersonalInfo(Integer personalInfoId) {
        this.personalInfoId = personalInfoId;
    }

    public PersonalInfo(Integer personalInfoId, String name, String gender, String job, int govId, String idIssuer, Date idIssueDate, Date idIssueExpireDate, Date birthday, String status, int dependent, int mobile, String resedentType) {
        this.personalInfoId = personalInfoId;
        this.name = name;
        this.gender = gender;
        this.job = job;
        this.govId = govId;
        this.idIssuer = idIssuer;
        this.idIssueDate = idIssueDate;
        this.idIssueExpireDate = idIssueExpireDate;
        this.birthday = birthday;
        this.status = status;
        this.dependent = dependent;
        this.mobile = mobile;
        this.resedentType = resedentType;
    }

    public Integer getPersonalInfoId() {
        return personalInfoId;
    }

    public void setPersonalInfoId(Integer personalInfoId) {
        this.personalInfoId = personalInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getGovId() {
        return govId;
    }

    public void setGovId(int govId) {
        this.govId = govId;
    }

    public String getIdIssuer() {
        return idIssuer;
    }

    public void setIdIssuer(String idIssuer) {
        this.idIssuer = idIssuer;
    }

    public Date getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(Date idIssueDate) {
        this.idIssueDate = idIssueDate;
    }

    public Date getIdIssueExpireDate() {
        return idIssueExpireDate;
    }

    public void setIdIssueExpireDate(Date idIssueExpireDate) {
        this.idIssueExpireDate = idIssueExpireDate;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDependent() {
        return dependent;
    }

    public void setDependent(int dependent) {
        this.dependent = dependent;
    }

    public Integer getCrId() {
        return crId;
    }

    public void setCrId(Integer crId) {
        this.crId = crId;
    }

    public String getCrIssuer() {
        return crIssuer;
    }

    public void setCrIssuer(String crIssuer) {
        this.crIssuer = crIssuer;
    }

    public Date getCrIssueDate() {
        return crIssueDate;
    }

    public void setCrIssueDate(Date crIssueDate) {
        this.crIssueDate = crIssueDate;
    }

    public Integer getPobox() {
        return pobox;
    }

    public void setPobox(Integer pobox) {
        this.pobox = pobox;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(Integer postalcode) {
        this.postalcode = postalcode;
    }

    public String getWassel() {
        return wassel;
    }

    public void setWassel(String wassel) {
        this.wassel = wassel;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getResedentType() {
        return resedentType;
    }

    public void setResedentType(String resedentType) {
        this.resedentType = resedentType;
    }

    public Integer getResedentRentAmount() {
        return resedentRentAmount;
    }

    public void setResedentRentAmount(Integer resedentRentAmount) {
        this.resedentRentAmount = resedentRentAmount;
    }

    public String getResedentCity() {
        return resedentCity;
    }

    public void setResedentCity(String resedentCity) {
        this.resedentCity = resedentCity;
    }

    public String getResedentDistrict() {
        return resedentDistrict;
    }

    public void setResedentDistrict(String resedentDistrict) {
        this.resedentDistrict = resedentDistrict;
    }

    public String getResedentStreet() {
        return resedentStreet;
    }

    public void setResedentStreet(String resedentStreet) {
        this.resedentStreet = resedentStreet;
    }

    public Integer getResedentNumber() {
        return resedentNumber;
    }

    public void setResedentNumber(Integer resedentNumber) {
        this.resedentNumber = resedentNumber;
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
        hash += (personalInfoId != null ? personalInfoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonalInfo)) {
            return false;
        }
        PersonalInfo other = (PersonalInfo) object;
        if ((this.personalInfoId == null && other.personalInfoId != null) || (this.personalInfoId != null && !this.personalInfoId.equals(other.personalInfoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "loan.system.entities.PersonalInfo[ personalInfoId=" + personalInfoId + " ]";
    }

    public String getOtherJob() {
        return otherJob;
    }

    public void setOtherJob(String otherJob) {
        this.otherJob = otherJob;
    }

    public String getOtherStatus() {
        return otherStatus;
    }

    public void setOtherStatus(String otherStatus) {
        this.otherStatus = otherStatus;
    }

    public String getClientNotes() {
        return clientNotes;
    }

    public void setClientNotes(String clientNotes) {
        this.clientNotes = clientNotes;
    }
    
}
