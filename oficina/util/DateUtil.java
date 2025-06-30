package com.mycompany.oficina.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Utilitário para formatação e parsing de datas e horários.
 */
public class DateUtil {

    private static final String DEFAULT_DATE_PATTERN = "dd/MM/yyyy";
    private static final String DEFAULT_DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);

    /**
     * Formata um LocalDate usando o padrão (dd/MM/yyyy).
     */
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }

    /**
     * Formata um LocalDateTime usando o padrão (dd/MM/yyyy HH:mm).
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : null;
    }

    /**
     * Faz parsing de uma string para LocalDate usando o padrão.
     */
    public static LocalDate parseDate(String dateString) {
        return dateString != null ? LocalDate.parse(dateString, DATE_FORMATTER) : null;
    }

    /**
     * Faz parsing de uma string para LocalDateTime usando o padrão.
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        return dateTimeString != null ? LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER) : null;
    }

    /**
     * Converte LocalDate para java.util.Date no início do dia.
     */
    public static Date toDate(LocalDate localDate) {
        return localDate != null ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
    }

    /**
     * Converte LocalDateTime para java.util.Date.
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return localDateTime != null ? Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    /**
     * Converte java.util.Date para LocalDate.
     */
    public static LocalDate toLocalDate(Date date) {
        return date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    /**
     * Converte java.util.Date para LocalDateTime.
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date != null ? LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()) : null;
    }
}