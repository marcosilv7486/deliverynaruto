package com.marcosilv7.narutodelivery.dto;

public class ProductFamilyDTO {
    private Long id;
    private String name;
    private String image;
    private Long countProducts;

    public ProductFamilyDTO(){}

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getCountProducts() {
        return countProducts;
    }

    public void setCountProducts(Long countProducts) {
        this.countProducts = countProducts;
    }
}
