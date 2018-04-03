package com.marcosilv7.narutodelivery.core.dao.domain;

import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "categories_product")
@Where(clause = "deletedAt is null")
public class CategoryProduct extends GenericEntity{

    @Column
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
