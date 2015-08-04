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
@Table(name = "RANKS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ranks.findAll", query = "SELECT r FROM Ranks r"),
    @NamedQuery(name = "Ranks.findByRankId", query = "SELECT r FROM Ranks r WHERE r.rankId = :rankId"),
    @NamedQuery(name = "Ranks.findByRankTitle", query = "SELECT r FROM Ranks r WHERE r.rankTitle = :rankTitle"),
    @NamedQuery(name = "Ranks.findByRankRetirmentAgePilot", query = "SELECT r FROM Ranks r WHERE r.rankRetirmentAgePilot = :rankRetirmentAgePilot"),
    @NamedQuery(name = "Ranks.findByRankRetirmentAge", query = "SELECT r FROM Ranks r WHERE r.rankRetirmentAge = :rankRetirmentAge")})
public class Ranks implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RANK_ID")
    private Integer rankId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "RANK_TITLE")
    private String rankTitle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RANK_RETIRMENT_AGE_PILOT")
    private int rankRetirmentAgePilot;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RANK_RETIRMENT_AGE")
    private int rankRetirmentAge;

    public Ranks() {
    }

    public Ranks(Integer rankId) {
        this.rankId = rankId;
    }

    public Ranks(Integer rankId, String rankTitle, int rankRetirmentAgePilot, int rankRetirmentAge) {
        this.rankId = rankId;
        this.rankTitle = rankTitle;
        this.rankRetirmentAgePilot = rankRetirmentAgePilot;
        this.rankRetirmentAge = rankRetirmentAge;
    }

    public Integer getRankId() {
        return rankId;
    }

    public void setRankId(Integer rankId) {
        this.rankId = rankId;
    }

    public String getRankTitle() {
        return rankTitle;
    }

    public void setRankTitle(String rankTitle) {
        this.rankTitle = rankTitle;
    }

    public int getRankRetirmentAgePilot() {
        return rankRetirmentAgePilot;
    }

    public void setRankRetirmentAgePilot(int rankRetirmentAgePilot) {
        this.rankRetirmentAgePilot = rankRetirmentAgePilot;
    }

    public int getRankRetirmentAge() {
        return rankRetirmentAge;
    }

    public void setRankRetirmentAge(int rankRetirmentAge) {
        this.rankRetirmentAge = rankRetirmentAge;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rankId != null ? rankId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ranks)) {
            return false;
        }
        Ranks other = (Ranks) object;
        if ((this.rankId == null && other.rankId != null) || (this.rankId != null && !this.rankId.equals(other.rankId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "loan.system.entities.Ranks[ rankId=" + rankId + " ]";
    }
    
}
