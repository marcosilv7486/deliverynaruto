package com.marcosilv7.narutodelivery.security.dao.domain;

import com.marcosilv7.narutodelivery.core.dao.domain.GenericEntity;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_scopes")
@Where(clause = "deletedAt is null")
public class UserScope extends GenericEntity{

    @ManyToOne
    private Scope scope;
    @ManyToOne
    private User user;

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
