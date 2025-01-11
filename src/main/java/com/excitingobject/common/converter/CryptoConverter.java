package com.excitingobject.common.converter;

import com.excitingobject.common.utils.CryptoUtils;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import org.springframework.beans.factory.annotation.Value;

@Convert
public class CryptoConverter implements AttributeConverter<String, String> {

    @Value("${encrypt.key}")
    private String encryptKey;

    @Override
    public String convertToDatabaseColumn(String encryptText) {
        try {
            return CryptoUtils.encryptAES256(encryptText, encryptKey);
        } catch (Exception e) {
            return encryptText;
        }
    }

    @Override
    public String convertToEntityAttribute(String decryptText) {
        try {
            return CryptoUtils.decryptAES256(decryptText, encryptKey);
        } catch (Exception e) {
            return decryptText;
        }
    }

}
