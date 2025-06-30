package com.mycompany.oficina;

import com.mycompany.oficina.model.*;
import com.mycompany.oficina.persistence.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AdministradorTest {

    @BeforeEach
    public void clean() {
        new File("data/clientes.json").delete();
        new File("data/despesas.json").delete();
        new File("data/faturas.json").delete();
        new File("data/estoque.json").delete();
    }

    @Test
    public void testGerenciarClientesEDespesas() {
        Administrador adm = new Administrador();
        Cliente c = new Cliente(1,"Fulano","","","cpf","end");
        adm.incluirCliente(c);
        ClienteRepository repo = new ClienteRepository();
        assertTrue(repo.findById(1).isPresent());

        c.setNome("Beltrano");
        adm.editarCliente(c);
        repo = new ClienteRepository();
        assertEquals("Beltrano", repo.findById(1).get().getNome());

        adm.removerCliente(1);
        repo = new ClienteRepository();
        assertFalse(repo.findById(1).isPresent());

        Despesa d = new Despesa();
        d.setId(1);
        d.setValor(10.0);
        d.setData(new java.util.Date());
        adm.registrarDespesa(d);
        DespesaRepository drepo = new DespesaRepository();
        assertEquals(1, drepo.findAll().size());
    }

    @Test
    public void testGerarBalanco() {
        // prepare faturas e despesas
        FaturaRepository frepo = new FaturaRepository();
        DespesaRepository drepo = new DespesaRepository();

        java.util.Date data = new java.util.Date();
        Fatura f = new Fatura(1,data,50.0,new Cliente());
        frepo.add(f);

        Despesa d = new Despesa(1,"t","desc",data,20.0);
        drepo.add(d);

        Administrador adm = new Administrador();
        BalancoMensal b = adm.gerarBalanco(data.getMonth()+1, data.getYear()+1900);
        assertEquals(50.0, b.getReceitas(), 0.001);
        assertEquals(20.0, b.getDespesas(), 0.001);
    }
}
