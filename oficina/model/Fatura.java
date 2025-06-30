package com.mycompany.oficina.model;

import java.util.Date;
import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Representa a fatura de cobrança ao cliente, gerada a partir de uma ordem de serviço.
 */
public class Fatura {

    private int id;
    private Date data;
    private double valorTotal;
    private Cliente cliente;

    /**
     * Construtor vazio para frameworks de desserialização.
     */
    public Fatura() { }

    /**
     * Construtor completo.
     * @param id         identificador da fatura
     * @param data       data de emissão da fatura
     * @param valorTotal valor total a ser cobrado
     * @param cliente    cliente destinatário da fatura
     */
    public Fatura(int id, Date data, double valorTotal, Cliente cliente) {
        this.id = id;
        this.data = data;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Imprime a fatura no console em formato legível.
     */
    public void imprimir() {
        System.out.println("----- Fatura ID: " + id + " -----");
        System.out.println("Data: " + data);
        System.out.println("Cliente: " + (cliente != null ? cliente.getNome() : "Desconhecido"));
        System.out.println("Valor Total: R$ " + valorTotal);
        System.out.println("------------------------------");
    }

    /**
     * Salva a fatura em um arquivo (JSON ou texto) - stub de implementação.
     */
    public void salvarExtrato() {
        String fileName = "fatura-" + id + ".json";
        Path path = Paths.get("data", fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(List.of(this), writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar extrato: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Fatura{" +
               "id=" + id +
               ", data=" + data +
               ", valorTotal=" + valorTotal +
               ", cliente=" + (cliente != null ? cliente.getNome() : "null") +
               '}';
    }
}
