package com.bank.card.converter;

import jakarta.persistence.AttributeConverter;

public class CardNumberEncryptor implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String s) {
        return "";
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return "";
    }
}
