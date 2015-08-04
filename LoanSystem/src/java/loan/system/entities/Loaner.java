/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author me
 */
@Entity
@Table(name = "LOANER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name ="Loaner.paidForClient", query = "SELECT l FROM Loaner l WHERE l.financailId.clientId.clientNumber = :clientNumber AND l.paid = false"),
    @NamedQuery(name = "Loaner.findByClientNumber", query= "SELECT l FROM Loaner l WHERE l.financailId.clientId.clientNumber= :clientNumber "),
    @NamedQuery(name = "Loaner.findAll", query = "SELECT l FROM Loaner l"),
    @NamedQuery(name = "Loaner.findByLoanerId", query = "SELECT l FROM Loaner l WHERE l.loanerId = :loanerId"),
    @NamedQuery(name = "Loaner.findByLoanerName", query = "SELECT l FROM Loaner l WHERE l.loanerName = :loanerName"),
    @NamedQuery(name = "Loaner.findByAmount", query = "SELECT l FROM Loaner l WHERE l.amount = :amount"),
    @NamedQuery(name = "Loaner.findByDuration", query = "SELECT l FROM Loaner l WHERE l.duration = :duration"),
    @NamedQuery(name = "Loaner.findByRemaining", query = "SELECT l FROM Loaner l WHERE l.remaining = :remaining"),
    @NamedQuery(name = "Loaner.findByInstallment", query = "SELECT l FROM Loaner l WHERE l.installment = :installment")})
public class Loaner implements Serializable {
    @Basic(optional = false)
    @NotNull()
    @Column(name = "AMOUNT")
    private float amount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REMAINING")
    private float remaining;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INSTALLMENT")
    private float installment;
    @Lob
    @Column(name = "PAID_PAPER")
    private byte[] paidPaper;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAID")
    private boolean paid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isEditable")
    private boolean isEditable;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LOANER_ID")
    private Integer loanerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "LOANER_NAME")
    private String loanerName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DURATION")
    private int duration;
    @JoinColumn(name = "FINANCAIL_ID", referencedColumnName = "FINANCAIL_ID")
    @ManyToOne(optional = false)
    private FinancailInfo financailId;

    public Loaner() {
    }

    public Loaner(Integer loanerId) {
        this.loanerId = loanerId;
    }

    public Loaner(Integer loanerId, String loanerName, int amount, int duration, int remaining, int installment) {
        this.loanerId = loanerId;
        this.loanerName = loanerName;
        this.amount = amount;
        this.duration = duration;
        this.remaining = remaining;
        this.installment = installment;
    }

    public Integer getLoanerId() {
        return loanerId;
    }

    public void setLoanerId(Integer loanerId) {
        this.loanerId = loanerId;
    }

    public String getLoanerName() {
        return loanerName;
    }

    public void setLoanerName(String loanerName) {
        this.loanerName = loanerName;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public FinancailInfo getFinancailId() {
        return financailId;
    }

    public void setFinancailId(FinancailInfo financailId) {
        this.financailId = financailId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loanerId != null ? loanerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loaner)) {
            return false;
        }
        Loaner other = (Loaner) object;
        if ((this.loanerId == null && other.loanerId != null) || (this.loanerId != null && !this.loanerId.equals(other.loanerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "loan.system.entities.Loaner[ loanerId=" + loanerId + " ]";
    }

    public boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public byte[] getPaidPaper() {
        return paidPaper;
    }

    public void setPaidPaper(byte[] paidPaper) {
        this.paidPaper = paidPaper;
    }
    
    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getRemaining() {
        return remaining;
    }

    public void setRemaining(float remaining) {
        this.remaining = remaining;
    }

    public float getInstallment() {
        return installment;
    }

    public void setInstallment(float installment) {
        this.installment = installment;
    }
    
}
