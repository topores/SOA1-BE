package com.example.demo1.model;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@XmlRootElement
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})
@Table(name = "PERSON")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @NotNull(message = "name - is null!")
    private String name; //Поле не может быть null
    
    @NotNull(message = "birthday - is null!")
    private java.util.Date birthday; //Поле может быть null

    @NotNull(message = "weight - is null!")
    @DecimalMin(value = "0", inclusive = false, message = "weight must be > 0")
    private Long weight; //Поле не может быть null, Значение поля должно быть больше 0

    @Column(unique=true)
    @NotNull(message = "passport - is null!")
    private String passportID; //Значение этого поля должно быть уникальным, Поле не может быть null


    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @NotNull(message = "location - is null!")
    private Location location; //Поле может быть null


}
