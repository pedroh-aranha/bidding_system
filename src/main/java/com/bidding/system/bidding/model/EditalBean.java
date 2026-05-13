/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.model;

import java.sql.Date;

/**
 *
 * @author Aluno
 */
public class EditalBean {
    private int id;
    private String titulo;
    private String descricao;
    private Date datafechamento;
    private String status;

    public EditalBean() {
    }

    public EditalBean(int id, String titulo, String descricao, Date datafechamento, String status) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.datafechamento = datafechamento;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDatafechamento() {
        return datafechamento;
    }

    public void setDatafechamento(Date datafechamento) {
        this.datafechamento = datafechamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   
}
