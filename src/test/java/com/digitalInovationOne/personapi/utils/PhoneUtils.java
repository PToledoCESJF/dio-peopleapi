package com.digitalInovationOne.personapi.utils;

import com.digitalInovationOne.personapi.dto.request.PhoneDTO;
import com.digitalInovationOne.personapi.entity.Phone;
import com.digitalInovationOne.personapi.enums.PhoneType;

public class PhoneUtils {

    private static final String PHONE_NUMBER = "3298765-4321";
    private static final PhoneType PHONE_TYPE = PhoneType.MOBILE;
    private static final long PHONE_ID = 1L;

    public static Phone createFakeEntity() {
        return Phone.builder()
                .id(PHONE_ID)
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }

    public static PhoneDTO createFakeDTO() {
        return PhoneDTO.builder()
                .id(PHONE_ID)
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }

}
