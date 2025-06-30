package com.mycompany.oficina;

import com.mycompany.oficina.model.Cliente;
import com.mycompany.oficina.service.Sistema;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Tests covering Q15-Q18 logic in Sistema. */
public class SistemaTest extends TestBase {

    @Test
    void testIteratorVsForeach() {
        List<Cliente> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Cliente c = new Cliente();
            c.setId(i);
            c.setNome("C" + i);
            c.setCpf("000" + i);
            list.add(c);
        }
        List<Integer> iter = new ArrayList<>();
        Iterator<Cliente> it = list.iterator();
        while (it.hasNext()) {
            iter.add(it.next().getId());
        }
        List<Integer> foreach = new ArrayList<>();
        for (Cliente c : list) {
            foreach.add(c.getId());
        }
        assertEquals(iter, foreach);
    }

    @Test
    void testComparators() {
        Cliente a = new Cliente();
        a.setId(1);
        a.setNome("B");
        a.setCpf("2");
        Cliente b = new Cliente();
        b.setId(2);
        b.setNome("A");
        b.setCpf("1");
        List<Cliente> list = Arrays.asList(a, b);

        list.sort(Cliente.comparatorPorCpf());
        assertEquals("1", list.get(0).getCpf());

        list.sort(Cliente.comparatorPorNome());
        assertEquals("A", list.get(0).getNome());
    }

    @Test
    void testBuscarComIterator() {
        Sistema sistema = new Sistema();
        List<Cliente> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Cliente c = new Cliente();
            c.setId(i);
            list.add(c);
        }
        Cliente target = list.get(3);
        Comparator<Cliente> cmp = Comparator.comparingInt(Cliente::getId);
        Cliente viaIterator = sistema.buscarComIterator(list, target, cmp);
        Collections.sort(list, cmp);
        int idx = Collections.binarySearch(list, target, cmp);
        Cliente viaBinary = list.get(idx);
        assertEquals(viaBinary, viaIterator);
    }

    @Test
    void testSimularFluxoCliente() {
        Sistema sistema = new Sistema();
        int baseClientes = sistema.getClientes().size();
        int baseOrdens = sistema.getOrdens().size();
        int baseFaturas = sistema.getFaturas().size();

        for (int i = 0; i < 10; i++) {
            sistema.simularFluxoCliente(1000 + i);
        }

        assertEquals(baseClientes + 10, sistema.getClientes().size());
        assertEquals(baseOrdens + 10, sistema.getOrdens().size());
        assertEquals(baseFaturas + 10, sistema.getFaturas().size());
    }
}
