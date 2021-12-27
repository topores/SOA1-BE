package com.example.demo1.mapper;

import com.example.demo1.dto.PersonDTO;
import com.example.demo1.exceptions.EntityIsNotValidException;
import com.example.demo1.model.Person;
import com.example.demo1.repository.LocationRepository;
import com.example.demo1.utils.FieldValidatorUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PersonMapper {

    private LocationMapper locationMapper;
    private LocationRepository locationRepository;

    public PersonMapper() {
        locationMapper = new LocationMapper();
        locationRepository = new LocationRepository();
    }

    public Person mapPersonDTOToPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setId(FieldValidatorUtil.getIntegerFieldValue(personDTO.getId()));
        person.setPassportID(FieldValidatorUtil.getStringValue(personDTO.getPassportID()));
        person.setBirthday(FieldValidatorUtil.getDateValue(personDTO.getBirthday()));
        person.setName(FieldValidatorUtil.getStringValue(personDTO.getName()));
        person.setWeight(FieldValidatorUtil.getLongFieldValue(personDTO.getWeight()));
        if (personDTO.getLocation().getId().equals(""))
            throw new EntityIsNotValidException("location must not be null");
        person.setLocation(locationMapper.mapLocationDTOToLocation(personDTO.getLocation()));
        if (person.getLocation().getId() != null) {
            if (!locationRepository.existsById(person.getLocation().getId()))
                throw new EntityIsNotValidException("location with id = " + person.getLocation().getId() + " does not exist");
        }

        return person;
    }

    public PersonDTO mapPersonToPersonDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(String.valueOf(person.getId()));
        personDTO.setName(person.getName());
        personDTO.setLocation(locationMapper.mapLocationToLocationDTO(person.getLocation()));

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        personDTO.setBirthday(dateFormat.format(person.getBirthday()));

        personDTO.setWeight(String.valueOf(person.getWeight()));
        personDTO.setPassportID(String.valueOf(person.getPassportID()));
        return personDTO;
    }

    public List<PersonDTO> mapPersonListToPersonDTOList(List<Person> personList) {
        ArrayList<PersonDTO> personDTOArrayList = new ArrayList<>();
        for (Person person : personList) {
            personDTOArrayList.add(mapPersonToPersonDTO(person));
        }
        return personDTOArrayList;
    }
}
