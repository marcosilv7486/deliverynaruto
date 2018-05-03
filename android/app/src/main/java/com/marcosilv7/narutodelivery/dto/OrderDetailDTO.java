package com.marcosilv7.narutodelivery.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class OrderDetailDTO implements Parcelable {

    private Long id;
    private int item;
    private String description;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private String descriptionImage;
    private Long productId;
    private Long orderId;

    public OrderDetailDTO(){}

    protected OrderDetailDTO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        item = in.readInt();
        description = in.readString();
        quantity = in.readInt();
        descriptionImage = in.readString();
        if (in.readByte() == 0) {
            productId = null;
        } else {
            productId = in.readLong();
        }
        if (in.readByte() == 0) {
            orderId = null;
        } else {
            orderId = in.readLong();
        }
    }

    public static final Creator<OrderDetailDTO> CREATOR = new Creator<OrderDetailDTO>() {
        @Override
        public OrderDetailDTO createFromParcel(Parcel in) {
            return new OrderDetailDTO(in);
        }

        @Override
        public OrderDetailDTO[] newArray(int size) {
            return new OrderDetailDTO[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getDescriptionImage() {
        return descriptionImage;
    }

    public void setDescriptionImage(String descriptionImage) {
        this.descriptionImage = descriptionImage;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
        dest.writeInt(item);
        dest.writeString(description);
        dest.writeInt(quantity);
        dest.writeString(descriptionImage);
        if (productId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(productId);
        }
        if (orderId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(orderId);
        }
    }
}
