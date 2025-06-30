package com.mycompany.oficina.service;

import com.mycompany.oficina.model.Cliente;
import com.mycompany.oficina.model.Agendamento;
import com.mycompany.oficina.model.OrdemServico;
import com.mycompany.oficina.model.Veiculo;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SistemaTest {

    @Test
    public void buscarComIteratorVsBinarySearch() {
        Sistema sistema = new Sistema();
        List<Cliente> clientes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            clientes.add(new Cliente(i, "Cliente" + i, null, null,
                    String.format("000.000.000-%02d", i), null));
        }
        Comparator<Cliente> comp = Comparator.comparingInt(Cliente::getId);
        Cliente alvo = clientes.get(2);

        Cliente encontrado = sistema.buscarComIterator(clientes, alvo, comp);
        assertEquals(alvo, encontrado);

        clientes.sort(comp);
        int idx = Collections.binarySearch(clientes, alvo, comp);
        assertTrue(idx >= 0);
        assertEquals(alvo, clientes.get(idx));
    }

    @Test
    public void simularFluxoCliente() {
        Sistema sistema = new Sistema();
        int id = 99;
        sistema.simularFluxoCliente(id);

        assertTrue(sistema.getClienteById(id).isPresent());
        assertTrue(sistema.getVeiculoById(id).isPresent());

        boolean agendamentoExiste = sistema.getAgendamentos().stream()
                .anyMatch(a -> a.getId() == id);
        assertTrue(agendamentoExiste);

        boolean osExiste = sistema.getOrdens().stream()
                .anyMatch(o -> o.getId() == id);
        assertTrue(osExiste);
    }
}
