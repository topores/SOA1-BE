package com.example.demo1.dto.dtoList;

import com.example.demo1.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class PersonDTOList {
    @XmlElementWrapper(name = "persons")
    @XmlElement(name = "person")
    private List<PersonDTO> personsList;
}
