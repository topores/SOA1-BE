package com.example.demo1.mapper;


import com.example.demo1.dto.CoordinatesDTO;
import com.example.demo1.model.Coordinates;
import com.example.demo1.utils.FieldValidatorUtil;

import java.util.ArrayList;
import java.util.List;

public class CoordinatesMapper {

    public Coordinates mapCoordinatesDTOToCoordinates(CoordinatesDTO coordinatesDTO) {
        Coordinates coordinates = new Coordinates();
        coordinates.setId(FieldValidatorUtil.getIntegerFieldValue(coordinatesDTO.getId()));
        coordinates.setX(FieldValidatorUtil.getFloatFieldValue(coordinatesDTO.getX()));
        coordinates.setY(FieldValidatorUtil.getLongFieldValue(coordinatesDTO.getY()));
        return coordinates;
    }

    public CoordinatesDTO mapCoordinatesToCoordinatesDTO(Coordinates coordinates) {
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setId(String.valueOf(coordinates.getId()));
        coordinatesDTO.setX(String.valueOf(coordinates.getX()));
        coordinatesDTO.setY(String.valueOf(coordinates.getY()));
        return coordinatesDTO;
    }

    public List<CoordinatesDTO> mapCoordinatesListToCoordinatesDTOList(List<Coordinates> coordinatesList) {
        ArrayList<CoordinatesDTO> coordinatesDTOList = new ArrayList<>();
        for (Coordinates coordinates : coordinatesList) {
            coordinatesDTOList.add(mapCoordinatesToCoordinatesDTO(coordinates));
        }
        return coordinatesDTOList;
    }
}
