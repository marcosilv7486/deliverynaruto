package com.marcosilv7.narutodelivery.core.dto;

public class DeliveryAddressDTO {

    private Long id;
    private Long userId;
    private String address;
    private String alias;
    private Double latitude;
    private Double longitude;
    private Boolean favorite;
    private String phone;

    public DeliveryAddressDTO(){}

    public DeliveryAddressDTO(Long id,
                              Long userId,
                              String address,
                              String alias,
                              Double latitude,
                              Double longitude,
                              Boolean favorite,
                              String phone) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.alias = alias;
        this.latitude = latitude;
        this.longitude = longitude;
        this.favorite = favorite;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
