package com.marcosilv7.narutodelivery.core.dao.domain;

import com.marcosilv7.narutodelivery.security.dao.domain.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends GenericEntity {

    @ManyToOne
    private User user;
    @Column
    @NotNull
    private String userFullName;
    @Column
    @NotNull
    private String number;
    @Column
    @NotNull
    private String userPhone;
    @Column
    @NotNull
    private String userAddress;
    @Column
    @NotNull
    private Double latUserAddress;
    @Column
    @NotNull
    private Double lonUserAddress;
    @Column
    @NotNull
    private String paymentType;
    @Column
    private String numberCreditCard;
    @Column
    private String observations;
    @Column
    @NotNull
    private BigDecimal total;
    @Column
    @NotNull
    private String status;
    @Column
    private LocalDateTime shippingDate;
    @Column
    private LocalDateTime arrivalDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Double getLatUserAddress() {
        return latUserAddress;
    }

    public void setLatUserAddress(Double latUserAddress) {
        this.latUserAddress = latUserAddress;
    }

    public Double getLonUserAddress() {
        return lonUserAddress;
    }

    public void setLonUserAddress(Double lonUserAddress) {
        this.lonUserAddress = lonUserAddress;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getNumberCreditCard() {
        return numberCreditCard;
    }

    public void setNumberCreditCard(String numberCreditCard) {
        this.numberCreditCard = numberCreditCard;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDateTime shippingDate) {
        this.shippingDate = shippingDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
