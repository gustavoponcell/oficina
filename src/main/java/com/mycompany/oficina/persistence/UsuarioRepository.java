package com.mycompany.oficina.persistence;

import com.google.gson.reflect.TypeToken;
import com.mycompany.oficina.model.Usuario;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações CRUD de Usuário em JSON.
 */
public class UsuarioRepository {

    private static final String FILE_NAME = "usuarios.json";
    private List<Usuario> usuarios;
    private final Type TYPE = new TypeToken<List<Usuario>>(){}.getType();

    /**
     * Construtor: carrega usuários do arquivo JSON (ou lista vazia).
     */
    public UsuarioRepository() {
        this.usuarios = JSONUtil.loadList(FILE_NAME, TYPE);
    }

    /**
     * Retorna todos os usuários.
     */
    public List<Usuario> findAll() {
        return usuarios;
    }

    /**
     * Busca um usuário pelo username.
     * @param username nome de usuário
     * @return Optional de Usuario
     */
    public Optional<Usuario> findByUsername(String username) {
        return usuarios.stream()
                       .filter(u -> u.getUsername().equals(username))
                       .findFirst();
    }

    /**
     * Adiciona um novo usuário e persiste.
     */
    public void add(Usuario usuario) {
        usuarios.add(usuario);
        save();
    }

    /**
     * Atualiza um usuário existente (por username) e persiste.
     * @param usuario objeto com dados atualizados
     * @return true se atualizado; false caso não exista
     */
    public boolean update(Usuario usuario) {
        Optional<Usuario> opt = findByUsername(usuario.getUsername());
        if (opt.isPresent()) {
            int idx = usuarios.indexOf(opt.get());
            usuarios.set(idx, usuario);
            save();
            return true;
        }
        return false;
    }

    /**
     * Remove usuário por username e persiste.
     * @param username nome de usuário
     * @return true se removido; false caso não exista
     */
    public boolean remove(String username) {
        Optional<Usuario> opt = findByUsername(username);
        if (opt.isPresent()) {
            usuarios.remove(opt.get());
            save();
            return true;
        }
        return false;
    }

    /**
     * Persiste a lista de usuários em JSON.
     */
    private void save() {
        JSONUtil.saveList(FILE_NAME, usuarios);
    }
}
