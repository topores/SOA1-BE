package com.example.demo1.utils;

import com.example.demo1.exceptions.EntityIsNotValidException;
import com.example.demo1.model.enums.MovieGenre;
import com.example.demo1.model.enums.MpaaRating;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FieldValidatorUtil {

    public static Integer getIntegerFieldValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot parse integer value from " + value);
        }
    }

    public static Double getDoubleFieldValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot parse double value from " + value);
        }
    }

    public static Float getFloatFieldValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot parse double value from " + value);
        }
    }

    public static Long getLongFieldValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot parse double value from " + value);
        }
    }

    public static ZonedDateTime getZonedDateTimeFieldValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z");
//            String formattedString = zonedDateTime.format(formatter);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(value, formatter);
            ZonedDateTime dateTime = date.atStartOfDay(ZoneId.systemDefault());
            System.out.println(dateTime);
            return dateTime;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot parse double value from " + value);
        }
    }

    public static String getStringValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        return value;
    }


    public static MovieGenre getMovieGenreValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return MovieGenre.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new EntityIsNotValidException("Movie genre does not exist " + value);
        }
    }

    public static MpaaRating getMpaaRatingValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return MpaaRating.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new EntityIsNotValidException("Mpaa rating does not exist " + value);
        }
    }

    private static boolean isEmptyOrNull(String value) {
        if (value == null || value.equals("null"))
            return true;
        value = value.trim();
        return value.isEmpty();
    }

    public static Date getDateValue(String value) {
        if (isEmptyOrNull(value))
            return null;
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(value);
        } catch (IllegalArgumentException | ParseException e) {
            throw new EntityIsNotValidException("Date is incorrect" + value);
        }


    }
}

