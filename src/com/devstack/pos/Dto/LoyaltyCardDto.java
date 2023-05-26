package com.devstack.pos.Dto;

import com.devstack.pos.entity.SuperEntity;
import com.devstack.pos.enums.CardType;

public class LoyaltyCardDto implements SuperEntity {
    private int code;
    private CardType cardType;
    private String barCode;
    private String email;

    public LoyaltyCardDto() {
    }

    public LoyaltyCardDto(int code, CardType cardType, String barCode, String email) {
        this.code = code;
        this.cardType = cardType;
        this.barCode = barCode;
        this.email = email;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "LoyaltyCardDao{" +
                "code=" + code +
                ", cardType=" + cardType +
                ", barCode='" + barCode + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
