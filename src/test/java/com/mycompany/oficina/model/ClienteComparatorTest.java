package com.mycompany.oficina.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteComparatorTest {

    @Test
    public void ordenaPorCpfEPorNome() {
        Cliente c1 = new Cliente(1, "Carlos", null, null, "222.222.222-22", null);
        Cliente c2 = new Cliente(2, "ana", null, null, "111.111.111-11", null);
        Cliente c3 = new Cliente(3, "Bruno", null, null, "333.333.333-33", null);

        List<Cliente> lista = new ArrayList<>(Arrays.asList(c1, c2, c3));
        lista.sort(Cliente.comparatorPorCpf());
        List<String> cpfs = lista.stream().map(Cliente::getCpf).toList();
        assertEquals(List.of("111.111.111-11", "222.222.222-22", "333.333.333-33"), cpfs);

        lista = new ArrayList<>(Arrays.asList(c1, c2, c3));
        lista.sort(Cliente.comparatorPorNome());
        List<String> nomes = lista.stream().map(Cliente::getNome).toList();
        assertEquals(List.of("ana", "Bruno", "Carlos"), nomes);
    }
}
