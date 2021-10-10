package com.digitalInovationOne.personapi.mapper;

import com.digitalInovationOne.personapi.dto.request.PersonDTO;
import com.digitalInovationOne.personapi.dto.request.PhoneDTO;
import com.digitalInovationOne.personapi.entity.Person;
import com.digitalInovationOne.personapi.entity.Phone;
import com.digitalInovationOne.personapi.utils.PersonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonMapperTest {

    @Autowired
    private PersonMapper personMapper;

    @Test
    public void testGivenPersonDTOThenReturnPersonEntity(){
        PersonDTO personDTO = PersonUtils.createFakeDtoToSave();
        Person person = personMapper.toModel(personDTO);
        Phone phone = person.getPhones().get(0);
        PhoneDTO phoneDTO = personDTO.getPhones().get(0);

        Assertions.assertEquals(personDTO.getFirstName(), person.getFirstName());
        Assertions.assertEquals(personDTO.getLastName(), person.getLastName());
        Assertions.assertEquals(personDTO.getCpf(), person.getCpf());

        Assertions.assertEquals(phoneDTO.getType(), phone.getType());
        Assertions.assertEquals(phoneDTO.getNumber(), phone.getNumber());

    }
    @Test
    public void testGivenPersonEntityThenReturnPersonDTO() {
        Person person = PersonUtils.createFakeEntity();
        PersonDTO personDTO = personMapper.toDTO(person);
        Phone phone = person.getPhones().get(0);
        PhoneDTO phoneDTO = personDTO.getPhones().get(0);

        Assertions.assertEquals(person.getFirstName(), personDTO.getFirstName());
        Assertions.assertEquals(person.getLastName(), personDTO.getLastName());
        Assertions.assertEquals(person.getCpf(), personDTO.getCpf());

        Assertions.assertEquals(phone.getType(), phoneDTO.getType());
        Assertions.assertEquals(phone.getNumber(), phoneDTO.getNumber());
    }
}
