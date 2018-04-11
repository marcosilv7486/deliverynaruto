package com.marcosilv7.narutodelivery.core.dto;

public class ProductSubFamilyDTO {

    private Long id;
    private String name;
    private String family;

    public ProductSubFamilyDTO(){}

    public ProductSubFamilyDTO(Long id, String name, String family) {
        this.id = id;
        this.name = name;
        this.family = family;
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

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
