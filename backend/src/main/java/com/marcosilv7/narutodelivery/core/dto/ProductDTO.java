package com.marcosilv7.narutodelivery.core.dto;

import java.math.BigDecimal;

public class ProductDTO {

    private Long id;
    private String name;
    private String descriptions;
    private String image;
    private BigDecimal price;
    private String family;
    private String subFamily;

    public ProductDTO(Long id,
                      String name,
                      String descriptions,
                      String image,
                      BigDecimal price,
                      String family,
                      String subFamily) {
        this.id = id;
        this.name = name;
        this.descriptions = descriptions;
        this.image = image;
        this.price = price;
        this.family = family;
        this.subFamily = subFamily;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getSubFamily() {
        return subFamily;
    }

    public void setSubFamily(String subFamily) {
        this.subFamily = subFamily;
    }
}
