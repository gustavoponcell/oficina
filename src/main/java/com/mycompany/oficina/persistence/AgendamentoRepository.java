package com.mycompany.oficina.persistence;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mycompany.oficina.model.Agendamento;

import java.io.EOFException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações CRUD de Agendamento em JSON.
 */
public class AgendamentoRepository {

    private static final String FILE_NAME = "agendamentos.json";
    private final Type TYPE = new TypeToken<List<Agendamento>>(){}.getType();

    private List<Agendamento> agendamentos;

    /**
     * Construtor: carrega agendamentos do arquivo JSON (ou lista vazia em caso de erro).
     */
    public AgendamentoRepository() {
        try {
            this.agendamentos = JSONUtil.loadList(FILE_NAME, TYPE);
        } catch (JsonSyntaxException e) {
            // JSON vazio ou malformado: inicia com lista vazia
            this.agendamentos = new ArrayList<>();
        }
    }

    /**
     * Retorna todos os agendamentos.
     */
    public List<Agendamento> findAll() {
        return agendamentos;
    }

    /**
     * Busca um agendamento por ID.
     * @param id identificador do agendamento
     * @return Optional de Agendamento
     */
    public Optional<Agendamento> findById(int id) {
        return agendamentos.stream()
                           .filter(a -> a.getId() == id)
                           .findFirst();
    }

    /**
     * Adiciona um novo agendamento e persiste.
     */
    public void add(Agendamento agendamento) {
        agendamentos.add(agendamento);
        save();
    }

    /**
     * Atualiza um agendamento existente (por ID) e persiste.
     * @param agendamento objeto com dados atualizados
     * @return true se atualizado; false caso não exista
     */
    public boolean update(Agendamento agendamento) {
        Optional<Agendamento> opt = findById(agendamento.getId());
        if (opt.isPresent()) {
            int idx = agendamentos.indexOf(opt.get());
            agendamentos.set(idx, agendamento);
            save();
            return true;
        }
        return false;
    }

    /**
     * Remove agendamento por ID e persiste.
     * @param id identificador do agendamento
     * @return true se removido; false caso não exista
     */
    public boolean remove(int id) {
        Optional<Agendamento> opt = findById(id);
        if (opt.isPresent()) {
            agendamentos.remove(opt.get());
            save();
            return true;
        }
        return false;
    }

    /**
     * Persiste a lista de agendamentos em JSON.
     */
    private void save() {
        JSONUtil.saveList(FILE_NAME, agendamentos);
    }

    public List<Agendamento> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
