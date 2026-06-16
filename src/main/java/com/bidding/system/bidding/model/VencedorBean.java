package com.bidding.system.bidding.model;

import java.sql.Date;

/**
 * Representa o vencedor de um edital registrado na tabela vencedores_editais.
 */
public class VencedorBean {

    private Long id;
    private Long idEdital;
    private Long idUsuario;
    private Long idLance;
    private double valorLance;
    private Date dataRegistro;

    // Campos extras via JOIN
    private String editalTitulo;
    private String usuarioNome;

    public VencedorBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEdital() {
        return idEdital;
    }

    public void setIdEdital(Long idEdital) {
        this.idEdital = idEdital;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdLance() {
        return idLance;
    }

    public void setIdLance(Long idLance) {
        this.idLance = idLance;
    }

    public double getValorLance() {
        return valorLance;
    }

    public void setValorLance(double valorLance) {
        this.valorLance = valorLance;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getEditalTitulo() {
        return editalTitulo;
    }

    public void setEditalTitulo(String editalTitulo) {
        this.editalTitulo = editalTitulo;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }
}
