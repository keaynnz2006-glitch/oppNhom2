package quanlybaixe.entity;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;
    private String role;

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password, String role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    // --- GETTER & SETTER CHUẨN ---
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // --- ALIAS METHODS (Tránh lỗi getUsername() và ép kiểu Object) ---
    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }
}