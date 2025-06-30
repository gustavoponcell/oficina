package com.mycompany.oficina.util;

import java.util.regex.Pattern;

/**
 * Utilitário para validações genéricas de dados de entrada.
 */
public class InputValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^\\(?\\d{2}\\)?[ ]?\\d{4,5}-?\\d{4}$"
    );
    private static final Pattern CPF_PATTERN = Pattern.compile(
        "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$"
    );

    /** Verifica se a string é nula ou vazia. */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    /** Valida e‑mail simples. */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /** Valida telefone no formato (XX)XXXXX-XXXX ou similar. */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    /** Valida CPF no formato XXX.XXX.XXX-XX. */
    public static boolean isValidCpf(String cpf) {
        return cpf != null && CPF_PATTERN.matcher(cpf).matches();
    }

    /** Verifica se a string representa número inteiro. */
    public static boolean isInteger(String s) {
        if (isNullOrEmpty(s)) return false;
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Verifica se a string representa número decimal. */
    public static boolean isDouble(String s) {
        if (isNullOrEmpty(s)) return false;
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Verifica se um número é positivo (>= 0). */
    public static boolean isNonNegative(double value) {
        return value >= 0;
    }
}