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
    @NamedQuery(name = "MyAdmin.findAll", query = "SELECT m FROM MyAdmin m WHERE m.userId != :userId"),
    @NamedQuery(name = "MyAdmin.deleteByUserId", query = "DELETE FROM MyAdmin m WHERE m.userId = :userId"),
    @NamedQuery(name = "MyAdmin.findByUserId", query = "SELECT m FROM MyAdmin m WHERE m.userId = :userId")
    })
public class MyAdmin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;
    private String positionTitle;

    public MyAdmin() {
    }

    public MyAdmin(String userId, String positionTitle) {
        this.userId = userId;
        this.positionTitle = positionTitle;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the userId fields are not set
        if (!(object instanceof MyAdmin)) {
            return false;
        }
        MyAdmin other = (MyAdmin) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MyAdmin[ userId=" + userId + " ]";
    }

}
