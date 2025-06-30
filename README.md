# Oficina Mecânica

Sistema de gerenciamento de oficina escrito em Java. O projeto segue exemplos de POO e é usado em atividades práticas sobre estruturas de dados.

## Build

1. Instale JDK 21 ou superior e Maven.
2. Com as dependências instaladas, execute na raiz do projeto:

```bash
mvn compile
```

Isso compila todas as classes. Para empacotar em um JAR, utilize `mvn package`.

## Execução

Rode a aplicação principal via Maven:

```bash
mvn exec:java -Dexec.mainClass=com.mycompany.oficina.app.Oficina
```

Na primeira execução será solicitado o cadastro do usuário administrador. Após o login o menu é apresentado com as opções de gerenciamento.

## Testes das Questões 15–18

As operações de teste solicitadas nas questões 15–18 estão implementadas no método `executarTestes()` e podem ser executadas dentro do próprio sistema:

1. Inicie a aplicação usando o comando de execução acima e faça login como administrador.
2. No menu administrador, escolha a opção **9. Executar Testes (Q15-Q18)**.
3. O console exibirá os resultados dos testes de Iterator vs For-Each (Q15), Comparator (Q16), busca binária (Q17) e o fluxo completo de dez clientes (Q18).

Se houver testes JUnit correspondentes no projeto, eles podem ser rodados com:

```bash
mvn test
```

Para rodar apenas um teste específico:

```bash
mvn -Dtest=ClasseDoTeste#metodo test
```

