package com.mycompany.oficina.model;

import java.util.Comparator;
import com.mycompany.oficina.persistence.ClienteRepository;

/**
 * Representa um cliente da oficina;
 * estende Pessoa.
 */
public class Cliente extends Pessoa {

    public Cliente() {
        super();
    }

    public Cliente(int id, String nome, String telefone, String email,
                   String cpf, String endereco) {
        super(id, nome, telefone, email, cpf, endereco);
    }

    /**
     * Remove este cliente do sistema, delegando ao repositório.
     */
    public void remover() {
        ClienteRepository repo = new ClienteRepository();
        boolean sucesso = repo.remove(this.getId());
        if (!sucesso) {
            throw new RuntimeException("Falha ao remover cliente com ID " + this.getId());
        }
    }

    // Q16: Comparadores estáticos para Cliente
    /**
     * Comparador para ordenação por CPF.
     */
    public static Comparator<Cliente> comparatorPorCpf() {
        return Comparator.comparing(Cliente::getCpf);
    }

    /**
     * Comparador para ordenação por nome (case-insensitive).
     */
    public static Comparator<Cliente> comparatorPorNome() {
        return Comparator.comparing(Cliente::getNome, String.CASE_INSENSITIVE_ORDER);
    }
}
