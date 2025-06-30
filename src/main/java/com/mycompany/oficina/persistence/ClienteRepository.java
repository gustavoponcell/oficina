package com.mycompany.oficina.persistence;

import com.google.gson.reflect.TypeToken;
import com.mycompany.oficina.model.Cliente;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações CRUD de Cliente em JSON.
 */
public class ClienteRepository {

    private static final String FILE_NAME = "clientes.json";
    private List<Cliente> clientes;
    private final Type TYPE = new TypeToken<List<Cliente>>(){}.getType();

    /**
     * Construtor: carrega clientes do arquivo JSON (ou lista vazia).
     */
    public ClienteRepository() {
        this.clientes = JSONUtil.loadList(FILE_NAME, TYPE);
    }

    /**
     * Retorna todos os clientes.
     */
    public List<Cliente> findAll() {
        return clientes;
    }

    /**
     * Busca um cliente por ID.
     * @return Optional de Cliente
     */
    public Optional<Cliente> findById(int id) {
        return clientes.stream()
                       .filter(c -> c.getId() == id)
                       .findFirst();
    }

    /**
     * Adiciona um novo cliente e persiste.
     */
    public void add(Cliente cliente) {
        clientes.add(cliente);
        save();
    }

    /**
     * Atualiza um cliente existente (por ID) e persiste.
     */
    public boolean update(Cliente cliente) {
        Optional<Cliente> opt = findById(cliente.getId());
        if (opt.isPresent()) {
            Cliente original = opt.get();
            int idx = clientes.indexOf(original);
            clientes.set(idx, cliente);
            save();
            return true;
        }
        return false;
    }

    /**
     * Remove cliente por ID e persiste.
     */
    public boolean remove(int id) {
        Optional<Cliente> opt = findById(id);
        if (opt.isPresent()) {
            clientes.remove(opt.get());
            save();
            return true;
        }
        return false;
    }

    /**
     * Persiste a lista de clientes em JSON.
     */
    private void save() {
        JSONUtil.saveList(FILE_NAME, clientes);
    }
}
