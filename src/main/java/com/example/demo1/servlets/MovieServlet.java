package com.example.demo1.servlets;


import com.example.demo1.dto.MovieDTO;
import com.example.demo1.dto.PagedMovieList;
import com.example.demo1.dto.dtoList.MovieDTOList;
import com.example.demo1.exceptions.EntityIsNotValidException;
import com.example.demo1.mapper.MovieMapper;
import com.example.demo1.model.Movie;
import com.example.demo1.repository.MovieRepository;
import com.example.demo1.utils.UrlParamsUtil;
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

@WebServlet(value = "/movies/*", loadOnStartup = 1)
public class MovieServlet extends HttpServlet {
    private MovieRepository repository;
    private ClassToJsonConverter classToJson;
    private JsonToClassConverter jsonToClass;
    private EntityValidator entityValidator;
    private MovieMapper movieMapper;

    @Override
    public void init() throws ServletException {
        repository = new MovieRepository();
        classToJson = new ClassToJsonConverter();
        jsonToClass = new JsonToClassConverter();
        entityValidator = new EntityValidator();
        movieMapper = new MovieMapper();
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        MovieDTOList movieDTOList = jsonToClass.getMovieDTOFromJSON(requestBody);
//        Movie movieToUpdate = movieMapper.mapMovieDTOToMovie(movieDTOList.getMovieList().get(0));
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        //Movie movieToPersist = movieMapper.mapMovieDTOToMovie(jsonToClass.getMovieDTOFromJSON(requestBody).getMovieList().get(0));
        String pathInfo = request.getPathInfo();

        String movieId = null;
        if (pathInfo != null)
            movieId = pathInfo.substring(1);

        if (movieId==null){
            throw new EntityIsNotValidException("Provide id of movie to update");
        }
        MovieDTO movieDTO = jsonToClass.getSingleMovieDTOFromJSON(requestBody);
        movieDTO.setId(movieId);
        Movie movieToUpdate = movieMapper.mapMovieDTOToMovie(movieDTO);


        Movie existingMovie = repository.findById(movieToUpdate.getId());
        movieToUpdate.setCreationDate(existingMovie.getCreationDate());
        entityValidator.validateMovie(movieToUpdate);
        repository.update(movieToUpdate);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Movie movieToPersist = movieMapper.mapMovieDTOToMovie(jsonToClass.getMovieDTOFromJSON(requestBody).getMovieList().get(0));

        //Movie movieToPersist = movieMapper.mapMovieDTOToMovie(jsonToClass.getSingleMovieDTOFromJSON(requestBody));


        //movieToPersist.setCreationDate(LocalDate.now());
        entityValidator.validateMovie(movieToPersist);
        repository.create(movieToPersist);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String perPage = UrlParamsUtil.getField(request, "perPage");
        String curPage = UrlParamsUtil.getField(request, "curPage");
        String sortBy = UrlParamsUtil.getField(request, "sortBy");

        String filterBy = UrlParamsUtil.getField(request, "filterBy");

        String pathInfo = request.getPathInfo();

        String movieId = null;
        if (pathInfo != null)
            movieId = pathInfo.substring(1);

        if (movieId != null) {
            Movie movie = repository.findById(Integer.parseInt(movieId));
            MovieDTO dto = movieMapper.mapMovieToMovieDTO(movie);
            response.getWriter().write(classToJson.movieToJSON(dto));
            return;
        }

        PagedMovieList pagedMovieList = repository.findAll(perPage, curPage, sortBy, filterBy);
        MovieDTOList dto = new MovieDTOList((movieMapper.mapMovieListToMovieDTOList(pagedMovieList.getMovieList())), pagedMovieList.getCount());
        response.getWriter().write(classToJson.moviesToJSON(dto));

        repository.clearEntityManager();

    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String movieId = null;
        if (pathInfo != null)
            movieId = pathInfo.substring(1);
        Movie movie = repository.findById(Integer.parseInt(movieId));
        repository.delete(movie);
    }

}