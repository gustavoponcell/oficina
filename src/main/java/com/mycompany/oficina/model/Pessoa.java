package com.mycompany.oficina.model;

/**
 * Representa uma pessoa com dados básicos de identificação e contato.
 */
public class Pessoa {

    private int id;
    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    private String endereco;

    /** Construtor vazio (pode ser útil para bibliotecas de serialização). */
    public Pessoa() { }

    /**
     * Construtor completo.
     * @param id identificador único
     * @param nome nome da pessoa
     * @param telefone telefone de contato
     * @param email e‑mail de contato
     * @param cpf CPF
     * @param endereco endereço
     */
    public Pessoa(int id, String nome, String telefone, String email, String cpf, String endereco) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    /** @return o identificador da pessoa */
    public int getId() {
        return id;
    }

    /** @param id define o identificador da pessoa */
    public void setId(int id) {
        this.id = id;
    }

    /** @return o nome da pessoa */
    public String getNome() {
        return nome;
    }

    /** @param nome define o nome da pessoa */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** @return o telefone de contato */
    public String getTelefone() {
        return telefone;
    }

    /** @param telefone define o telefone de contato */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /** @return o e‑mail de contato */
    public String getEmail() {
        return email;
    }

    /** @param email define o e‑mail de contato */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return o CPF */
    public String getCpf() {
        return cpf;
    }

    /** @param cpf define o CPF */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /** @return o endereço */
    public String getEndereco() {
        return endereco;
    }

    /** @param endereco define o endereço */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", telefone='" + telefone + '\'' +
               ", email='" + email + '\'' +
               ", cpf='" + cpf + '\'' +
               ", endereco='" + endereco + '\'' +
               '}';
    }
}
