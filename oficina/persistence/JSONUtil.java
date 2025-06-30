package com.mycompany.oficina.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import com.mycompany.oficina.model.Usuario;
import com.mycompany.oficina.model.Administrador;
import com.mycompany.oficina.model.Colaborador;
import com.mycompany.oficina.util.LocalDateTimeAdapter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilitário genérico para carregar e salvar listas de objetos em arquivos JSON.
 * Registra adaptadores para subclasses de Usuario e para LocalDateTime.
 */
public class JSONUtil {

    /**
     * TypeAdapterFactory que injeta o campo "type" para o polimorfismo de Usuario.
     */
    private static final RuntimeTypeAdapterFactory<Usuario> usuarioTypeAdapter =
        RuntimeTypeAdapterFactory.of(Usuario.class, "type")
            .registerSubtype(Usuario.class, "usuario")
            .registerSubtype(Administrador.class, "administrador")
            .registerSubtype(Colaborador.class, "colaborador");

    /**
     * Instância Gson configurada para lidar com polimorfismo e LocalDateTime.
     */
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapterFactory(usuarioTypeAdapter)
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .enableComplexMapKeySerialization()
        .setPrettyPrinting()
        .create();

    private static final String DATA_DIR = "data";

    static {
        // Garante que o diretório de dados exista
        Path dataPath = Paths.get(DATA_DIR);
        if (!Files.exists(dataPath)) {
            try {
                Files.createDirectory(dataPath);
            } catch (IOException e) {
                throw new RuntimeException("Não foi possível criar diretório de dados", e);
            }
        }
    }

    /**
     * Carrega uma lista de objetos do tipo especificado a partir de um arquivo JSON.
     * @param fileName nome do arquivo dentro de data/, ex: "usuarios.json"
     * @param type Tipo obtido via TypeToken, ex: {@code new TypeToken<List<Usuario>>(){}.getType()}
     * @return lista carregada ou vazia se não houver arquivo
     */
    public static <T> List<T> loadList(String fileName, Type type) {
        Path filePath = Paths.get(DATA_DIR, fileName);
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }
        try (Reader reader = Files.newBufferedReader(filePath)) {
            List<T> list = gson.fromJson(reader, type);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo JSON: " + fileName, e);
        }
    }

    /**
     * Salva uma lista de objetos em um arquivo JSON no formato com campo "type".
     * @param fileName nome do arquivo dentro de data/, ex: "usuarios.json"
     * @param list lista de objetos a salvar
     */
    public static <T> void saveList(String fileName, List<T> list) {
        Path filePath = Paths.get(DATA_DIR, fileName);
        try (Writer writer = Files.newBufferedWriter(filePath)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar arquivo JSON: " + fileName, e);
        }
    }
}
