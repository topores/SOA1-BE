package com.example.demo1.servlets;


import com.example.demo1.dto.CoordinatesDTO;
import com.example.demo1.dto.dtoList.CoordinatesDTOList;
import com.example.demo1.mapper.CoordinatesMapper;
import com.example.demo1.model.Coordinates;
import com.example.demo1.repository.CoordinatesRepository;
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

@WebServlet("/coordinates/*")
public class CoordinatesServlet extends HttpServlet {
    private CoordinatesRepository repository;
    private ClassToJsonConverter classToJson;
    private JsonToClassConverter jsonToClass;
    private EntityValidator entityValidator;
    private CoordinatesMapper coordinatesMapper;


    @Override
    public void init() throws ServletException {
        repository = new CoordinatesRepository();
        classToJson = new ClassToJsonConverter();
        jsonToClass = new JsonToClassConverter();
        entityValidator = new EntityValidator();
        coordinatesMapper = new CoordinatesMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String id = null;
        System.out.println(pathInfo);
        if (pathInfo != null)
            id = pathInfo.substring(1);

        if (id != null) {
            Coordinates coordinates = (repository.findById(Integer.parseInt(id)));
            //Coordinates coordinates = new Coordinates(1,Float.parseFloat("2.0"),3L);
            CoordinatesDTO dto = coordinatesMapper.mapCoordinatesToCoordinatesDTO(coordinates);
//            List<CoordinatesDTO> dtoList = new ArrayList<>();
//            dtoList.add(coordinatesMapper.mapCoordinatesToCoordinatesDTO(coordinates));
//            dto.setCoordinatesList(dtoList);
            response.getWriter().write(classToJson.coordinatesToJSON(dto));
            return;
        }

        List<Coordinates> coordinates = repository.findAll();

        CoordinatesDTOList dto = new CoordinatesDTOList(new ArrayList<>());
        dto.setCoordinatesList(coordinatesMapper.mapCoordinatesListToCoordinatesDTOList(coordinates));

        response.getWriter().write(classToJson.coordinatesToJSON(dto));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
       // System.out.println(requestBody);
//        CoordinatesDTOList coordinatesDTOList = jsonToClass.getCoordinatesDTOFromJSON(requestBody);
//
//        Coordinates coordinatesToUpdate = coordinatesMapper.mapCoordinatesDTOToCoordinates(coordinatesDTOList.getCoordinatesList().get(0));

        Coordinates coordinatesToUpdate = coordinatesMapper.mapCoordinatesDTOToCoordinates(jsonToClass.getSingleCoordinatesDTOFromJSON(requestBody));



        entityValidator.validateCoordinates(coordinatesToUpdate);
        repository.update(coordinatesToUpdate);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        System.out.println(requestBody);
        //response.getWriter().write(requestBody);
        CoordinatesDTOList coordinatesDTOList = jsonToClass.getCoordinatesDTOFromJSON(requestBody);
        System.out.println(coordinatesDTOList);
        Coordinates coordinatesToPersist = coordinatesMapper.mapCoordinatesDTOToCoordinates(coordinatesDTOList.getCoordinatesList().get(0));
        entityValidator.validateCoordinates(coordinatesToPersist);
        repository.create(coordinatesToPersist);
    }

}
