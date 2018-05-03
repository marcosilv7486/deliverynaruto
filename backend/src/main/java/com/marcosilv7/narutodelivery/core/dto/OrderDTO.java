package com.marcosilv7.narutodelivery.core.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDTO {

    private Long id;
    @NotNull
    private Long userId;
    private String number;
    private String userFullName;
    private String userPhone;
    @NotNull
    private String userAddress;
    @NotNull
    private Double latUserAddress;
    @NotNull
    private Double lonUserAddress;
    @NotNull
    private String paymentType;
    private String numberCreditCard;
    @NotNull
    private String invoiceType;
    private String rucNumber;
    private String razonSocial;
    private String nombreComercial;
    private String domicilioFiscal;
    private BigDecimal total;
    private String status;
    private Date shippingDate;
    private Date arrivalDate;

    private List<OrderDetailDTO> items;

    public OrderDTO(){}

    public OrderDTO(Long id, @NotNull Long userId,
                    String number,
                    String userFullName,
                    String userPhone,
                    @NotNull String userAddress,
                    @NotNull Double latUserAddress,
                    @NotNull Double lonUserAddress,
                    @NotNull String paymentType,
                    String numberCreditCard,
                    @NotNull String invoiceType,
                    String rucNumber,
                    String razonSocial,
                    String nombreComercial,
                    String domicilioFiscal,
                    BigDecimal total,
                    String status,
                    Date shippingDate,
                    Date arrivalDate) {
        this.id = id;
        this.userId = userId;
        this.number = number;
        this.userFullName = userFullName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.latUserAddress = latUserAddress;
        this.lonUserAddress = lonUserAddress;
        this.paymentType = paymentType;
        this.numberCreditCard = numberCreditCard;
        this.invoiceType = invoiceType;
        this.rucNumber = rucNumber;
        this.razonSocial = razonSocial;
        this.nombreComercial = nombreComercial;
        this.domicilioFiscal = domicilioFiscal;
        this.total = total;
        this.status = status;
        this.shippingDate = shippingDate;
        this.arrivalDate = arrivalDate;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
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

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public List<OrderDetailDTO> getItems() {
        if(items == null)
            items = new ArrayList<>();
        return items;
    }

    public void setItems(List<OrderDetailDTO> items) {
        this.items = items;
    }
}
