package com.example.demo1.mapper;

import com.example.demo1.dto.MovieDTO;
import com.example.demo1.exceptions.EntityIsNotValidException;
import com.example.demo1.model.Movie;
import com.example.demo1.model.Person;
import com.example.demo1.repository.CoordinatesRepository;
import com.example.demo1.repository.PersonRepository;
import com.example.demo1.utils.FieldValidatorUtil;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MovieMapper {
    private CoordinatesMapper coordinatesMapper;
    private PersonMapper personMapper;
    private CoordinatesRepository coordinatesRepository;
    private PersonRepository personRepository;

    public MovieMapper() {
        coordinatesMapper = new CoordinatesMapper();
        personMapper = new PersonMapper();
        coordinatesRepository = new CoordinatesRepository();
        personRepository = new PersonRepository();
    }

    public Movie mapMovieDTOToMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setId(FieldValidatorUtil.getIntegerFieldValue(movieDTO.getId()));
        movie.setCoordinates(coordinatesMapper.mapCoordinatesDTOToCoordinates(movieDTO.getCoordinates()));
        if (movie.getCoordinates().getId() == null) throw new EntityIsNotValidException("coordinates must not be null");
        if (movie.getCoordinates() != null && !coordinatesRepository.existsById(movie.getCoordinates().getId()))
            throw new EntityIsNotValidException("coordinates with id = " + movie.getCoordinates().getId() + " does not exist");
        movie.setCreationDate(FieldValidatorUtil.getZonedDateTimeFieldValue(movieDTO.getCreationDate()));
        movie.setGenre(FieldValidatorUtil.getMovieGenreValue(movieDTO.getGenre()));
        movie.setMpaaRating(FieldValidatorUtil.getMpaaRatingValue(movieDTO.getMpaaRating()));
        movie.setName(FieldValidatorUtil.getStringValue(movieDTO.getName()));
        movie.setOscarsCount(FieldValidatorUtil.getLongFieldValue(movieDTO.getOscarsCount()));
        Person director;
        if (!movieDTO.getDirector().getId().equals("")){
            director = personRepository.findById(Integer.parseInt(movieDTO.getDirector().getId()));
        }else {
            throw new EntityIsNotValidException("director must not be null");
        }
        movie.setDirector(director);
        return movie;
    }

    public MovieDTO mapMovieToMovieDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(String.valueOf(movie.getId()));
        movieDTO.setName(movie.getName());
        movieDTO.setCoordinates(coordinatesMapper.mapCoordinatesToCoordinatesDTO(movie.getCoordinates()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        movieDTO.setCreationDate(movie.getCreationDate().format(formatter));

        movieDTO.setDirector(personMapper.mapPersonToPersonDTO(movie.getDirector()));
        movieDTO.setMpaaRating(String.valueOf(movie.getMpaaRating()));
        movieDTO.setGenre(String.valueOf(movie.getGenre()));
        movieDTO.setOscarsCount(String.valueOf(movie.getOscarsCount()));
        return movieDTO;
    }

    public List<MovieDTO> mapMovieListToMovieDTOList(List<Movie> movieList) {
        ArrayList<MovieDTO> movieDTOArrayList = new ArrayList<>();
        for (Movie movie : movieList) {
            movieDTOArrayList.add(mapMovieToMovieDTO(movie));
        }
        return movieDTOArrayList;
    }


}
