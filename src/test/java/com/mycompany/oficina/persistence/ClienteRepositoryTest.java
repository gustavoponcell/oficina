package com.mycompany.oficina.persistence;

import static org.junit.jupiter.api.Assertions.*;

import com.mycompany.oficina.model.Cliente;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClienteRepositoryTest {
    private final Path clientesFile = Paths.get("data", "clientes.json");

    @BeforeEach
    void setUp() throws Exception {
        Files.deleteIfExists(clientesFile);
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(clientesFile);
    }

    @Test
    void testCrudOperations() {
        ClienteRepository repo = new ClienteRepository();
        assertTrue(repo.findAll().isEmpty());

        Cliente c = new Cliente(1, "Joao", "111", "j@j.com", "123", "Rua 1");
        repo.add(c);
        assertEquals(1, repo.findAll().size());
        assertTrue(repo.findById(1).isPresent());

        c.setNome("Joao da Silva");
        assertTrue(repo.update(c));
        assertEquals("Joao da Silva", repo.findById(1).get().getNome());

        assertTrue(repo.remove(1));
        assertTrue(repo.findAll().isEmpty());
    }
}
