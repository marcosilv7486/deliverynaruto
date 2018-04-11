package com.marcosilv7.narutodelivery.core.dao.domain;

import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Where(clause = "deletedAt is null")
public class Product extends GenericEntity{

    @Column
    @NotNull
    private String name;

    @Column
    private String description;

    @Column
    @NotNull
    @Min(0)
    private BigDecimal price;

    @ManyToOne
    private ProductSubFamily subFamily;

    @Column
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProductSubFamily getSubFamily() {
        return subFamily;
    }

    public void setSubFamily(ProductSubFamily subFamily) {
        this.subFamily = subFamily;
    }
}
