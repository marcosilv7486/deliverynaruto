package com.marcosilv7.narutodelivery.core.dao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product_subfamily")
public class ProductSubFamily extends GenericEntity {

    @ManyToOne
    private ProductFamily family;

    @Column
    @NotNull
    private String name;

    public ProductFamily getFamily() {
        return family;
    }

    public void setFamily(ProductFamily family) {
        this.family = family;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
