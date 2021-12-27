package com.example.demo1.converters;

import com.example.demo1.dto.CoordinatesDTO;
import com.example.demo1.dto.LocationDTO;
import com.example.demo1.dto.MovieDTO;
import com.example.demo1.dto.PersonDTO;
import com.example.demo1.dto.dtoList.CoordinatesDTOList;
import com.example.demo1.dto.dtoList.LocationDTOList;
import com.example.demo1.dto.dtoList.MovieDTOList;
import com.example.demo1.dto.dtoList.PersonDTOList;
import com.example.demo1.exceptions.JsonParseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonToClassConverter {

    public CoordinatesDTOList getCoordinatesDTOFromJSON(String json) {
        CoordinatesDTOList coordinatesDTOList = null;
        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(CoordinatesDTOList.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            coordinatesDTOList = (CoordinatesDTOList) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            GsonBuilder builder = new GsonBuilder();
            //builder.setPrettyPrinting();
            Gson gson = builder.create();
            coordinatesDTOList = gson.fromJson(json, CoordinatesDTOList.class);
            System.out.println(gson.toJson(coordinatesDTOList));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return coordinatesDTOList;
    }

    public MovieDTOList getMovieDTOFromJSON(String json) {
        MovieDTOList movieDTOList;
        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(MovieDTOList.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            movieDTOList = (MovieDTOList) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            GsonBuilder builder = new GsonBuilder();
            //builder.setPrettyPrinting();
            Gson gson = builder.create();
            movieDTOList = gson.fromJson(json, MovieDTOList.class);
            System.out.println(gson.toJson(movieDTOList));
        } catch (Exception e) {
            throw new JsonParseException(e.getMessage());
        }
        return movieDTOList;
    }

    public LocationDTOList getLocationDTOFromJSON(String json) {
        LocationDTOList movieDTO = null;
        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(LocationDTOList.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            movieDTO = (LocationDTOList) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            GsonBuilder builder = new GsonBuilder();
            //builder.setPrettyPrinting();
            Gson gson = builder.create();
            movieDTO = gson.fromJson(json, LocationDTOList.class);
            System.out.println(gson.toJson(movieDTO));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieDTO;
    }

    public PersonDTOList getPersonDTOFromJSON(String json) {
        PersonDTOList movieDTO = null;
        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(PersonDTOList.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            movieDTO = (PersonDTOList) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            GsonBuilder builder = new GsonBuilder();
            //builder.setPrettyPrinting();
            Gson gson = builder.create();
            movieDTO = gson.fromJson(json, PersonDTOList.class);
            System.out.println(gson.toJson(movieDTO));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieDTO;
    }



    public CoordinatesDTO getSingleCoordinatesDTOFromJSON(String json) {
        CoordinatesDTO coordinatesDTO = null;
        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(CoordinatesDTO.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            coordinatesDTO = (CoordinatesDTO) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            GsonBuilder builder = new GsonBuilder();
            //builder.setPrettyPrinting();
            Gson gson = builder.create();
            coordinatesDTO = gson.fromJson(json, CoordinatesDTO.class);
            System.out.println(gson.toJson(coordinatesDTO));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return coordinatesDTO;
    }

    public MovieDTO getSingleMovieDTOFromJSON(String json) {
        MovieDTO movieDTO;
        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(MovieDTO.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            movieDTO = (MovieDTO) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            GsonBuilder builder = new GsonBuilder();
            //builder.setPrettyPrinting();
            Gson gson = builder.create();
            movieDTO = gson.fromJson(json, MovieDTO.class);
            System.out.println(gson.toJson(movieDTO));
        } catch (Exception e) {
            throw new JsonParseException(e.getMessage());
        }
        return movieDTO;
    }

    public LocationDTO getSingleLocationDTOFromJSON(String json) {
        LocationDTO movieDTO = null;
        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(LocationDTO.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            movieDTO = (LocationDTO) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            GsonBuilder builder = new GsonBuilder();
            //builder.setPrettyPrinting();
            Gson gson = builder.create();
            movieDTO = gson.fromJson(json, LocationDTO.class);
            System.out.println(gson.toJson(movieDTO));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieDTO;
    }

    public PersonDTO getSinglePersonDTOFromJSON(String json) {
        PersonDTO movieDTO = null;
        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(PersonDTO.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            movieDTO = (PersonDTO) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            GsonBuilder builder = new GsonBuilder();
            //builder.setPrettyPrinting();
            Gson gson = builder.create();
            movieDTO = gson.fromJson(json, PersonDTO.class);
            System.out.println(gson.toJson(movieDTO));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieDTO;
    }
}
