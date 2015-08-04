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
@Table(name = "EMPLOYMENT_INFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmploymentInfo.findByClientNumber", query = "SELECT e FROM EmploymentInfo e WHERE e.clientId.clientNumber = :clientNumber"),
    @NamedQuery(name = "EmploymentInfo.findAll", query = "SELECT e FROM EmploymentInfo e"),
    @NamedQuery(name = "EmploymentInfo.findByEmploymentId", query = "SELECT e FROM EmploymentInfo e WHERE e.employmentId = :employmentId"),
    @NamedQuery(name = "EmploymentInfo.findByEmployer", query = "SELECT e FROM EmploymentInfo e WHERE e.employer = :employer"),
    @NamedQuery(name = "EmploymentInfo.findByJobTitle", query = "SELECT e FROM EmploymentInfo e WHERE e.jobTitle = :jobTitle"),
    @NamedQuery(name = "EmploymentInfo.findByEmploymentNumber", query = "SELECT e FROM EmploymentInfo e WHERE e.employmentNumber = :employmentNumber"),
    @NamedQuery(name = "EmploymentInfo.findByDepartement", query = "SELECT e FROM EmploymentInfo e WHERE e.departement = :departement"),
    @NamedQuery(name = "EmploymentInfo.findByEmploymentDate", query = "SELECT e FROM EmploymentInfo e WHERE e.employmentDate = :employmentDate"),
    @NamedQuery(name = "EmploymentInfo.findByRank", query = "SELECT e FROM EmploymentInfo e WHERE e.rank = :rank"),
    @NamedQuery(name = "EmploymentInfo.findByMilitaryNumber", query = "SELECT e FROM EmploymentInfo e WHERE e.militaryNumber = :militaryNumber"),
    @NamedQuery(name = "EmploymentInfo.findBySalary", query = "SELECT e FROM EmploymentInfo e WHERE e.salary = :salary"),
    @NamedQuery(name = "EmploymentInfo.findByExtraIncom", query = "SELECT e FROM EmploymentInfo e WHERE e.extraIncom = :extraIncom"),
    @NamedQuery(name = "EmploymentInfo.findByPayDay", query = "SELECT e FROM EmploymentInfo e WHERE e.payDay = :payDay"),
    @NamedQuery(name = "EmploymentInfo.findByToRetire", query = "SELECT e FROM EmploymentInfo e WHERE e.toRetire = :toRetire"),
    @NamedQuery(name = "EmploymentInfo.findByEmployerReportTo", query = "SELECT e FROM EmploymentInfo e WHERE e.employerReportTo = :employerReportTo"),
    @NamedQuery(name = "EmploymentInfo.findByEmployerJobTitle", query = "SELECT e FROM EmploymentInfo e WHERE e.employerJobTitle = :employerJobTitle"),
    @NamedQuery(name = "EmploymentInfo.findByEmployerTel", query = "SELECT e FROM EmploymentInfo e WHERE e.employerTel = :employerTel"),
    @NamedQuery(name = "EmploymentInfo.findByEmployerTelExt", query = "SELECT e FROM EmploymentInfo e WHERE e.employerTelExt = :employerTelExt"),
    @NamedQuery(name = "EmploymentInfo.findByContactName", query = "SELECT e FROM EmploymentInfo e WHERE e.contactName = :contactName"),
    @NamedQuery(name = "EmploymentInfo.findByContactTel", query = "SELECT e FROM EmploymentInfo e WHERE e.contactTel = :contactTel"),
    @NamedQuery(name = "EmploymentInfo.findByContactMobile", query = "SELECT e FROM EmploymentInfo e WHERE e.contactMobile = :contactMobile")})
public class EmploymentInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EMPLOYMENT_ID")
    private Integer employmentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "EMPLOYER")
    private String employer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "JOB_TITLE")
    private String jobTitle;
    @Column(name = "EMPLOYMENT_NUMBER")
    private Integer employmentNumber;
    @Size(max = 45)
    @Column(name = "DEPARTEMENT")
    private String departement;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPLOYMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date employmentDate;
    @Size(max = 45)
    @Column(name = "RANK")
    private String rank;
    @Column(name = "MILITARY_NUMBER")
    private Integer militaryNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SALARY")
    private int salary;
    @Column(name = "EXTRA_INCOM")
    private Integer extraIncom;
    @Size(max = 45)
    @Column(name = "PAY_DAY")
    private String payDay;
    @Size(max = 45)
    @Column(name = "TO_RETIRE")
    private String toRetire;
    @Size(max = 45)
    @Column(name = "EMPLOYER_REPORT_TO")
    private String employerReportTo;
    @Size(max = 45)
    @Column(name = "EMPLOYER_JOB_TITLE")
    private String employerJobTitle;
    @Column(name = "EMPLOYER_TEL")
    private Integer employerTel;
    @Column(name = "EMPLOYER_TEL_EXT")
    private Integer employerTelExt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "CONTACT_NAME")
    private String contactName;
    @Column(name = "CONTACT_TEL")
    private Integer contactTel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONTACT_MOBILE")
    private int contactMobile;
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "CLIENT_ID")
    @OneToOne(optional = false)
    private Client clientId;

    public EmploymentInfo() {
    }

    public EmploymentInfo(Integer employmentId) {
        this.employmentId = employmentId;
    }

    public EmploymentInfo(Integer employmentId, String employer, String jobTitle, Date employmentDate, int salary, String contactName, int contactMobile) {
        this.employmentId = employmentId;
        this.employer = employer;
        this.jobTitle = jobTitle;
        this.employmentDate = employmentDate;
        this.salary = salary;
        this.contactName = contactName;
        this.contactMobile = contactMobile;
    }

    public Integer getEmploymentId() {
        return employmentId;
    }

    public void setEmploymentId(Integer employmentId) {
        this.employmentId = employmentId;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getEmploymentNumber() {
        return employmentNumber;
    }

    public void setEmploymentNumber(Integer employmentNumber) {
        this.employmentNumber = employmentNumber;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getMilitaryNumber() {
        return militaryNumber;
    }

    public void setMilitaryNumber(Integer militaryNumber) {
        this.militaryNumber = militaryNumber;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Integer getExtraIncom() {
        return extraIncom;
    }

    public void setExtraIncom(Integer extraIncom) {
        this.extraIncom = extraIncom;
    }

    public String getPayDay() {
        return payDay;
    }

    public void setPayDay(String payDay) {
        this.payDay = payDay;
    }

    public String getToRetire() {
        return toRetire;
    }

    public void setToRetire(String toRetire) {
        this.toRetire = toRetire;
    }

    public String getEmployerReportTo() {
        return employerReportTo;
    }

    public void setEmployerReportTo(String employerReportTo) {
        this.employerReportTo = employerReportTo;
    }

    public String getEmployerJobTitle() {
        return employerJobTitle;
    }

    public void setEmployerJobTitle(String employerJobTitle) {
        this.employerJobTitle = employerJobTitle;
    }

    public Integer getEmployerTel() {
        return employerTel;
    }

    public void setEmployerTel(Integer employerTel) {
        this.employerTel = employerTel;
    }

    public Integer getEmployerTelExt() {
        return employerTelExt;
    }

    public void setEmployerTelExt(Integer employerTelExt) {
        this.employerTelExt = employerTelExt;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Integer getContactTel() {
        return contactTel;
    }

    public void setContactTel(Integer contactTel) {
        this.contactTel = contactTel;
    }

    public int getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(int contactMobile) {
        this.contactMobile = contactMobile;
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
        hash += (employmentId != null ? employmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmploymentInfo)) {
            return false;
        }
        EmploymentInfo other = (EmploymentInfo) object;
        if ((this.employmentId == null && other.employmentId != null) || (this.employmentId != null && !this.employmentId.equals(other.employmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "loan.system.entities.EmploymentInfo[ employmentId=" + employmentId + " ]";
    }
    
}
