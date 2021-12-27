package com.example.demo1.model;

import com.example.demo1.model.enums.MovieGenre;
import com.example.demo1.model.enums.MpaaRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Entity
@Table(name = "MOVIE")
//@NamedQuery(name = "Entity.Movie.getAll", query = "SELECT m FROM Movie m")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotEmpty
    @NotNull(message = "name - is null!")
    private String name;//Поле не может быть null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinate_id")

    private Coordinates coordinates;//Поле не может быть null

    @Column(name = "date")
//    @NotNull(message = "creation date - is null!")
    @CreationTimestamp
    private ZonedDateTime creationDate;//Поле не может быть null;Значение этого поля должно генерироваться автоматически

    @Column(name = "oscars")
    @DecimalMin(value = "0", inclusive = false, message = "oscars count must be > 0")
    private Long oscarsCount; //Значение поля должно быть больше 0, Поле может быть null

    @Enumerated(EnumType.STRING)
    private MovieGenre genre; //Поле может быть null

    @Enumerated(EnumType.STRING)
    @Column(name = "rating")
    @NotNull(message = "rating - is null!")
    private MpaaRating mpaaRating;//Поле не может быть null

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @XmlElement(name = "director")
    private Person director;//Поле не может быть null
}
