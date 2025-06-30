package com.mycompany.oficina;

import com.mycompany.oficina.model.Cliente;
import com.mycompany.oficina.service.Sistema;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Exercita funcionalidades solicitadas no README Q15-Q18.
 */
public class TestOficina {

    @Test
    public void testListagemIteratorForeach() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente c1 = novoCliente(1, "Carlos", "333");
        Cliente c2 = novoCliente(2, "Ana", "111");
        Cliente c3 = novoCliente(3, "Bruno", "222");
        Collections.addAll(clientes, c1, c2, c3);

        System.out.println("Listagem via Iterator:");
        StringBuilder sbIt = new StringBuilder();
        for (Iterator<Cliente> it = clientes.iterator(); it.hasNext(); ) {
            Cliente c = it.next();
            System.out.println("  " + c.getNome());
            sbIt.append(c.getNome());
        }

        System.out.println("Listagem via for-each:");
        StringBuilder sbFor = new StringBuilder();
        for (Cliente c : clientes) {
            System.out.println("  " + c.getNome());
            sbFor.append(c.getNome());
        }

        assertEquals(sbIt.toString(), sbFor.toString());
    }

    @Test
    public void testOrdenacaoComparators() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente c1 = novoCliente(1, "Carlos", "333");
        Cliente c2 = novoCliente(2, "Ana", "111");
        Cliente c3 = novoCliente(3, "Bruno", "222");
        Collections.addAll(clientes, c1, c2, c3);

        Collections.sort(clientes, Cliente.comparatorPorCpf());
        System.out.println("Ordenado por CPF:");
        clientes.forEach(c -> System.out.println("  " + c.getCpf()));
        assertEquals("111", clientes.get(0).getCpf());
        assertEquals("222", clientes.get(1).getCpf());
        assertEquals("333", clientes.get(2).getCpf());

        Collections.sort(clientes, Cliente.comparatorPorNome());
        System.out.println("Ordenado por Nome:");
        clientes.forEach(c -> System.out.println("  " + c.getNome()));
        assertEquals("Ana", clientes.get(0).getNome());
        assertEquals("Bruno", clientes.get(1).getNome());
        assertEquals("Carlos", clientes.get(2).getNome());
    }

    @Test
    public void testComparacaoTemposBusca() {
        List<Cliente> clientes = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            clientes.add(novoCliente(i, "Cli" + i, String.format("%03d", i)));
        }
        Cliente alvo = clientes.get(500);
        Sistema sis = new Sistema();

        long startIt = System.nanoTime();
        Cliente achadoIt = sis.buscarComIterator(clientes, alvo,
                Comparator.comparingInt(Cliente::getId));
        long durItUs = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - startIt);
        System.out.println("Iterator encontrou " + achadoIt.getNome() + " em " + durItUs + " µs");

        Collections.sort(clientes, Comparator.comparingInt(Cliente::getId));
        long startBs = System.nanoTime();
        int idx = Collections.binarySearch(clientes, alvo,
                Comparator.comparingInt(Cliente::getId));
        long durBsUs = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - startBs);
        System.out.println("BinarySearch encontrou " + clientes.get(idx).getNome() + " em " + durBsUs + " µs");

        assertNotNull(achadoIt);
        assertTrue(idx >= 0);
    }

    @Test
    public void testSimularFluxoDezClientes() {
        Sistema sis = new Sistema();
        assertDoesNotThrow(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Simulando cliente " + i);
                sis.simularFluxoCliente(i);
            }
        });
    }

    private static Cliente novoCliente(int id, String nome, String cpf) {
        Cliente c = new Cliente();
        c.setId(id);
        c.setNome(nome);
        c.setCpf(cpf);
        return c;
    }
}
