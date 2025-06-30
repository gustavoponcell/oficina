package com.mycompany.oficina.model;

/**
 * Representa um elevador de veículos na oficina.
 * Permite elevar e baixar veículos para manutenção.
 */
public class Elevador {

    // Contadores estáticos para total de elevadores criados
    private static int totalElevadoresPrivados = 0;
    protected static int totalElevadoresProtegidos = 0;

    private int id;
    private String tipo;
    private boolean disponivel;

    /**
     * Construtor vazio para frameworks de desserialização.
     */
    public Elevador() {
        totalElevadoresPrivados++;
        totalElevadoresProtegidos++;
        // demais inicializações, se houver
    }

    /**
     * Construtor padrão.
     *
     * @param id         identificador do elevador
     * @param tipo       tipo de elevador (hidráulico, pneumático, etc.)
     * @param disponivel se o elevador está disponível
     */
    public Elevador(int id, String tipo, boolean disponivel) {
        this.id = id;
        this.tipo = tipo;
        this.disponivel = disponivel;
        totalElevadoresPrivados++;
        totalElevadoresProtegidos++;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    // Getters estáticos para os contadores
    public static int getTotalElevadoresPrivados() {
        return totalElevadoresPrivados;
    }

    public static int getTotalElevadoresProtegidos() {
        return totalElevadoresProtegidos;
    }

    /**
     * Eleva o elevador para realizar manutenção.
     * @throws IllegalStateException se o elevador já estiver em uso
     */
    public void elevar() {
        if (!disponivel) {
            throw new IllegalStateException("Elevador não está disponível");
        }
        this.disponivel = false;
    }

    /**
     * Abaixa o elevador após término de manutenção.
     * @throws IllegalStateException se o elevador já estiver disponível
     */
    public void baixar() {
        if (disponivel) {
            throw new IllegalStateException("Elevador já está disponível");
        }
        this.disponivel = true;
    }

    @Override
    public String toString() {
        return "Elevador{" +
               "id=" + id +
               ", tipo='" + tipo + '\'' +
               ", disponivel=" + disponivel +
               '}';
    }
}
