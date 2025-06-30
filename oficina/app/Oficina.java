package com.mycompany.oficina.app;

import com.mycompany.oficina.model.Administrador;
import com.mycompany.oficina.model.Colaborador;
import com.mycompany.oficina.model.Usuario;
import com.mycompany.oficina.model.Cliente;
import com.mycompany.oficina.model.Veiculo;
import com.mycompany.oficina.model.Agendamento;
import com.mycompany.oficina.model.OrdemServico;
import com.mycompany.oficina.model.Produto;
import com.mycompany.oficina.model.Despesa;
import com.mycompany.oficina.model.Relatorio;
import com.mycompany.oficina.model.RelatorioVendas;
import com.mycompany.oficina.util.DateUtil;
import com.mycompany.oficina.util.InputValidator;
import com.mycompany.oficina.service.Sistema;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Classe de interface de console para a Oficina.
 */
public class Oficina {

    private static final Scanner SC = new Scanner(System.in);
    private static final Sistema sistema = new Sistema();
    private static Usuario usuarioLogado;

    public static void main(String[] args) {
        System.out.println("=== Bem-vindo à Oficina ===");
        ensureAdminExists();
        Usuario user = login();
        usuarioLogado = user;
        if (user instanceof Administrador) {
            menuAdministrador();
        } else if (user instanceof Colaborador) {
            menuColaborador();
        } else {
            System.out.println("Usuário sem perfil ou inválido. Encerrando.");
        }
        System.out.println("Obrigado por usar o sistema. Até logo!");
        SC.close();
    }

    private static void ensureAdminExists() {
        List<Usuario> usuarios = sistema.getUsuarios();
        boolean hasAdmin = usuarios.stream().anyMatch(u -> u instanceof Administrador);
        if (!hasAdmin) {
            System.out.println("=== Cadastro do Primeiro Administrador ===");
            System.out.print("Digite o nome de usuário: ");
            String login = SC.nextLine();
            System.out.print("Digite a senha: ");
            String pass = SC.nextLine();
            sistema.addUsuario(new Administrador(login, pass));
            System.out.println("Administrador cadastrado com sucesso!\n");
        }
    }

    private static Usuario login() {
        System.out.print("Login: ");
        String user = SC.nextLine();
        System.out.print("Senha: ");
        String pass = SC.nextLine();
        Usuario u = sistema.autenticar(user, pass);
        if (u == null) {
            System.out.println("Credenciais inválidas. Tente novamente.");
            return login();
        }
        System.out.println("Login bem-sucedido: " + u.getUsername() + "\n");
        return u;
    }

    private static void menuAdministrador() {
        int opc;
        do {
            System.out.println("--- Menu Administrador ---");
            System.out.println("1. Gerenciar Clientes");
            System.out.println("2. Gerenciar Veículos");
            System.out.println("3. Gerenciar Agendamentos");
            System.out.println("4. Registrar Despesa");
            System.out.println("5. Receber Compra");
            System.out.println("6. Gerar Relatórios");
            System.out.println("7. Gerenciar Colaboradores");
            System.out.println("8. Gerenciar Produtos");
            System.out.println("9. Executar Testes (Q15-Q18)");
            System.out.println("10. Sair");
            System.out.print("Opção: "); opc = readInt();
            switch (opc) {
                case 1: gerenciarClientes();      break;
                case 2: gerenciarVeiculos();      break;
                case 3: gerenciarAgendamentos();  break;
                case 4: registrarDespesa();       break;
                case 5: receberCompra();          break;
                case 6: gerarRelatorios();        break;
                case 7: gerenciarColaboradores(); break;
                case 8: gerenciarProdutos();      break;
                case 9: executarTestes();         break;
                case 10: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opc != 10);
    }

    private static void menuColaborador() {
        int opc;
        do {
            System.out.println("--- Menu Colaborador ---");
            System.out.println("1. Gerenciar Clientes");
            System.out.println("2. Gerenciar Veículos");
            System.out.println("3. Gerenciar Agendamentos");
            System.out.println("4. Sair");
            System.out.print("Opção: "); opc = readInt();
            switch (opc) {
                case 1: gerenciarClientes();     break;
                case 2: gerenciarVeiculos();     break;
                case 3: gerenciarAgendamentos(); break;
                case 4: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opc != 4);
    }

    private static void gerenciarClientes() {
        int opc;
        do {
            System.out.println("--- Gerenciar Clientes ---");
            System.out.println("1. Listar");
            System.out.println("2. Adicionar");
            System.out.println("3. Atualizar");
            System.out.println("4. Remover");
            System.out.println("5. Voltar");
            System.out.print("Opção: "); opc = readInt();
            switch (opc) {
                case 1:
                    sistema.getClientes().forEach(System.out::println);
                    break;
                case 2:
                    Cliente c = new Cliente();
                    System.out.print("ID: "); c.setId(readInt());
                    System.out.print("Nome: "); c.setNome(SC.nextLine());
                    System.out.print("Telefone: "); c.setTelefone(SC.nextLine());
                    System.out.print("Email: "); c.setEmail(SC.nextLine());
                    System.out.print("CPF: "); c.setCpf(SC.nextLine());
                    System.out.print("Endereço: "); c.setEndereco(SC.nextLine());
                    sistema.addCliente(c);
                    System.out.println("Cliente adicionado.\n");
                    break;
                case 3:
                    System.out.print("ID do cliente: ");
                    Optional<Cliente> optC = sistema.getClienteById(readInt());
                    optC.ifPresentOrElse(cc -> {
                        System.out.print("Novo nome (" + cc.getNome() + "): ");
                        cc.setNome(SC.nextLine());
                        sistema.updateCliente(cc);
                        System.out.println("Cliente atualizado.\n");
                    }, () -> System.out.println("Cliente não encontrado.\n"));
                    break;
                case 4:
                    System.out.print("ID para remover: ");
                    if (sistema.removeCliente(readInt()))
                        System.out.println("Cliente removido.\n");
                    else
                        System.out.println("Cliente não encontrado.\n");
                    break;
                case 5: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opc != 5);
    }

    private static void gerenciarVeiculos() {
        int opc;
        do {
            System.out.println("--- Gerenciar Veículos ---");
            System.out.println("1. Listar");
            System.out.println("2. Adicionar");
            System.out.println("3. Atualizar");
            System.out.println("4. Remover");
            System.out.println("5. Voltar");
            System.out.print("Opção: "); opc = readInt();
            switch (opc) {
                case 1:
                    sistema.getVeiculos().forEach(System.out::println);
                    break;
                case 2:
                    Veiculo v = new Veiculo();
                    System.out.print("ID: "); v.setId(readInt());
                    System.out.print("Placa: "); v.setPlaca(SC.nextLine());
                    System.out.print("Modelo: "); v.setModelo(SC.nextLine());
                    System.out.print("Cliente ID: ");
                    sistema.getClienteById(readInt()).ifPresent(v::setCliente);
                    sistema.addVeiculo(v);
                    System.out.println("Veículo adicionado.\n");
                    break;
                case 3:
                    System.out.print("ID do veículo: ");
                    Optional<Veiculo> optV = sistema.getVeiculoById(readInt());
                    optV.ifPresentOrElse(vv -> {
                        System.out.print("Nova placa (" + vv.getPlaca() + "): ");
                        vv.setPlaca(SC.nextLine());
                        sistema.updateVeiculo(vv);
                        System.out.println("Veículo atualizado.\n");
                    }, () -> System.out.println("Veículo não encontrado.\n"));
                    break;
                case 4:
                    System.out.print("ID para remover: ");
                    if (sistema.removeVeiculo(readInt()))
                        System.out.println("Veículo removido.\n");
                    else
                        System.out.println("Veículo não encontrado.\n");
                    break;
                case 5: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opc != 5);
    }

    private static void gerenciarColaboradores() {
        int opc;
        do {
            System.out.println("--- Gerenciar Colaboradores ---");
            System.out.println("1. Listar");
            System.out.println("2. Adicionar");
            System.out.println("3. Atualizar");
            System.out.println("4. Remover");
            System.out.println("5. Voltar");
            System.out.print("Opção: "); opc = readInt();
            switch (opc) {
                case 1:
                    sistema.getUsuarios().stream()
                        .filter(u -> u instanceof Colaborador)
                        .forEach(System.out::println);
                    break;
                case 2:
                    Colaborador col = new Colaborador();
                    System.out.print("Usuário: "); col.setUsername(SC.nextLine());
                    System.out.print("Senha: "); col.setPassword(SC.nextLine());
                    sistema.addUsuario(col);
                    System.out.println("Colaborador adicionado.\n");
                    break;
                case 3:
                    System.out.print("Usuário a atualizar: ");
                    String usrUpd = SC.nextLine();
                    sistema.getUsuarios().stream()
                        .filter(u -> u.getUsername().equals(usrUpd) && u instanceof Colaborador)
                        .findFirst()
                        .ifPresentOrElse(cc -> {
                            System.out.print("Nova senha: ");
                            cc.setPassword(SC.nextLine());
                            sistema.updateUsuario(cc);
                            System.out.println("Colaborador atualizado.\n");
                        }, () -> System.out.println("Colaborador não encontrado.\n"));
                    break;
                case 4:
                    System.out.print("Usuário para remover: ");
                    String usrDel = SC.nextLine();
                    if (sistema.removeUsuario(usrDel))
                        System.out.println("Colaborador removido.\n");
                    else
                        System.out.println("Colaborador não encontrado.\n");
                    break;
                case 5: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opc != 5);
    }

    private static void gerenciarAgendamentos() {
        int opc;
        do {
            System.out.println("--- Gerenciar Agendamentos ---");
            System.out.println("1. Listar");
            System.out.println("2. Adicionar");
            System.out.println("3. Cancelar");
            System.out.println("4. Listar Ordens por Cliente");
            System.out.println("5. Voltar");
            System.out.print("Opção: "); opc = readInt();
            switch (opc) {
                case 1:
                    sistema.getAgendamentos().forEach(System.out::println);
                    break;
                case 2:
                    Agendamento ag = new Agendamento();
                    System.out.print("ID: "); ag.setId(readInt());
                    System.out.print("Data/Hora (dd/MM/yyyy HH:mm): ");
                    ag.setDataHora(DateUtil.parseDateTime(SC.nextLine()));
                    System.out.print("Cliente ID: ");
                    sistema.getClienteById(readInt()).ifPresent(ag::setCliente);
                    System.out.print("Veículo ID: ");
                    sistema.getVeiculoById(readInt()).ifPresent(ag::setVeiculo);
                    System.out.print("Descrição problema: ");
                    ag.setDescricaoProblema(SC.nextLine());
                    System.out.print("Estado inicial: ");
                    ag.setEstadoInicial(SC.nextLine());
                    ag.setValorRetido(0);
                    sistema.registrarAgendamento(ag);
                    System.out.println("Agendamento registrado.\n");
                    break;
                case 3:
                    System.out.print("ID do agendamento: ");
                    sistema.cancelarAgendamento(readInt());
                    System.out.println("Agendamento cancelado.\n");
                    break;
                case 4:
                    listarOrdensPorCliente();
                    break;
                case 5: break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opc != 5);
    }

    private static void gerenciarProdutos() {
        int opc;
        do {
            System.out.println("--- Gerenciar Produtos ---");
            System.out.println("1. Listar");
            System.out.println("2. Adicionar");
            System.out.println("3. Atualizar");
            System.out.println("4. Remover");
            System.out.println("5. Voltar");
            System.out.print("Opção: "); opc = readInt();
            switch (opc) {
                case 1:
                    sistema.getProdutos().forEach(System.out::println);
                    break;
                case 2:
                    Produto p = new Produto();
                    System.out.print("Código: "); p.setCodigo(readInt());
                    System.out.print("Nome: "); p.setNome(SC.nextLine());
                    System.out.print("Preço: "); p.setPreco(Double.parseDouble(SC.nextLine()));
                    sistema.addProduto(p);
                    System.out.println("Produto adicionado.\n");
                    break;
                case 3:
                    System.out.print("Código do produto: ");
                    Optional<Produto> optP = sistema.getProdutoById(readInt());
                    optP.ifPresentOrElse(pp -> {
                        System.out.print("Novo nome (" + pp.getNome() + "): ");
                        pp.setNome(SC.nextLine());
                        System.out.print("Novo preço (" + pp.getPreco() + "): ");
                        pp.setPreco(Double.parseDouble(SC.nextLine()));
                        sistema.updateProduto(pp);
                        System.out.println("Produto atualizado.\n");
                    }, () -> System.out.println("Produto não encontrado.\n"));
                    break;
                case 4:
                    System.out.print("Código para remover: ");
                    if (sistema.removeProduto(readInt()))
                        System.out.println("Produto removido.\n");
                    else
                        System.out.println("Produto não encontrado.\n");
                    break;
                case 5: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opc != 5);
    }

    private static void listarOrdensPorCliente() {
        System.out.print("ID do cliente: ");
        int clienteId = readInt();
        List<OrdemServico> ordens = sistema.getOrdensPorCliente(clienteId);
        if (ordens.isEmpty()) {
            System.out.println("Nenhuma ordem de serviço encontrada para o cliente ID " + clienteId + ".\n");
        } else {
            System.out.println("Ordens de serviço para o cliente ID " + clienteId + ":");
            ordens.forEach(System.out::println);
            System.out.println();
        }
    }

    private static void registrarDespesa() {
        if (!(usuarioLogado instanceof Administrador)) {
            System.out.println("Acesso negado: somente administradores podem registrar despesas.\n");
            return;
        }
        Despesa d = new Despesa();
        System.out.print("ID: "); d.setId(readInt());
        System.out.print("Tipo: "); d.setTipo(SC.nextLine());
        System.out.print("Descrição: "); d.setDescricao(SC.nextLine());
        System.out.print("Data (dd/MM/yyyy): ");
        d.setData(DateUtil.toDate(DateUtil.parseDate(SC.nextLine())));
        System.out.print("Valor: "); d.setValor(Double.parseDouble(SC.nextLine()));
        sistema.registrarDespesa(d);
        System.out.println("Despesa registrada.\n");
    }

    private static void receberCompra() {
        System.out.println("--- Receber Compra ---");
        System.out.println("Digite 0 no código do produto para cancelar.");
        System.out.print("Quantidade de itens a registrar: ");
        int n = readInt();
        Map<Produto,Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < n; i++) {
            System.out.print("Código produto (0 para sair): ");
            int code = readInt();
            if (code == 0) {
                System.out.println("Entrada de compra interrompida.\n");
                break;
            }
            System.out.print("Quantidade: ");
            int qty = readInt();
            map.put(new Produto(code,"",0.0), qty);
        }
        if (!map.isEmpty()) {
            sistema.receberCompra(map);
            System.out.println("Compra registrada.\n");
        } else {
            System.out.println("Nenhum item registrado.\n");
        }
    }

    private static void gerarRelatorios() {
        if (!(usuarioLogado instanceof Administrador)) {
            System.out.println("Acesso negado: somente administradores podem gerar relatórios.\n");
            return;
        }
        System.out.println("--- Relatórios ---");
        System.out.println("1. Diário");
        System.out.println("2. Mensal");
        System.out.print("Opção: "); int opc = readInt();
        if (opc == 1) {
            System.out.print("Data (dd/MM/yyyy): ");
            sistema.emitirRelatorioDia(
                DateUtil.toDate(DateUtil.parseDate(SC.nextLine()))
            ).gerar();
        } else if (opc == 2) {
            System.out.print("Mês: "); int m = readInt();
            System.out.print("Ano: "); int y = readInt();
            sistema.emitirRelatorioMensal(m,y).gerar();
        }
        System.out.println();
    }

   /**
     * Executa os testes das questões 15 a 18.
     */
    private static void executarTestes() {
        List<Cliente> clientes = sistema.getClientes();
        if (clientes.isEmpty()) {
            // Garante ao menos um cliente para os testes
            sistema.simularFluxoCliente(1);
            clientes = sistema.getClientes();
        }

        // === Q15: Iterator vs For-Each ===
        System.out.println("\n----- Q15: Iterator vs For-Each -----");
        Iterator<Cliente> it = clientes.iterator();
        while (it.hasNext()) {
            Cliente c = it.next();
            System.out.println("Iterator processando cliente: " + c.getNome());
        }
        // O loop for-each utiliza internamente um Iterator
        for (Cliente c : clientes) {
            System.out.println("Foreach processando cliente: " + c.getNome());
        }
        // O for-each é apenas uma sintaxe simplificada que utiliza um Iterator
        // por baixo dos panos para percorrer a coleção

        System.out.println("-------------------------------------");

        // === Q16: Testes de Comparator ===
        System.out.println("----- Q16: Testes de Comparator -----");
        Collections.sort(clientes, Cliente.comparatorPorCpf());
        System.out.println("Ordenação por CPF:");
        clientes.forEach(c -> System.out.println("  " + c.getCpf() + " - " + c.getNome()));
        Collections.sort(clientes, Cliente.comparatorPorNome());
        System.out.println("Ordenação por Nome:");
        clientes.forEach(c -> System.out.println("  " + c.getNome() + " - " + c.getCpf()));

        System.out.println("-------------------------------------");

        // === Q17: Iterator vs BinarySearch ===
        System.out.println("----- Q17: Iterator vs BinarySearch -----");
        Cliente alvo = clientes.get(0);

        long startIt = System.nanoTime();
        Cliente encontradoIt = sistema.buscarComIterator(
            clientes,
            alvo,
            Comparator.comparingInt(Cliente::getId)
        );
        long timeIt = System.nanoTime() - startIt;
        double timeItMs = timeIt / 1_000_000.0;
        System.out.println(String.format(
                "Iterator encontrou: %s em %.3f ms",
                (encontradoIt != null ? encontradoIt.getNome() : "não achou"),
                timeItMs));

        Collections.sort(clientes, Comparator.comparingInt(Cliente::getId));
        long startBs = System.nanoTime();
        int idx = Collections.binarySearch(
            clientes,
            alvo,
            Comparator.comparingInt(Cliente::getId)
        );
        long timeBs = System.nanoTime() - startBs;
        double timeBsMs = timeBs / 1_000_000.0;
        System.out.println(String.format(
                (idx >= 0
                        ? "BinarySearch encontrou: " + clientes.get(idx).getNome()
                        : "BinarySearch não encontrou")
                        + " em %.3f ms",
                timeBsMs));
        // Nota: binarySearch exige lista ordenada

        System.out.println("-------------------------------------");

        // === Q18: Fluxo Completo (10 Clientes) ===
        System.out.println("----- Q18: Fluxo Completo (10 Clientes) -----");
        for (int i = 1; i <= 10; i++) {
            sistema.simularFluxoCliente(i);
        }
        System.out.println("-------------------------------------");
        System.out.println("\n✅ Testes concluídos!");
    }

    private static int readInt() {
        while (true) {
            String s = SC.nextLine();
            if (InputValidator.isInteger(s)) {
                return Integer.parseInt(s);
            }
            System.out.print("Número inválido. Digite novamente: ");
        }
    }
}
