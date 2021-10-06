package com.digitalInovationOne.personapi.entity;

import com.digitalInovationOne.personapi.dto.request.PersonDTO;
import com.digitalInovationOne.personapi.dto.request.PhoneDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String cpf;

    private LocalDate birthDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Phone> phones;

    public Person(PersonDTO personDTO){
        this.setFirstName(personDTO.getFirstName());
        this.setLastName(personDTO.getLastName());
        this.setCpf(personDTO.getCpf());
        this.setBirthDate(LocalDate.parse(personDTO.getBirthDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        this.setPhones(personDTO.getPhones().stream().map(obj -> new Phone(obj)).collect(Collectors.toList()));
    }
}
