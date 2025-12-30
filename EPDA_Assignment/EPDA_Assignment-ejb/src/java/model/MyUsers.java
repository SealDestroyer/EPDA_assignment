package model;

import java.io.Serializable;
import java.sql.Timestamp;
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
            name = "MyUsers.findAllLecturersWithDateRange",
            query = "SELECT COUNT(u) FROM MyUsers u WHERE u.userID LIKE 'L%' AND u.registrationDateTime BETWEEN :startDate AND :endDate"
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
    ),
    @NamedQuery(
            name = "MyUsers.findAllStudents",
            query = "SELECT u FROM MyUsers u WHERE u.userID LIKE 'S%' ORDER BY u.userID"
    )
        ,
        @NamedQuery(
            name = "MyUsers.findAllStudentsWithDateRange",
            query = "SELECT COUNT(u) FROM MyUsers u WHERE u.userID LIKE 'S%' AND u.registrationDateTime BETWEEN :startDate AND :endDate"
    )
        ,
        @NamedQuery(
            name = "MyUsers.findAcamdemicLeaders",
            query = "SELECT u FROM MyUsers u WHERE u.userID LIKE 'AL%' ORDER BY u.userID"
    )
        ,
        @NamedQuery(
            name = "MyUsers.findAllAcademicsLeaderWithDateRange",
            query = "SELECT COUNT(u) FROM MyUsers u WHERE u.userID LIKE 'AL%' AND u.registrationDateTime BETWEEN :startDate AND :endDate"
    ),
        @NamedQuery(
            name = "MyUsers.deleteByUserId",
            query = "DELETE FROM MyUsers u WHERE u.userID = :userID"
    )
        ,
        @NamedQuery(
            name = "MyUsers.findUserIdByEmailAndPassword",
            query = "SELECT u.userID FROM MyUsers u WHERE u.email = :userEmail AND u.password = :userPassword"
    )
        ,
        @NamedQuery(
            name = "MyUsers.findAdminsExcludingUser",
            query = "SELECT u FROM MyUsers u WHERE u.userID LIKE 'AD%' AND u.userID != :userID ORDER BY u.userID"
    )
        ,
        @NamedQuery(
            name = "MyUsers.findByEmailAndSecretKey",
            query = "SELECT u FROM MyUsers u WHERE u.email = :email AND u.secretKey = :secretKey"
    )
        ,
        @NamedQuery(
            name = "MyUsers.updatePasswordAndSecretKeyByEmail",
            query = "UPDATE MyUsers u SET u.password = :password, u.secretKey = :secretKey WHERE u.email = :email"
    )
        ,
        @NamedQuery(
            name = "MyUsers.findByEmail",
            query = "SELECT u FROM MyUsers u WHERE u.email = :email"
    )
        ,
        @NamedQuery(
            name = "MyUsers.findAllAdminsWithDateRange",
            query = "SELECT COUNT(u) FROM MyUsers u WHERE u.userID LIKE 'AD%' AND u.registrationDateTime BETWEEN :startDate AND :endDate"
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
    private Integer secretKey;
    private Timestamp registrationDateTime;

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
        this.secretKey = (int) (Math.random() * 900000) + 100000;
        this.registrationDateTime = new Timestamp(System.currentTimeMillis());
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

    public Integer getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Integer secretKey) {
        this.secretKey = secretKey;
    }

    public Timestamp getRegistrationDateTime() {
        return registrationDateTime;
    }

    public void setRegistrationDateTime(Timestamp registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
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
