package com.marcosilv7.narutodelivery.core.dao.domain;

import com.marcosilv7.narutodelivery.security.dao.domain.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "payment_method")
public class PaymentMethod extends GenericEntity {

    @ManyToOne
    private User user;
    @Column
    @NotNull
    private String numberCreditCard;

    @Column
    @NotNull
    @Length(min = 2,max = 2)
    private String monthExp;
    @Column
    @NotNull
    @Length(min = 2,max = 2)
    private String yearExp;
    @Column
    @NotNull
    @Length(min = 3,max = 3)
    private String cvs;
    @Column
    private Boolean favorite;

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

    public String getMonthExp() {
        return monthExp;
    }

    public void setMonthExp(String monthExp) {
        this.monthExp = monthExp;
    }

    public String getYearExp() {
        return yearExp;
    }

    public void setYearExp(String yearExp) {
        this.yearExp = yearExp;
    }

    public String getCvs() {
        return cvs;
    }

    public void setCvs(String cvs) {
        this.cvs = cvs;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
