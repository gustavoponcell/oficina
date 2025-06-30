package com.mycompany.oficina.model;

import com.mycompany.oficina.persistence.VeiculoRepository;

/**
 * Representa um veículo pertencente a um cliente da oficina.
 * Conta instâncias criadas com contadores estáticos.
 */
public class Veiculo {

    private int id;
    private String placa;
    private String modelo;
    private Cliente cliente;

    /**
     * Contador privado para número total de veículos instanciados.
     */
    private static int totalVeiculosEncapsulados = 0;

    /**
     * Contador protegido visível em subclasses e pacote.
     */
    protected static int totalVeiculosProtegidos = 0;

    /**
     * Construtor vazio que incrementa os contadores.
     */
    public Veiculo() {
        totalVeiculosEncapsulados++;
        totalVeiculosProtegidos++;
    }

    /**
     * Construtor completo.
     * @param id identificador único do veículo
     * @param placa placa de registro
     * @param modelo modelo do veículo
     * @param cliente cliente proprietário
     */
    public Veiculo(int id, String placa, String modelo, Cliente cliente) {
        this();
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Inclui este veículo no sistema.
     * Deve delegar ao serviço ou repositório adequado.
     */
    public void incluir() {
        VeiculoRepository repo = new VeiculoRepository();
        this.id = repo.findAll().size() + 1;
        repo.add(this);
    }

    /**
     * Edita os dados deste veículo no sistema.
     */
    public void editar() {
        VeiculoRepository repo = new VeiculoRepository();
        repo.update(this);
    }

    /**
     * Remove este veículo do sistema.
     */
    public void remover() {
        VeiculoRepository repo = new VeiculoRepository();
        repo.remove(this.id);
    }

    /**
     * @return total de veículos instanciados (privado)
     */
    public static int getTotalVeiculosEncapsulados() {
        return totalVeiculosEncapsulados;
    }

    /**
     * @param total novo valor para o contador privado
     */
    public static void setTotalVeiculosEncapsulados(int total) {
        totalVeiculosEncapsulados = total;
    }

    /**
     * @return total de veículos instanciados (protegido)
     */
    public static int getTotalVeiculosProtegidos() {
        return totalVeiculosProtegidos;
    }

    /**
     * @param total novo valor para o contador protegido
     */
    public static void setTotalVeiculosProtegidos(int total) {
        totalVeiculosProtegidos = total;
    }

    @Override
    public String toString() {
        return "Veiculo{" +
               "id=" + id +
               ", placa='" + placa + '\'' +
               ", modelo='" + modelo + '\'' +
               ", cliente=" + (cliente != null ? cliente.getNome() : "null") +
               '}';
    }
}