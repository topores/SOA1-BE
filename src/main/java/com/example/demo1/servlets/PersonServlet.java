package com.example.demo1.servlets;


import com.example.demo1.dto.PersonDTO;
import com.example.demo1.dto.dtoList.PersonDTOList;
import com.example.demo1.mapper.LocationMapper;
import com.example.demo1.mapper.PersonMapper;
import com.example.demo1.model.Person;
import com.example.demo1.repository.PersonRepository;
import com.example.demo1.validators.EntityValidator;
import com.example.demo1.converters.ClassToJsonConverter;
import com.example.demo1.converters.JsonToClassConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/persons/*")
public class PersonServlet extends HttpServlet {
    private PersonRepository repository;
    private ClassToJsonConverter classToJson;
    private JsonToClassConverter jsonToClass;
    private LocationMapper locationMapper;
    private EntityValidator entityValidator;
    private PersonMapper personMapper;

    @Override
    public void init() throws ServletException {
        repository = new PersonRepository();
        classToJson = new ClassToJsonConverter();
        jsonToClass = new JsonToClassConverter();
        locationMapper = new LocationMapper();
        entityValidator = new EntityValidator();
        personMapper = new PersonMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String id = null;
        if (pathInfo != null)
            id = pathInfo.substring(1);

        if (id != null) {
            Person person = repository.findById(Integer.parseInt(id));
            PersonDTO dto = personMapper.mapPersonToPersonDTO(person);
            response.getWriter().write(classToJson.personToJSON(dto));
            return;
        }

        List<Person> personList = repository.findAll();
        PersonDTOList dto = new PersonDTOList(new ArrayList<>());
        dto.setPersonsList(personMapper.mapPersonListToPersonDTOList(personList));
        response.getWriter().write(classToJson.personsToJSON(dto));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        PersonDTOList personDTOList = jsonToClass.getPersonDTOFromJSON(requestBody);
//        Person personToUpdate = personMapper.mapPersonDTOToPerson(personDTOList.getPersonsList().get(0));

        Person personToUpdate = personMapper.mapPersonDTOToPerson(jsonToClass.getSinglePersonDTOFromJSON(requestBody));

        entityValidator.validateForUpdatePerson(personToUpdate);
        repository.update(personToUpdate);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        PersonDTOList personDTOList = jsonToClass.getPersonDTOFromJSON(requestBody);
        Person personToPersist = personMapper.mapPersonDTOToPerson(personDTOList.getPersonsList().get(0));

        entityValidator.validatePerson(personToPersist);
        repository.create(personToPersist);
    }

}
