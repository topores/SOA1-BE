package com.example.demo1.filters;


import com.example.demo1.dto.ExceptionDTO;
import com.example.demo1.exceptions.EntityIsNotValidException;
import com.example.demo1.exceptions.JsonParseException;
import com.example.demo1.converters.ClassToJsonConverter;
import org.hibernate.exception.ConstraintViolationException;


import javax.persistence.NoResultException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "ExceptionHandlerFilter", urlPatterns = {"/movies/*", "/persons/*",
        "/coordinates/*", "/locations/*", "/additional"})
public class ExceptionHandlerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ConstraintViolationException | EntityIsNotValidException | JsonParseException | NumberFormatException e) {
            ClassToJsonConverter javaToJson = new ClassToJsonConverter();
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(400);
            ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage());
            response.getWriter().write(javaToJson.exceprionToJSON(exceptionDTO));
        } catch (NoResultException e) {
            ClassToJsonConverter javaToJava = new ClassToJsonConverter();
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(404);
            ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage());
            response.getWriter().write(javaToJava.exceprionToJSON(exceptionDTO));
        } catch (Exception e) {
            ClassToJsonConverter javaToJava = new ClassToJsonConverter();
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(400);
            ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage());
            e.printStackTrace();
            response.getWriter().write(javaToJava.exceprionToJSON(exceptionDTO));
        }
    }
}
