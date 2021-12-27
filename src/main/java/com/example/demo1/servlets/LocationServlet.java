package com.example.demo1.servlets;



import com.example.demo1.dto.LocationDTO;
import com.example.demo1.dto.dtoList.LocationDTOList;
import com.example.demo1.mapper.LocationMapper;
import com.example.demo1.model.Location;
import com.example.demo1.repository.LocationRepository;
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

@WebServlet("/locations/*")
public class LocationServlet extends HttpServlet {
    private LocationRepository repository;
    private ClassToJsonConverter classToJson;
    private JsonToClassConverter jsonToClass;
    private LocationMapper locationMapper;
    private EntityValidator entityValidator;

    @Override
    public void init() throws ServletException {
        repository = new LocationRepository();
        classToJson = new ClassToJsonConverter();
        jsonToClass = new JsonToClassConverter();
        locationMapper = new LocationMapper();
        entityValidator = new EntityValidator();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String id = null;
        if (pathInfo != null)
            id = pathInfo.substring(1);

        if (id != null) {
            Location location = repository.findById(Integer.parseInt(id));
            LocationDTO dto = locationMapper.mapLocationToLocationDTO(location);

            //dto.getLocationsList().add(locationMapper.mapLocationToLocationDTO(location));
            //dto.getLocationsList().add(new LocationDTO());
            response.getWriter().write(classToJson.locationToJSON(dto));
            return;
        }

        List<Location> locations = repository.findAll();
        LocationDTOList dto = new LocationDTOList(new ArrayList<>());
        //dto.getLocationsList().add(new LocationDTO());
        dto.setLocationsList(locationMapper.mapLocationListToLocationDTOList(locations));
        response.getWriter().write(classToJson.locationsToJSON(dto));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        LocationDTOList locationDTOList = jsonToClass.getLocationDTOFromJSON(requestBody);
//        Location locationToUpdate = locationMapper.mapLocationDTOToLocation(locationDTOList.getLocationsList().get(0));
//
        Location locationToUpdate = locationMapper.mapLocationDTOToLocation(jsonToClass.getSingleLocationDTOFromJSON(requestBody));

        entityValidator.validateLocation(locationToUpdate);
        repository.update(locationToUpdate);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        LocationDTOList locationDTOList = jsonToClass.getLocationDTOFromJSON(requestBody);
        Location locationToPersist = locationMapper.mapLocationDTOToLocation(locationDTOList.getLocationsList().get(0));
        entityValidator.validateLocation(locationToPersist);
        repository.create(locationToPersist);
    }

}
