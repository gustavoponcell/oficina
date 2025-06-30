package com.mycompany.oficina.model;

/**
 * Contrato para qualquer classe que ofereça método de autenticação por senha.
 */
public interface Autenticavel {
    /**
     * Verifica se a senha fornecida confere com as credenciais armazenadas.
     * @param senha texto da senha a ser validada
     * @return true se a senha for válida; false caso contrário
     */
    boolean autenticar(String senha);
}
