package com.marcosilv7.narutodelivery.security.dao.domain;

import com.marcosilv7.narutodelivery.core.dao.domain.GenericEntity;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "scopes")
@Where(clause = "deletedAt is null")
public class Scope extends GenericEntity {

    @Column
    @NotNull
    private String name;
    @Column
    @NotNull
    private String nameSpring;

    @Column
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSpring() {
        return nameSpring;
    }

    public void setNameSpring(String nameSpring) {
        this.nameSpring = nameSpring;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
