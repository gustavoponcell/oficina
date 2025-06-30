package com.mycompany.oficina.model;

/**
 * Representa o balanço mensal contendo receitas e despesas.
 * Extende Relatorio para possibilitar geração de relatório periodizado.
 */
public class BalancoMensal extends Relatorio {

    private int mes;
    private int ano;
    private double receitas;
    private double despesas;

    /**
     * Construtor vazio para frameworks de desserialização.
     */
    public BalancoMensal() {
        super();
    }

    /**
     * Construtor completo.
     * @param id        identificador do relatório
     * @param dataInicio início do período (geralmente início do mês)
     * @param dataFim    fim do período (geralmente fim do mês)
     * @param mes       número do mês (1-12)
     * @param ano       ano correspondente
     * @param receitas  total de receitas no período
     * @param despesas  total de despesas no período
     */
    public BalancoMensal(int id, java.util.Date dataInicio, java.util.Date dataFim,
                         int mes, int ano, double receitas, double despesas) {
        super(id, dataInicio, dataFim);
        this.mes = mes;
        this.ano = ano;
        this.receitas = receitas;
        this.despesas = despesas;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getReceitas() {
        return receitas;
    }

    public void setReceitas(double receitas) {
        this.receitas = receitas;
    }

    public double getDespesas() {
        return despesas;
    }

    public void setDespesas(double despesas) {
        this.despesas = despesas;
    }

    /**
     * Calcula o saldo do período (receitas menos despesas).
     * @return saldo resultante
     */
    public double calcularSaldo() {
        return receitas - despesas;
    }

    /**
     * Gera o relatório de balanço, imprimindo no console.
     */
    @Override
    public void gerar() {
        System.out.println("Balanço Mensal - " + mes + "/" + ano);
        System.out.println("Receitas: R$ " + receitas);
        System.out.println("Despesas: R$ " + despesas);
        System.out.println("Saldo: R$ " + calcularSaldo());
    }

    @Override
    public String toString() {
        return "BalancoMensal{" +
               "id=" + getId() +
               ", mes=" + mes +
               ", ano=" + ano +
               ", receitas=" + receitas +
               ", despesas=" + despesas +
               '}';
    }
}
