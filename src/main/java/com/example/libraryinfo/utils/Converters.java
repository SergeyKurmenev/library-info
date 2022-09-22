package com.example.libraryinfo.utils;

import com.example.libraryinfo.entities.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class Converters {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static final Function<String[], User> STRING_ARRAY_TO_USER = row ->
            User.builder()
                    .surName(row[0])
                    .firstName(row[1])
                    .membershipStart(LocalDate.parse(row[2], DATE_TIME_FORMATTER))
                    .membershipEnd(row[3].isBlank() ? null : LocalDate.parse(row[3], DATE_TIME_FORMATTER))
                    .gender(row[4])
                    .build();
}
