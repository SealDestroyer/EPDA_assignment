package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(
            name = "MyUsers.findLecturers",
            query = "SELECT u FROM MyUsers u WHERE u.userID LIKE 'L%' ORDER BY u.userID"
    )
    ,
        @NamedQuery(
            name = "MyUsers.findByUserIds",
            query = "SELECT u FROM MyUsers u WHERE u.userID IN :ids"
    )
    ,
        @NamedQuery(
            name = "MyUsers.findAllUsers",
            query = "SELECT u FROM MyUsers u"
    )
    ,
    @NamedQuery(
            name = "MyUsers.findAllStudents",
            query = "SELECT u FROM MyUsers u WHERE u.userID LIKE 'S%' ORDER BY u.userID"
    )
        ,
        @NamedQuery(
            name = "MyUsers.findAcamdemicLeaders",
            query = "SELECT u FROM MyUsers u WHERE u.userID LIKE 'AL%' ORDER BY u.userID"
    )
        ,
        @NamedQuery(
            name = "MyUsers.deleteByUserId",
            query = "DELETE FROM MyUsers u WHERE u.userID = :userID"
    )
    
})

public class MyUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String userID;     
    private String fullName;
    private String password;
    private String gender;
    private String phone;
    private String icNumber;
    private String email;
    private String address;

    public MyUsers() {
    }

    public MyUsers(String userID, String fullName, String password, String gender,
            String phone, String icNumber, String email, String address) {
        this.userID = userID;
        this.fullName = fullName;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.icNumber = icNumber;
        this.email = email;
        this.address = address;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIcNumber() {
        return icNumber;
    }

    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MyUsers)) {
            return false;
        }
        MyUsers other = (MyUsers) object;
        if ((this.userID == null && other.userID != null)
                || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MyUsers[ userID=" + userID + " ]";
    }
}
