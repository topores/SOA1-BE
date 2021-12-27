package com.example.demo1.converters;

import com.example.demo1.dto.*;
import com.example.demo1.model.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.example.demo1.dto.dtoList.CoordinatesDTOList;
import com.example.demo1.dto.dtoList.LocationDTOList;
import com.example.demo1.dto.dtoList.MovieDTOList;
import com.example.demo1.dto.dtoList.PersonDTOList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class ClassToJsonConverter {
    public String coordinatesToJSON(CoordinatesDTOList coordinateDTO) {
        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(CoordinatesDTOList.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            StringWriter sw = new StringWriter();
//            jaxbMarshaller.marshal(coordinateDTO, sw);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            return gson.toJson(coordinateDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String moviesToJSON(MovieDTOList employee) {
        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(MovieDTOList.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            StringWriter sw = new StringWriter();
//            jaxbMarshaller.marshal(employee, sw);
//            return sw.toString();
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            return gson.toJson(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String locationsToJSON(LocationDTOList employee) {
        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(LocationDTOList.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            StringWriter sw = new StringWriter();
//            jaxbMarshaller.marshal(employee, sw);
//            return sw.toString();
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            return gson.toJson(employee);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String personsToJSON(PersonDTOList employee) {
        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(PersonDTOList.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            StringWriter sw = new StringWriter();
//            jaxbMarshaller.marshal(employee, sw);
//            return sw.toString();
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            return gson.toJson(employee);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String movieToJSON(MovieDTO employee) {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            return gson.toJson(employee);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String personToJSON(PersonDTO employee) {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            return gson.toJson(employee);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String locationToJSON(LocationDTO employee) {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            return gson.toJson(employee);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String coordinatesToJSON(CoordinatesDTO employee) {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            return gson.toJson(employee);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String countToJSON(CountDTO employee) {
        try {
//
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            return gson.toJson(employee);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String exceprionToJSON(ExceptionDTO exception) {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            return gson.toJson(exception);
//            JAXBContext jaxbContext = JAXBContext.newInstance(ExceptionDTO.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            StringWriter sw = new StringWriter();
//            jaxbMarshaller.marshal(exception, sw);
//            return sw.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
