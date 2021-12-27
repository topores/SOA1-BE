package com.example.demo1.mapper;


import com.example.demo1.dto.LocationDTO;
import com.example.demo1.model.Location;
import com.example.demo1.utils.FieldValidatorUtil;

import java.util.ArrayList;
import java.util.List;

public class LocationMapper {
    public Location mapLocationDTOToLocation(LocationDTO locationDTO) {
        Location location = new Location();
        location.setId(FieldValidatorUtil.getIntegerFieldValue(locationDTO.getId()));
        location.setX(FieldValidatorUtil.getDoubleFieldValue(locationDTO.getX()));
        location.setY(FieldValidatorUtil.getIntegerFieldValue(locationDTO.getY()));
        location.setName(FieldValidatorUtil.getStringValue(locationDTO.getName()));
        return location;
    }

    public LocationDTO mapLocationToLocationDTO(Location location) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(String.valueOf(location.getId()));
        locationDTO.setName(location.getName());
        locationDTO.setX(String.valueOf(location.getX()));
        locationDTO.setY(String.valueOf(location.getY()));
        return locationDTO;
    }

    public List<LocationDTO> mapLocationListToLocationDTOList(List<Location> locationList) {
        ArrayList<LocationDTO> locationDTOArrayList = new ArrayList<>();
        for (Location location : locationList) {
            locationDTOArrayList.add(mapLocationToLocationDTO(location));
        }
        return locationDTOArrayList;
    }
}
