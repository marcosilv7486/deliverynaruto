package com.marcosilv7.narutodelivery.core.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderDetailDTO {

    private Long id;
    private int item;
    private String description;
    @NotNull
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private String descriptionImage;
    @NotNull
    private Long productId;
    private Long orderId;

    public OrderDetailDTO() {}

    public OrderDetailDTO(Long id,
                          int item,
                          String description,
                          @NotNull int quantity,
                          BigDecimal unitPrice,
                          BigDecimal total,
                          String descriptionImage,
                          @NotNull Long productId,
                          Long orderId) {
        this.id = id;
        this.item = item;
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.descriptionImage = descriptionImage;
        this.productId = productId;
        this.orderId = orderId;
    }

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
}
