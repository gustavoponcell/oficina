package com.mycompany.oficina.persistence;

import com.mycompany.oficina.model.Estoque;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório para persistência do estoque.
 * Implementa fallback para JSON mal formatado, iniciando estoque vazio em caso de erro.
 */
public class EstoqueRepository {

    private static final String FILE_NAME = "estoque.json";
    private Estoque estoque;

    public EstoqueRepository() {
        List<Estoque> list;
        try {
            Type type = new TypeToken<List<Estoque>>(){}.getType();
            list = JSONUtil.loadList(FILE_NAME, type);                // citeturn13file2
        } catch (JsonSyntaxException e) {
            // Fallback para estoque vazio se JSON estiver corrompido
            list = new ArrayList<>();                               // citeturn11file0
        }
        if (list.isEmpty()) {
            // Inicializa estoque vazio e persiste
            estoque = new Estoque();                                 
            JSONUtil.saveList(FILE_NAME, List.of(estoque));         // citeturn13file2
        } else {
            estoque = list.get(0);
        }
    }

    /** Retorna o estoque carregado ou inicializado. */
    public Estoque getEstoque() {
        return estoque;
    }

    /** Persiste o estoque atual no arquivo JSON. */
    public void save() {
        JSONUtil.saveList(FILE_NAME, List.of(estoque));             // citeturn13file2
    }
}
