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
@Table(name = "REQUEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Request.findLast", query = "SELECT r FROM Request r WHERE r.reqClientId.clientNumber = :clientNumber ORDER BY r.reqNumber DESC"),
    @NamedQuery(name = "Request.findByClientNumber", query = "SELECT r FROM Request r WHERE r.reqClientId.clientNumber = :clientNumber"),
    @NamedQuery(name = "Request.findByUserId", query = "SELECT r FROM Request r JOIN FETCH r.reqUserId u WHERE u.userId = :userId"),
    @NamedQuery(name = "Request.findByStatusId", query = "SELECT r FROM Request r JOIN FETCH r.reqStatusId s WHERE s.statusId = :statusId"),
    @NamedQuery(name = "Request.ArchiveViewForAll", query = "SELECT r FROM Request r JOIN FETCH r.reqClientId c WHERE (TRIM(c.clientNumber) LIKE :subject AND r.reqStatusId.statusId IN (7,8)) OR (TRIM(r.reqNumber) LIKE :subject AND r.reqStatusId.statusId IN (7,8)) OR (TRIM(r.reqClientGovId) LIKE :subject  AND r.reqStatusId.statusId IN (7,8))"),//"SELECT r FROM Request r LEFT JOIN FETCH r.reqClientId c WHERE (TRIM(c.clientNumber) LIKE :subject  OR TRIM(r.reqNumber) LIKE :subject ) OR (TRIM(r.reqClientGovId) LIKE :subject) AND (r.reqStatusId.statusId IN (7,8))"),
    @NamedQuery(name = "Request.findAll", query = "SELECT r FROM Request r"),
    @NamedQuery(name = "Request.ViewForAll", query = "SELECT r FROM Request r WHERE r.reqStatusId.statusId = :statusId"),
    @NamedQuery(name = "Request.ViewForUser" , query = "SELECT r FROM Request r WHERE r.reqStatusId.statusId IN (2,11)"),
    @NamedQuery(name = "Request.ViewForManager" , query = "SELECT r FROM Request r WHERE r.reqStatusId.statusId IN (1,14)"),
    @NamedQuery(name = "Request.ViewForCEO" , query = "SELECT r FROM Request r WHERE r.reqStatusId.statusId IN (4,12,13)"),
    @NamedQuery(name = "Request.ViewForEmployee", query = "SELECT r FROM Request r WHERE (r.reqStatusId.statusId =3 OR r.reqStatusId.statusId=9) AND r.reqUserId.userId= :userId "),
    @NamedQuery(name = "Request.ViewForCoorindator", query = "SELECT r FROM Request r WHERE r.reqStatusId.statusId IN (5,6,10)"),
    @NamedQuery(name = "Request.ArchiveForAll", query = "SELECT r FROM Request r WHERE r.reqStatusId.statusId IN (7,8)"),
    @NamedQuery(name = "Request.findByReqId", query = "SELECT r FROM Request r WHERE r.reqId = :reqId"),
    @NamedQuery(name = "Request.findByReqNumber", query = "SELECT r FROM Request r WHERE r.reqNumber = :reqNumber")})
public class Request implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "REQ_DOC")
    private byte[] reqDoc;
    @Lob
    @Column(name = "REQ_APPROVED_DOC")
    private byte[] reqApprovedDoc;
    @Lob
    @Column(name = "REQ_COL_PAPERS")
    private byte[] reqColPapers;
    @Lob
    @Column(name = "REQ_PAID_BY_CLIENT")
    private byte[] reqPaidByClient;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REQ_TO_BE_SCANNED")
    private boolean reqToBeScanned;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REQ_TO_BE_COLLECTED")
    private boolean reqToBeCollected;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REQ_CLIENT_GOV_ID")
    private int reqClientGovId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "historyNotes")
    private String historyNotes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REQ_Date")
    @Temporal(TemporalType.DATE)
    private Date rEQDate;
    @JoinColumn(name = "REQ_USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private User reqUserId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "REQ_ID")
    private Integer reqId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REQ_NUMBER")
    private int reqNumber;
    @JoinColumn(name = "REQ_STATUS_ID", referencedColumnName = "STATUS_ID")
    @ManyToOne(optional = false)
    private Status reqStatusId;
    @JoinColumn(name = "REQ_CLIENT_ID", referencedColumnName = "CLIENT_ID")
    @ManyToOne(optional = false)
    private Client reqClientId;

    public Request() {
    }

    public Request(Integer reqId) {
        this.reqId = reqId;
    }

    public Request(Integer reqId, int reqNumber, byte[] reqDoc) {
        this.reqId = reqId;
        this.reqNumber = reqNumber;
        this.reqDoc = reqDoc;
    }

    public Integer getReqId() {
        return reqId;
    }

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public int getReqNumber() {
        return reqNumber;
    }

    public void setReqNumber(int reqNumber) {
        this.reqNumber = reqNumber;
    }

    public byte[] getReqDoc() {
        return reqDoc;
    }

    public void setReqDoc(byte[] reqDoc) {
        this.reqDoc = reqDoc;
    }

    public byte[] getReqApprovedDoc() {
        return reqApprovedDoc;
    }

    public void setReqApprovedDoc(byte[] reqApprovedDoc) {
        this.reqApprovedDoc = reqApprovedDoc;
    }

    public Status getReqStatusId() {
        return reqStatusId;
    }

    public void setReqStatusId(Status reqStatusId) {
        this.reqStatusId = reqStatusId;
    }

    public Client getReqClientId() {
        return reqClientId;
    }

    public void setReqClientId(Client reqClientId) {
        this.reqClientId = reqClientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reqId != null ? reqId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Request)) {
            return false;
        }
        Request other = (Request) object;
        if ((this.reqId == null && other.reqId != null) || (this.reqId != null && !this.reqId.equals(other.reqId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "loan.system.entities.Request[ reqId=" + reqId + " ]";
    }

    public User getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(User reqUserId) {
        this.reqUserId = reqUserId;
    }

    public Date getREQDate() {
        return rEQDate;
    }

    public void setREQDate(Date rEQDate) {
        this.rEQDate = rEQDate;
    }

    public String getHistoryNotes() {
        return historyNotes;
    }

    public void setHistoryNotes(String historyNotes) {
        this.historyNotes = historyNotes;
    }


    public int getReqClientGovId() {
        return reqClientGovId;
    }

    public void setReqClientGovId(int reqClientGovId) {
        this.reqClientGovId = reqClientGovId;
    }

    public byte[] getReqColPapers() {
        return reqColPapers;
    }

    public void setReqColPapers(byte[] reqColPapers) {
        this.reqColPapers = reqColPapers;
    }

    public byte[] getReqPaidByClient() {
        return reqPaidByClient;
    }

    public void setReqPaidByClient(byte[] reqPaidByClient) {
        this.reqPaidByClient = reqPaidByClient;
    }


    public boolean getReqToBeCollected() {
        return reqToBeCollected;
    }

    public void setReqToBeCollected(boolean reqToBeCollected) {
        this.reqToBeCollected = reqToBeCollected;
    }

    

    public boolean getReqToBeScanned() {
        return reqToBeScanned;
    }

    public void setReqToBeScanned(boolean reqToBeScanned) {
        this.reqToBeScanned = reqToBeScanned;
    }

    
}
