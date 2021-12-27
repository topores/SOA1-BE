package com.example.demo1.dto.dtoList;

import com.example.demo1.dto.CoordinatesDTO;
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
public class CoordinatesDTOList {
    @XmlElementWrapper(name = "coordinates")
    @XmlElement(name = "coordinate")
    private List<CoordinatesDTO> coordinatesList;

    @Override
    public String toString() {
        String s="";
        for (CoordinatesDTO c: coordinatesList){
            s=s+" "+c.toString();
        }
        return "CoordinatesDTOList{" +
                "coordinatesList=" + s +
                '}';
    }
}
