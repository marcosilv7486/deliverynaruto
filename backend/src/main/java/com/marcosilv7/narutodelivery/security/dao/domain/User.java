package com.marcosilv7.narutodelivery.security.dao.domain;

import com.marcosilv7.narutodelivery.core.dao.domain.GenericEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Where(clause = "deletedAt is null")
public class User extends GenericEntity {

    @Column
    @NotNull
    private String userName;
    @Column
    @NotNull
    private String fullName;
    @Column
    private Date lastLogin;
    @Column
    private boolean enabled;
    @Column
    private String avatar;
    @Column
    @NotNull
    private String password;
    @Column
    private Date bithDay;
    @Column
    private String phone;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<UserScope> scopes;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<UserScope> getScopes() {
        return scopes;
    }

    public void setScopes(List<UserScope> scopes) {
        this.scopes = scopes;
    }

    public Date getBithDay() {
        return bithDay;
    }

    public void setBithDay(Date bithDay) {
        this.bithDay = bithDay;
    }
}
