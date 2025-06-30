# Contributor Guide

Este guia descreve como configurar e usar o agente CI para o projeto **Oficina Mecânica**, além de boas práticas para desenvolvimento local, testes e PRs.

## Dev Environment Tips

- **JDK & Maven**: Certifique-se de ter o OpenJDK 23 e Maven instalados. Use nosso script de setup abaixo (seção “CI Setup”) ou instale manualmente:
  ```bash
  sudo apt-get update -y
  sudo apt-get install -y openjdk-23-jdk maven
  ```
- **Variáveis de ambiente**: Garanta que `JAVA_HOME` aponte para o JDK 23:
  ```bash
  echo "export JAVA_HOME=/usr/lib/jvm/java-23-openjdk-amd64" >> ~/.bashrc
  echo "export PATH=\$JAVA_HOME/bin:\$PATH" >> ~/.bashrc
  source ~/.bashrc
  ```
- **IDE Recommendation**: Use IntelliJ IDEA ou VS Code com as extensões Java e Maven para aproveitar autocomplete e inspeções.
- **Código & Formatação**: Aplique o formatter do Maven antes de commitar:
  ```bash
  mvn fmt:format
  ```

## CI Setup

No início de cada job do agente, execute este script de configuração para preparar o ambiente e garantir consistência:

```bash
#!/usr/bin/env bash
set -e

# 1) Atualizar e instalar JDK & Maven
echo "==> Instalando JDK e Maven"
sudo apt-get update -y
sudo apt-get install -y openjdk-23-jdk maven

# 2) Configurar JAVA_HOME e PATH
echo "==> Configurando variáveis de ambiente"
echo "export JAVA_HOME=/usr/lib/jvm/java-23-openjdk-amd64" >> ~/.bashrc
echo "export PATH=\$JAVA_HOME/bin:\$PATH" >> ~/.bashrc
source ~/.bashrc

# 3) Resolver dependências Maven
echo "==> Resolving Maven dependencies"
mvn dependency:resolve

# 4) Executar testes unitários
echo "==> Executando testes JUnit"
mvn test

# 5) Compilar e rodar aplicação (opcional)
echo "==> Compilando e executando aplicação"
mvn compile exec:java -Dexec.mainClass=com.mycompany.oficina.app.Oficina
```

> **Dica:** use `AGENTES.md` como referência para o agente. Qualquer variável persistente pode ser adicionada ao `~/.bashrc`.

## Testing Instructions

- Localmente, você pode rodar todos os testes com:
  ```bash
  mvn test
  ```
- Para focar em um único teste (JUnit 5), passe o padrão via `-Dtest`:
  ```bash
  mvn -Dtest=ClienteTest#testCpfOrder test
  ```
- Após mover ou renomear classes, execute o lint e formato:
  ```bash
  mvn fmt:check
  mvn validate
  ```
- **Teste de Performance Q17**: verifique saída de tempo das buscas Iterator vs BinarySearch.

## PR Instructions

- **Título do PR**: siga o padrão
  ```
  [<Modulo>] Breve descrição da mudança
  ```
- **Branch naming**: `feature/<modulo>-<curta-descrição>` ou `fix/<modulo>-<problema>`.
- **Checklist antes de merge**:
  - [ ] Todos os testes passam (`mvn test`).
  - [ ] Código formatado (`mvn fmt:format`).
  - [ ] Documentação e comentários atualizados.
  - [ ] Revisão de um colega aprovada.

---

## Codex Review Prompt Template

Use este prompt no **Codex CLI** ou na aba **Codex** do ChatGPT para revisar e propor melhorias ao projeto “Oficina Mecânica”:  

```text
Você é um auditor sênior de POO Java com foco em qualidade, performance e manutenção. Você terá acesso a todo o código-fonte do repositório "Oficina Mecânica" e aos requisitos detalhados (PDF e questões 15–18). Sua tarefa:

1. Validar conformidade estrutural:
   - Dependências em `pom.xml` e modularização de pacotes.
   - Padrões de design e princípios SOLID.
2. Revisar requisitos funcionais:
   - Checar implementações de Iterator vs For-Each (Q15).
   - Validar testes de Comparator (Q16) e de busca (Q17).
   - Confirmar o fluxo completo de 10 clientes (Q18) sem erros.
3. Identificar code smells, potenciais bugs e vulnerabilidades (security/e linter).
4. Sugerir refatorações:
   - Melhoria de nomes, extração de métodos/class, otimização de loops e coleções.
   - Ajustes em persistência JSON, logging e tratamento de exceções.
   - Adição ou aprimoramento de testes unitários e de integração.
5. Fornecer exemplos de patch em estilo `diff` para cada sugestão.
6. Propor mensagens de commit claras para cada mudança.

No final, entregue um relatório em Markdown contendo:
- Tabela de status (✅/⚠️/❌) para cada requisito.
- Snippets de código antes/depois.
- Comentários de revisão e justificativas.
```

*Use este prompt toda vez que iniciar uma revisão de código com o Codex para obter um feedback consistente e rastreável.*

---

## Codex Menu Improvement Prompt

Use o prompt abaixo no **Codex CLI** ou na aba **Codex** do ChatGPT para revisar e aprimorar o menu de console em `Oficina.java`, garantindo alinhamento com as funcionalidades do projeto e os requisitos do professor:

```text
Você é um desenvolvedor sênior Java e auditor de UX de console. Sua tarefa é analisar o método `menuAdministrador()` e `menuColaborador()` em **Oficina.java** e propor melhorias que:

1. **Organizem as opções de forma lógica**: agrupar funcionalidades relacionadas (ex: Clientes, Veículos, Agendamentos).
2. **Melhorem a usabilidade**:
   - Adicionar atalhos numéricos claros.
   - Exibir descrições mais informativas.
   - Validar entradas e tratar erros de opção inválida.
3. **Incorporem requisitos do professor**:
   - Incluir opção de “Executar Testes (Q15-Q18)” no menu principal.
   - Destacar ações críticas com avisos (ex: remover cliente).
   - Permitir voltar ao menu anterior.
4. **Sugiram refatorações de código**:
   - Extrair métodos para renderizar menus.
   - Implementar loop de menu genérico reutilizável.
   - Usar enums ou constantes para opções de menu.
   - Adicionar mensagens de confirmação para ações críticas.
5. **Output**:
   - Um relatório em Markdown com sugestões de patch no estilo `diff`.
   - Exemplos de código antes e depois para cada melhoria.
   - Observações sobre impacto na experiência do usuário.
```

*Este prompt foca especificamente no aperfeiçoamento do menu de console, fornecendo ao Codex contexto e critérios de avaliação claros.*

### Saídas de Console e Testes
Para além do menu, inclua no prompt:
1. **Melhoria das saídas `print`**:
   - Formatação clara (timestamps, prefixos de seção).
   - Mensagens consistentes em `executarTestes()`, substituindo jargões técnicos por descrições legíveis.
   - Saída tabular ou formatada em Markdown para relatórios gerados.
   - Uso de espaçamento, quebras de linha e delimitadores para separar blocos de informação.
2. **Exemplos de Saída Legível**:
   - Antes/depois de uma execução de teste trivial.
   - Demonstração de como apresentar resultados de tempo (Q17) de forma legível (por ex., “Iterator levou 10 µs” em vez de ns cru).

Incorpore essas diretrizes no relatório final do Codex, fornecendo amostras de output aprimorado e sugestões de código para gerar estas saídas.

