package com.mycompany.oficina;

import com.mycompany.oficina.model.*;
import com.mycompany.oficina.persistence.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ColaboradorFornecedorTest {

    @BeforeEach
    public void clean() {
        new File("data/agendamentos.json").delete();
        new File("data/clientes.json").delete();
        new File("data/estoque.json").delete();
    }

    @Test
    public void testColaboradorClienteAgendamento() {
        Colaborador col = new Colaborador();
        Cliente c = new Cliente(1,"C","","","cpf","end");
        col.incluirCliente(c);
        ClienteRepository crepo = new ClienteRepository();
        assertTrue(crepo.findById(1).isPresent());

        c.setNome("Novo");
        col.editarCliente(c);
        crepo = new ClienteRepository();
        assertEquals("Novo", crepo.findById(1).get().getNome());

        Agendamento ag = new Agendamento();
        ag.setId(1);
        ag.setCliente(c);
        col.registrarAgendamento(ag);
        AgendamentoRepository arepo = new AgendamentoRepository();
        assertTrue(arepo.findById(1).isPresent());

        col.cancelarAgendamento(ag);
        arepo = new AgendamentoRepository();
        assertFalse(arepo.findById(1).isPresent());

        col.removerCliente(1);
        crepo = new ClienteRepository();
        assertFalse(crepo.findById(1).isPresent());
    }

    @Test
    public void testFornecedorEntrega() {
        Produto p = new Produto(1,"P",0.0);
        Fornecedor f = new Fornecedor();
        f.entregarProdutos(Map.of(p,5));
        EstoqueRepository erepo = new EstoqueRepository();
        assertEquals(5, erepo.getEstoque().verificarEstoque(p));
    }
}
