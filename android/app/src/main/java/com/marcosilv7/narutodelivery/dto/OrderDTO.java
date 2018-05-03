package com.marcosilv7.narutodelivery.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDTO implements Parcelable {

    private Long id;
    private Long userId;
    private String number;
    private String userFullName;
    private String userPhone;
    private String userAddress;
    private Double latUserAddress;
    private Double lonUserAddress;
    private String paymentType;
    private String numberCreditCard;
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

    protected OrderDTO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readLong();
        }
        number = in.readString();
        userFullName = in.readString();
        userPhone = in.readString();
        userAddress = in.readString();
        if (in.readByte() == 0) {
            latUserAddress = null;
        } else {
            latUserAddress = in.readDouble();
        }
        if (in.readByte() == 0) {
            lonUserAddress = null;
        } else {
            lonUserAddress = in.readDouble();
        }
        paymentType = in.readString();
        numberCreditCard = in.readString();
        invoiceType = in.readString();
        rucNumber = in.readString();
        razonSocial = in.readString();
        nombreComercial = in.readString();
        domicilioFiscal = in.readString();
        status = in.readString();
    }

    public static final Creator<OrderDTO> CREATOR = new Creator<OrderDTO>() {
        @Override
        public OrderDTO createFromParcel(Parcel in) {
            return new OrderDTO(in);
        }

        @Override
        public OrderDTO[] newArray(int size) {
            return new OrderDTO[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        if (userId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(userId);
        }
        dest.writeString(number);
        dest.writeString(userFullName);
        dest.writeString(userPhone);
        dest.writeString(userAddress);
        if (latUserAddress == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latUserAddress);
        }
        if (lonUserAddress == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lonUserAddress);
        }
        dest.writeString(paymentType);
        dest.writeString(numberCreditCard);
        dest.writeString(invoiceType);
        dest.writeString(rucNumber);
        dest.writeString(razonSocial);
        dest.writeString(nombreComercial);
        dest.writeString(domicilioFiscal);
        dest.writeString(status);
    }
}
