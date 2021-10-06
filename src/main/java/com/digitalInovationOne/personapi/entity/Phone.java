package com.digitalInovationOne.personapi.entity;

import com.digitalInovationOne.personapi.dto.request.PhoneDTO;
import com.digitalInovationOne.personapi.enums.PhoneType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhoneType type;

    @Column(nullable = false)
    private String number;

    public Phone(PhoneDTO phoneDTO) {
        this.setId(phoneDTO.getId());
        this.setType((phoneDTO.getType() == null) ? null : phoneDTO.getType());
        this.setNumber(phoneDTO.getNumber());
    }

}
