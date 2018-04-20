package com.marcosilv7.narutodelivery.core.dao.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product_family")
public class ProductFamily extends GenericEntity {

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
