package com.mycompany.oficina.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Representa um usuário do sistema, capaz de autenticar‑se por senha.
 */
public class Usuario implements Autenticavel {

    private String username;
    private String passwordHash;

    /** Construtor vazio (necessário para desserialização). */
    public Usuario() { }

    /**
     * Construtor principal.
     * @param username nome de usuário (login)
     * @param password senha em texto puro (será hasheada)
     */
    public Usuario(String username, String password) {
        this.username = username;
        setPassword(password);
    }

    /** @return o username do usuário */
    public String getUsername() {
        return username;
    }

    /** @param username define o username do usuário */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Hash da senha com SHA‑256 e armazenamento no campo passwordHash.
     * @param password senha em texto puro
     */
    public void setPassword(String password) {
        this.passwordHash = sha256(password);
    }

    /**
     * Verifica se o hash da senha fornecida confere com o hash armazenado.
     * @param senha texto da senha a validar
     * @return true se coincidir; false caso contrário
     */
    @Override
    public boolean autenticar(String senha) {
        return Objects.equals(passwordHash, sha256(senha));
    }
    
    @Override
    public String toString() {
        return username;
    }


    /**
     * Gera SHA‑256 de uma string.
     */
    private String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(2 * hashBytes.length);
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar SHA-256", e);
        }
    }
}
