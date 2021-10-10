package com.digitalInovationOne.personapi.service;

import com.digitalInovationOne.personapi.dto.request.PersonDTO;
import com.digitalInovationOne.personapi.dto.response.MessageResponseDTO;
import com.digitalInovationOne.personapi.entity.Person;
import com.digitalInovationOne.personapi.exception.PersonNotFoundException;
import com.digitalInovationOne.personapi.mapper.PersonMapper;
import com.digitalInovationOne.personapi.repository.PersonRepository;
import com.digitalInovationOne.personapi.utils.PersonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonService personService;

    @Test
    void testGivenPersonDTOThenReturnSavedMessage() {
        PersonDTO personDTO = PersonUtils.createFakeDtoToSave();
        Person expectedSavedPerson = PersonUtils.createFakeEntity();

        Mockito.when(personRepository.save(any(Person.class))).thenReturn(expectedSavedPerson);
        MessageResponseDTO succesMessage = personService.createPerson(personDTO);
        Assertions.assertEquals("Person successfully created with ID 1", succesMessage.getMessage());
    }

    @Test
    void testGivenValidPersonIdThenReturnThisPerson() throws PersonNotFoundException {
        PersonDTO expectedPersonDTO = PersonUtils.createFakeDtoToValid();
        Person expectedSavedPerson = PersonUtils.createFakeEntity();
        expectedPersonDTO.setId(expectedSavedPerson.getId());

        Mockito.when(personRepository.findById(expectedSavedPerson.getId())).thenReturn(Optional.of(expectedSavedPerson));
        PersonDTO personDTO = personService.findById(expectedSavedPerson.getId());

        Assertions.assertEquals(expectedPersonDTO, personDTO);
        Assertions.assertEquals(expectedSavedPerson.getId(), personDTO.getId());
        Assertions.assertEquals(expectedSavedPerson.getFirstName(), personDTO.getFirstName());
    }

    @Test
    void testGivenInvalidPersonIdThenThrowException() {
        var invalidPersonId = 1L;
        Mockito.when(personRepository.findById(invalidPersonId))
                .thenReturn(Optional.ofNullable(any(Person.class)));

        Assertions.assertThrows(PersonNotFoundException.class, () -> personService.findById(invalidPersonId));
    }

    @Test
    void testGivenNoDataThenReturnAllPeopleRegistered() {
        List<Person> expectedRegisteredPeople = Collections.singletonList(PersonUtils.createFakeEntity());
        PersonDTO personDTO = PersonUtils.createFakeDtoToValid();

        Mockito.when(personRepository.findAll()).thenReturn(expectedRegisteredPeople);

        List<PersonDTO> expectedPeopleDTOList = personService.listAll();

        Assertions.assertFalse(expectedPeopleDTOList.isEmpty());
        Assertions.assertEquals(expectedPeopleDTOList.get(0), personDTO);
    }

    @Test
    void testGivenValidPersonIdAndUpdateInfoThenReturnSuccesOnUpdate() throws PersonNotFoundException {
        var updatedPersonId = 2L;

        PersonDTO updatePersonDTORequest = PersonUtils.createFakeDtoToSave();
        updatePersonDTORequest.setId(updatedPersonId);
        updatePersonDTORequest.setLastName("Toledo updated");

        Person expectedPersonToUpdate = PersonUtils.createFakeEntity();
        expectedPersonToUpdate.setId(updatedPersonId);

        Person expectedPersonUpdated = PersonUtils.createFakeEntity();
        expectedPersonUpdated.setId(updatedPersonId);
        expectedPersonToUpdate.setLastName(updatePersonDTORequest.getLastName());

        Mockito.when(personRepository.findById(updatedPersonId)).thenReturn(Optional.of(expectedPersonUpdated));
        Mockito.when(personRepository.save(any(Person.class))).thenReturn(expectedPersonUpdated);

        MessageResponseDTO successMessage = personService.updateById(updatedPersonId, updatePersonDTORequest);

        Assertions.assertEquals("Person successfully updated with ID 2", successMessage.getMessage());
    }

    @Test
    void testGivenInvalidPersonIdAndUpdateInfoThenThrowExceptionOnUpdate() throws PersonNotFoundException {
        var invalidPersonId = 1L;

        PersonDTO updatePersonDTORequest = PersonUtils.createFakeDtoToValid();
        updatePersonDTORequest.setId(invalidPersonId);
        updatePersonDTORequest.setLastName("Toledo updated");

        Mockito.when(personRepository.findById(invalidPersonId))
                .thenReturn(Optional.ofNullable(any(Person.class)));

        Assertions.assertThrows(PersonNotFoundException.class, () -> personService.updateById(invalidPersonId, updatePersonDTORequest));
    }

    @Test
    void testGivenValidPersonIdThenReturnSuccesOnDelete() throws PersonNotFoundException {
        var deletedPersonId = 1L;
        Person expectedPersonToDelete = PersonUtils.createFakeEntity();

        Mockito.when(personRepository.findById(deletedPersonId)).thenReturn(Optional.of(expectedPersonToDelete));
        personService.delete(deletedPersonId);

        Mockito.verify(personRepository, Mockito.times(1)).deleteById(deletedPersonId);
    }

    @Test
    void testGivenInvalidPersonIdThenReturnSuccesOnDelete() throws PersonNotFoundException {
        var invalidPersonId = 1L;

        Mockito.when(personRepository.findById(invalidPersonId))
                .thenReturn(Optional.ofNullable(any(Person.class)));

        Assertions.assertThrows(PersonNotFoundException.class, () -> personService.delete(invalidPersonId));
    }

}
