package com.mycompany.oficina;

import com.mycompany.oficina.model.Cliente;
import com.mycompany.oficina.persistence.ClienteRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Basic CRUD tests for ClienteRepository. */
public class ClienteRepositoryTest extends TestBase {
    @Test
    void testCrud() {
        ClienteRepository repo = new ClienteRepository();
        int initial = repo.findAll().size();

        Cliente c = new Cliente();
        c.setId(9999);
        c.setNome("JUnit");
        c.setCpf("999.999.999-99");

        repo.add(c);
        assertEquals(initial + 1, repo.findAll().size());
        Optional<Cliente> loaded = repo.findById(9999);
        assertTrue(loaded.isPresent());
        assertEquals("JUnit", loaded.get().getNome());

        c.setNome("Updated");
        assertTrue(repo.update(c));
        assertEquals("Updated", repo.findById(9999).get().getNome());

        assertTrue(repo.remove(9999));
        assertEquals(initial, repo.findAll().size());
    }
}
