package com.marcosilv7.narutodelivery.core.dao.domain;

import com.marcosilv7.narutodelivery.security.dao.domain.User;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Where(clause = "deletedAt is null")
public class Order extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column
    @NotNull
    private String number;
    @Column
    @NotNull
    private String userFullName;
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
    @NotNull
    private String invoiceType;
    @Column
    private String rucNumber;
    @Column
    private String razonSocial;
    @Column
    private String nombreComercial;
    @Column
    private String domicilioFiscal;
    @Column
    @NotNull
    private BigDecimal total;
    @Column
    @NotNull
    private String status;
    @Column
    private Date shippingDate;
    @Column
    private Date arrivalDate;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order",cascade = {CascadeType.PERSIST})
    private List<OrderDetail> details;

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

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getRucNumber() {
        return rucNumber;
    }

    public void setRucNumber(String rucNumber) {
        this.rucNumber = rucNumber;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDomicilioFiscal() {
        return domicilioFiscal;
    }

    public void setDomicilioFiscal(String domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public List<OrderDetail> getDetails() {
        if(details == null)
            details = new ArrayList<>();
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }
}
