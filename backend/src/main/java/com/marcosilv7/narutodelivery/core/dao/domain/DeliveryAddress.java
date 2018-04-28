package com.marcosilv7.narutodelivery.core.dao.domain;

import com.marcosilv7.narutodelivery.security.dao.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "delivery_address")
public class DeliveryAddress extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column
    @NotNull
    private String address;
    @Column
    @NotNull
    private String reference;
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @Column
    private Boolean favorite;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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
}
