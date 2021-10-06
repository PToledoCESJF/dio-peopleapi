package com.digitalInovationOne.personapi.dto.request;

import com.digitalInovationOne.personapi.entity.Person;
import com.digitalInovationOne.personapi.entity.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Long id;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String firstName;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String lastName;

    @NotEmpty
    @CPF
    private String cpf;

    private String birthDate;

    @Valid
    @NotEmpty
    private List<PhoneDTO> phones;

    public PersonDTO(Person person){
        this.setId(person.getId());
        this.setFirstName(person.getFirstName());
        this.setLastName(person.getLastName());
        this.setCpf(person.getCpf());
        this.setBirthDate(person.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        this.setPhones(person.getPhones().stream().map(obj -> new PhoneDTO(obj)).collect(Collectors.toList()));
    }
}