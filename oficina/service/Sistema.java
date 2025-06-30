package com.mycompany.oficina.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.mycompany.oficina.model.Agendamento;
import com.mycompany.oficina.model.BalancoMensal;
import com.mycompany.oficina.model.Cliente;
import com.mycompany.oficina.model.Despesa;
import com.mycompany.oficina.model.Elevador;
import com.mycompany.oficina.model.Fatura;
import com.mycompany.oficina.model.Fornecedor;
import com.mycompany.oficina.model.OrdemServico;
import com.mycompany.oficina.model.Produto;
import com.mycompany.oficina.model.Relatorio;
import com.mycompany.oficina.model.RelatorioVendas;
import com.mycompany.oficina.model.Usuario;
import com.mycompany.oficina.model.Veiculo;
import com.mycompany.oficina.model.Estoque;

import com.mycompany.oficina.persistence.AgendamentoRepository;
import com.mycompany.oficina.persistence.ClienteRepository;
import com.mycompany.oficina.persistence.DespesaRepository;
import com.mycompany.oficina.persistence.ElevadorRepository;
import com.mycompany.oficina.persistence.FaturaRepository;
import com.mycompany.oficina.persistence.FornecedorRepository;
import com.mycompany.oficina.persistence.OrdemServicoRepository;
import com.mycompany.oficina.persistence.ProdutoRepository;
import com.mycompany.oficina.persistence.RelatorioRepository;
import com.mycompany.oficina.persistence.UsuarioRepository;
import com.mycompany.oficina.persistence.VeiculoRepository;
import com.mycompany.oficina.persistence.EstoqueRepository;

/**
 * Serviço principal da Oficina, centralizando operações de domínio.
 */
public class Sistema {
    // repositórios de domínio
    private final UsuarioRepository       usuarioRepo     = new UsuarioRepository();
    private final ClienteRepository       clienteRepo     = new ClienteRepository();
    private final VeiculoRepository       veiculoRepo     = new VeiculoRepository();
    private final AgendamentoRepository   agendamentoRepo = new AgendamentoRepository();
    private final OrdemServicoRepository  ordemRepo       = new OrdemServicoRepository();
    private final DespesaRepository       despesaRepo     = new DespesaRepository();
    private final FaturaRepository        faturaRepo      = new FaturaRepository();
    private final FornecedorRepository    fornecedorRepo  = new FornecedorRepository();
    private final ElevadorRepository      elevadorRepo    = new ElevadorRepository();
    private final RelatorioRepository     relatorioRepo   = new RelatorioRepository();
    private final EstoqueRepository       estoqueRepo     = new EstoqueRepository();
    private final ProdutoRepository       produtoRepo     = new ProdutoRepository();

    // ===== Usuários =====
    public void addUsuario(Usuario u) {
        usuarioRepo.add(u);
    }
    public Usuario autenticar(String username, String pass) {
        return usuarioRepo.findByUsername(username)
            .filter(u -> u.autenticar(pass))
            .orElse(null);
    }
    public List<Usuario> getUsuarios() {
        return usuarioRepo.findAll();
    }
    public boolean removeUsuario(String username) {
        return usuarioRepo.remove(username);
    }
    public void updateUsuario(Usuario u) {
        usuarioRepo.update(u);
    }

    // ===== Produtos =====
    public void addProduto(Produto p) {
        produtoRepo.add(p);
    }
    public Optional<Produto> getProdutoById(int id) {
        return produtoRepo.findById(id);
    }
    public List<Produto> getProdutos() {
        return produtoRepo.findAll();
    }
    public boolean removeProduto(int id) {
        return produtoRepo.remove(id);
    }
    public void updateProduto(Produto p) {
        produtoRepo.update(p);
    }

    // ===== Clientes =====
    public void addCliente(Cliente c)                   { clienteRepo.add(c); }
    public Optional<Cliente> getClienteById(int id)     { return clienteRepo.findById(id); }
    public List<Cliente> getClientes()                 { return clienteRepo.findAll(); }
    public boolean removeCliente(int id)                { return clienteRepo.remove(id); }
    public void updateCliente(Cliente c)                { clienteRepo.update(c); }

    // ===== Veículos =====
    public void addVeiculo(Veiculo v)                   { veiculoRepo.add(v); }
    public Optional<Veiculo> getVeiculoById(int id)     { return veiculoRepo.findById(id); }
    public List<Veiculo> getVeiculos()                 { return veiculoRepo.findAll(); }
    public boolean removeVeiculo(int id)                { return veiculoRepo.remove(id); }
    public void updateVeiculo(Veiculo v)                { veiculoRepo.update(v); }

    // ===== Agendamentos =====
    public void registrarAgendamento(Agendamento ag)    { agendamentoRepo.add(ag); }
    public void updateAgendamento(Agendamento ag)       { agendamentoRepo.update(ag); }
    public List<Agendamento> getAgendamentos()         { return agendamentoRepo.findAll(); }
    public void cancelarAgendamento(int id)            { agendamentoRepo.remove(id); }

    // ===== Ordens de Serviço =====
    public void registrarOrdemServico(OrdemServico os)  { ordemRepo.add(os); }
    public void updateOrdemServico(OrdemServico os)     { ordemRepo.update(os); }
    public List<OrdemServico> getOrdens()              { return ordemRepo.findAll(); }
    public List<OrdemServico> getOrdensPorCliente(int clienteId) {
        return ordemRepo.findAll().stream()
                        .filter(os -> os.getCliente().getId() == clienteId)
                        .collect(Collectors.toList());
    }

    // ===== Despesas =====
    public void registrarDespesa(Despesa d)             { despesaRepo.add(d); }
    public List<Despesa> getDespesas()                 { return despesaRepo.findAll(); }

    // ===== Faturas =====
    public void registrarFatura(Fatura f)               { faturaRepo.add(f); }
    public List<Fatura> getFaturas()                   { return faturaRepo.findAll(); }

    // ===== Fornecedores =====
    public void registrarFornecedor(Fornecedor f)       { fornecedorRepo.add(f); }
    public List<Fornecedor> getFornecedores()          { return fornecedorRepo.findAll(); }

    // ===== Elevadores =====
    public List<Elevador> getElevadores()              { return elevadorRepo.findAll(); }

    // ===== Relatórios =====
    public Relatorio emitirRelatorioDia(Date dia)      { /* existente */ return null; }
    public RelatorioVendas emitirRelatorioMensal(int mes, int ano) { /* existente */ return null; }
    public BalancoMensal gerarBalancoMes(int mes, int ano)        { /* existente */ return null; }

    // ===== Estoque =====
    public Estoque getEstoque()                        { return estoqueRepo.getEstoque(); }
    public void receberCompra(Map<Produto, Integer> itensCompra) {
        Estoque estoque = estoqueRepo.getEstoque();
        estoque.receberCompra(itensCompra);
        estoqueRepo.save();
    }
    public void removerEstoque(Produto p, int quantidade) {
        Estoque estoque = estoqueRepo.getEstoque();
        estoque.removerEstoque(p, quantidade);
        estoqueRepo.save();
    }

    /**
     * Q17: busca genérica usando Iterator + Comparator
     */
    public <T> T buscarComIterator(List<T> lista, T alvo, Comparator<T> comparator) {
        for (Iterator<T> it = lista.iterator(); it.hasNext(); ) {
            T item = it.next();
            if (comparator.compare(item, alvo) == 0) {
                return item;
            }
        }
        return null;
    }

    /**
     * Q18: simula todo o fluxo de 1 cliente (pode ser chamado 10x)
     */
    public void simularFluxoCliente(int id) {
        System.out.println("-> Iniciando fluxo do Cliente " + id);

        // 1) Cadastro do cliente
        Cliente c = new Cliente();
        c.setId(id);
        c.setNome("Cliente Teste " + id);
        c.setCpf(String.format("000.111.222-%02d", id));
        addCliente(c);
        System.out.println("   Cliente cadastrado: " + c.getNome());

        // 2) Criar e cadastrar veículo
        Veiculo v = new Veiculo();
        v.setId(id);
        v.setPlaca("TESTE-" + id);
        v.setModelo("Modelo-" + id);
        v.setCliente(c);
        addVeiculo(v);
        System.out.println("   Veículo cadastrado: " + v.getModelo() + " - " + v.getPlaca());

        // 3) Registrar agendamento
        Agendamento ag = new Agendamento();
        ag.setId(id);
        ag.setDataHora(LocalDateTime.now().plusDays(id));
        ag.setCliente(c);
        ag.setVeiculo(v);
        registrarAgendamento(ag);
        System.out.println("   Agendamento registrado para " + ag.getDataHora());

        // 4) Registrar ordem de serviço
        OrdemServico os = new OrdemServico();
        os.setId(id);
        os.setAgendamento(ag);
        registrarOrdemServico(os);
        System.out.println("   Ordem de serviço criada: OS " + os.getId());

        // 5) Garantir estoque e remover com retry
        Produto teste = new Produto(1, "Peça Genérica", 10.0);
        Map<Produto, Integer> compra = Collections.singletonMap(teste, 2);
        System.out.println("   Verificando estoque para peças ...");
        try {
            receberCompra(compra);
            removerEstoque(teste, 2);
        } catch (IllegalArgumentException ex) {
            System.out.println("   Estoque insuficiente, realizando nova compra ...");
            receberCompra(Collections.singletonMap(teste, 10));
            removerEstoque(teste, 2);
        }

        // 6) Emissão de nota fiscal
        os.calcularValor();
        os.gerarNotaFiscal();
        System.out.println("   Nota fiscal emitida para " + c.getNome());
    }
}
