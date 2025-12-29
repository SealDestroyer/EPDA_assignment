/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author bohch
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "MyUserID.count", query = "SELECT COUNT(u) FROM MyUserID u"),
    @NamedQuery(name = "MyUserID.findByAD", query = "SELECT u FROM MyUserID u WHERE u.currentUserId LIKE 'AD%'"),
    @NamedQuery(name = "MyUserID.findByAL", query = "SELECT u FROM MyUserID u WHERE u.currentUserId LIKE 'AL%'"),
    @NamedQuery(name = "MyUserID.findByL", query = "SELECT u FROM MyUserID u WHERE u.currentUserId LIKE 'L%'"),
    @NamedQuery(name = "MyUserID.findByS", query = "SELECT u FROM MyUserID u WHERE u.currentUserId LIKE 'S%'")
})
public class MyUserID implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String currentUserId;
    private String userType;

    public MyUserID() {
    }

    public MyUserID(String currentUserId, String userType) {
        this.currentUserId = currentUserId;
        this.userType = userType;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MyUserID)) {
            return false;
        }
        MyUserID other = (MyUserID) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MyUserID[ id=" + id + " ]";
    }
    
}
