package com.mycompany.oficina.service;

import com.mycompany.oficina.model.*;
import com.mycompany.oficina.util.DateUtil;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class SistemaTest {
    @Test
    public void testEmitirRelatorioDia() {
        Sistema sis = new Sistema();

        Cliente c = new Cliente();
        c.setId(1);
        c.setNome("Teste");
        sis.addCliente(c);

        Veiculo v = new Veiculo();
        v.setId(1);
        v.setPlaca("AAA-1111");
        v.setCliente(c);
        sis.addVeiculo(v);

        Agendamento ag = new Agendamento();
        ag.setId(1);
        ag.setCliente(c);
        ag.setVeiculo(v);
        ag.setDataHora(LocalDateTime.now());
        sis.registrarAgendamento(ag);

        OrdemServico os = new OrdemServico();
        os.setId(1);
        os.setAgendamento(ag);
        sis.registrarOrdemServico(os);

        Date dia = DateUtil.toDate(ag.getDataHora().toLocalDate());
        Relatorio rel = sis.emitirRelatorioDia(dia);

        assertTrue(rel instanceof RelatorioVendas);
        RelatorioVendas rv = (RelatorioVendas) rel;
        assertEquals(1, rv.getOrdens().size());
    }

    @Test
    public void testGerarBalancoMes() {
        Sistema sis = new Sistema();

        Despesa desp = new Despesa(1, "teste", "desc", new Date(), 100.0);
        sis.registrarDespesa(desp);

        Fatura fat = new Fatura(1, new Date(), 200.0, null);
        sis.registrarFatura(fat);

        BalancoMensal b = sis.gerarBalancoMes(DateUtil.toLocalDate(new Date()).getMonthValue(),
                                               DateUtil.toLocalDate(new Date()).getYear());
        assertEquals(200.0, b.getReceitas(), 0.01);
        assertEquals(100.0, b.getDespesas(), 0.01);
    }
}
