package com.marcosilv7.narutodelivery.core.dao.domain;

import com.marcosilv7.narutodelivery.security.dao.domain.User;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "payment_method")
@Where(clause = "deletedAt is null")
public class PaymentMethod extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column
    @NotNull
    private String numberCreditCard;
    @Column
    @NotNull
    private int monthExp;
    @Column
    @NotNull
    private int yearExp;
    @Column
    @NotNull
    @Length(min = 3,max = 3)
    private String cvv;
    @Column
    private Boolean favorite;
    @Column
    private String postalCode;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNumberCreditCard() {
        return numberCreditCard;
    }

    public void setNumberCreditCard(String numberCreditCard) {
        this.numberCreditCard = numberCreditCard;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
