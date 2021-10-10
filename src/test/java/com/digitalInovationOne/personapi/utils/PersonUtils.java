package com.digitalInovationOne.personapi.utils;

import com.digitalInovationOne.personapi.dto.request.PersonDTO;
import com.digitalInovationOne.personapi.entity.Person;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class PersonUtils {

    private static final String FIRST_NAME = "Paulo";
    private static final String LAST_NAME = "Toledo";
    private static final String CPF_NUMBER = "948.405.850-75";
    private static final long PERSON_ID = 1L;
    public static final LocalDate BIRTH_DATE = LocalDate.of(2021, 10, 10);

    public static PersonDTO createFakeDtoToValid() {
        return PersonDTO.builder()
                .id(PERSON_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate(BIRTH_DATE.format(DateTimeFormatter.ISO_DATE))
                .phones(Collections.singletonList(PhoneUtils.createFakeDTO()))
                .build();
    }

    public static PersonDTO createFakeDtoToSave() {
        return PersonDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate("10-10-2021")
                .phones(Collections.singletonList(PhoneUtils.createFakeDTO()))
                .build();
    }

    public static Person createFakeEntity() {
        return Person.builder()
                .id(PERSON_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate(BIRTH_DATE)
                .phones(Collections.singletonList(PhoneUtils.createFakeEntity()))
                .build();
    }

    public static String asJsonString(PersonDTO personDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModules(new JavaTimeModule());

            return objectMapper.writeValueAsString(personDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
