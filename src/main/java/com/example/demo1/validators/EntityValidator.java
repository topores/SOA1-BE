package com.example.demo1.validators;



import com.example.demo1.exceptions.EntityIsNotValidException;
import com.example.demo1.model.Coordinates;
import com.example.demo1.model.Location;
import com.example.demo1.model.Movie;
import com.example.demo1.model.Person;
import com.example.demo1.repository.PersonRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

public class EntityValidator {
    private Validator validator;

    public EntityValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private String formExceptionMsg(Set<ConstraintViolation<Object>> constraintViolations) {
        String errorMessage = "";
        for (ConstraintViolation<Object> violation : constraintViolations) {
            errorMessage = errorMessage.concat(violation.getMessage() + "\n");
        }
        return errorMessage;
    }

    public void validateMovie(Movie movie) throws EntityIsNotValidException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(movie);
        if (!constraintViolations.isEmpty())
            throw new EntityIsNotValidException(formExceptionMsg(constraintViolations));
    }

    public void validateLocation(Location movie) throws EntityIsNotValidException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(movie);
        if (!constraintViolations.isEmpty())
            throw new EntityIsNotValidException(formExceptionMsg(constraintViolations));
    }

    public void validateCoordinates(Coordinates movie) throws EntityIsNotValidException {

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(movie);
        if (!constraintViolations.isEmpty())
            throw new EntityIsNotValidException(formExceptionMsg(constraintViolations));
    }

    public void validatePerson(Person movie) throws EntityIsNotValidException {

        List<Person> persons= new PersonRepository().findAll();
        for (Person p:persons) {
            if (movie.getPassportID().equals(p.getPassportID())) throw new EntityIsNotValidException("Person with this PassportID already exists!");
        }

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(movie);
        if (!constraintViolations.isEmpty())
            throw new EntityIsNotValidException(formExceptionMsg(constraintViolations));
    }

    public void validateForUpdatePerson(Person movie) throws EntityIsNotValidException {

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(movie);
        if (!constraintViolations.isEmpty())
            throw new EntityIsNotValidException(formExceptionMsg(constraintViolations));
    }


}
