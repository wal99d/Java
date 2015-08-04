/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author me
 */
@Entity
@Table(name = "USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findByRoleID", query = "SELECT u FROM User u WHERE u.userRoleId.roleId = :roleId"),
    @NamedQuery(name = "User.getUserByNamePasswordAndRoleID" , query="select u FROM User u where u.userName = :userName and u.userPassword = :userPassword and u.userRoleId.roleId = :roleId"),
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId"),
    @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
    @NamedQuery(name = "User.findByUserPassword", query = "SELECT u FROM User u WHERE u.userPassword = :userPassword"),
    @NamedQuery(name = "User.findByCannotRemove", query = "SELECT u FROM User u WHERE u.cannotRemove = :cannotRemove"),
    @NamedQuery(name = "User.findByLastLogin", query = "SELECT u FROM User u WHERE u.lastLogin = :lastLogin"),
    @NamedQuery(name = "User.findByLastPasswordUpdate", query = "SELECT u FROM User u WHERE u.lastPasswordUpdate = :lastPasswordUpdate"),
    @NamedQuery(name = "User.findByUserForgot", query = "SELECT u FROM User u WHERE u.userForgot = :userForgot")})
public class User implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reqUserId")
    private Collection<Request> requestCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_ID")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "USER_NAME")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "USER_PASSWORD")
    private String userPassword;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cannotRemove")
    private boolean cannotRemove;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lastLogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lastPasswordUpdate")
    @Temporal(TemporalType.DATE)
    private Date lastPasswordUpdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "userForgot")
    private boolean userForgot;
    @JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "ROLE_ID")
    @ManyToOne(optional = false)
    private Role userRoleId;
    @JoinColumn(name = "USER_DEPT_ID", referencedColumnName = "DEPT_ID")
    @ManyToOne(optional = false)
    private Departement userDeptId;

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(Integer userId, String userName, String userPassword, boolean cannotRemove, Date lastLogin, Date lastPasswordUpdate, boolean userForgot) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.cannotRemove = cannotRemove;
        this.lastLogin = lastLogin;
        this.lastPasswordUpdate = lastPasswordUpdate;
        this.userForgot = userForgot;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean getCannotRemove() {
        return cannotRemove;
    }

    public void setCannotRemove(boolean cannotRemove) {
        this.cannotRemove = cannotRemove;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastPasswordUpdate() {
        return lastPasswordUpdate;
    }

    public void setLastPasswordUpdate(Date lastPasswordUpdate) {
        this.lastPasswordUpdate = lastPasswordUpdate;
    }

    public boolean getUserForgot() {
        return userForgot;
    }

    public void setUserForgot(boolean userForgot) {
        this.userForgot = userForgot;
    }

    public Role getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Role userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Departement getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(Departement userDeptId) {
        this.userDeptId = userDeptId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "loan.system.entities.User[ userId=" + userId + " ]";
    }

    @XmlTransient
    public Collection<Request> getRequestCollection() {
        return requestCollection;
    }

    public void setRequestCollection(Collection<Request> requestCollection) {
        this.requestCollection = requestCollection;
    }
    
}
