# Oficina Mecânica

Um pequeno sistema em Java que demonstra o gerenciamento de uma oficina mecânica. O projeto utiliza JSON para persistência simples e oferece menus de administração e de colaborador.

## Compilação e execução

1. Certifique-se de ter o **Maven** instalado e um JDK compatível (recomendado JDK 17+).
2. No diretório do projeto, execute para compilar e baixar dependências:
   ```bash
   mvn compile
   ```
3. Para iniciar a aplicação pelo Maven:
   ```bash
   mvn exec:java -Dexec.mainClass=com.mycompany.oficina.app.Oficina
   ```

## Diretório de dados

Os arquivos JSON utilizados pela aplicação ficam em `data/`. O diretório é criado automaticamente na primeira execução e pode conter amostras para testes locais.

## Opções principais do menu

- **Gerenciar Clientes / Veículos / Agendamentos** – Cadastro e listagem de cada entidade.
- **Registrar Despesa** – Insere registros financeiros.
- **Receber Compra** – Atualiza o estoque com produtos adquiridos.
- **Gerar Relatórios** – Mostra relatórios simples de vendas e serviços.
- **Gerenciar Colaboradores / Produtos** – Acesso restrito para usuários administradores.
- **Executar Testes (Q15‑Q18)** – Roda um conjunto de testes demonstrativos.
- **Sair** – Encerra o programa.

## Executando os testes Q15–Q18

Ao rodar a aplicação, escolha a opção **"Executar Testes (Q15-Q18)"** no menu principal. O sistema irá demonstrar:

1. Uso de `Iterator` vs `for-each` (Q15).
2. Comparadores de clientes (Q16).
3. Desempenho entre busca com `Iterator` e `binarySearch` (Q17).
4. Fluxo completo de atendimento para dez clientes (Q18).

