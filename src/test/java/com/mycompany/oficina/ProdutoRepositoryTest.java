package com.mycompany.oficina;

import com.mycompany.oficina.model.Produto;
import com.mycompany.oficina.persistence.ProdutoRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Basic CRUD tests for ProdutoRepository. */
public class ProdutoRepositoryTest extends TestBase {
    @Test
    void testCrud() {
        ProdutoRepository repo = new ProdutoRepository();
        int initial = repo.findAll().size();

        Produto p = new Produto(9999, "Peça Teste", 5.0);
        repo.add(p);
        assertEquals(initial + 1, repo.findAll().size());
        assertTrue(repo.findById(9999).isPresent());

        Produto updated = new Produto(9999, "Peça Nova", 8.0);
        repo.update(updated);
        assertEquals("Peça Nova", repo.findById(9999).get().getNome());

        assertTrue(repo.remove(9999));
        assertEquals(initial, repo.findAll().size());
    }
}
