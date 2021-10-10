package com.digitalInovationOne.personapi.service;

import com.digitalInovationOne.personapi.dto.request.PersonDTO;
import com.digitalInovationOne.personapi.dto.response.MessageResponseDTO;
import com.digitalInovationOne.personapi.entity.Person;
import com.digitalInovationOne.personapi.exception.PersonNotFoundException;
import com.digitalInovationOne.personapi.mapper.PersonMapper;
import com.digitalInovationOne.personapi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    // Metodo refatorado o uso do Mapstruct
    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
//        return allPeople.stream().map(obj -> new PersonDTO(obj)).collect(Collectors.toList());
        return allPeople.stream().map(personMapper::toDTO).collect(Collectors.toList());
    }

    // Metodo refatorado o uso do Mapstruct
    public MessageResponseDTO createPerson(PersonDTO personDTO){
//        Person personToSave = new Person(personDTO);
        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created person with ID ");
    }

    // Metodo refatorado o uso do Mapstruct
    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);
//        return new PersonDTO(person);
        return personMapper.toDTO(person);
    }

    public void delete(Long id) throws PersonNotFoundException{
        verifyIfExists(id);
        personRepository.deleteById(id);
    }

    // Metodo refatorado o uso do Mapstruct
    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);
//        Person personToUpdate = new Person(personDTO);
        Person personToUpdate = personMapper.toModel(personDTO);
        Person updatedPerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Updated person with ID ");
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}
