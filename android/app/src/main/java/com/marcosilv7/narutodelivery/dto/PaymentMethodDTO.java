package com.marcosilv7.narutodelivery.dto;

public class PaymentMethodDTO {
    private Long id;
    private Long userId;
    private String numberCreditCard;
    private int monthExp;
    private int yearExp;
    private String cvv;
    private Boolean favorite;
    private String postalCode;

    public PaymentMethodDTO(){}

    public PaymentMethodDTO(Long id, Long userId,
                            String numberCreditCard, int monthExp,
                            int yearExp, String cvv, Boolean favorite,
                            String postalCode) {
        this.id = id;
        this.userId = userId;
        this.numberCreditCard = numberCreditCard;
        this.monthExp = monthExp;
        this.yearExp = yearExp;
        this.cvv = cvv;
        this.favorite = favorite;
        this.postalCode = postalCode;
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

    public String getNumberCreditCard() {
        return numberCreditCard;
    }

    public void setNumberCreditCard(String numberCreditCard) {
        this.numberCreditCard = numberCreditCard;
    }

    public int getMonthExp() {
        return monthExp;
    }

    public void setMonthExp(int monthExp) {
        this.monthExp = monthExp;
    }

    public int getYearExp() {
        return yearExp;
    }

    public void setYearExp(int yearExp) {
        this.yearExp = yearExp;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
