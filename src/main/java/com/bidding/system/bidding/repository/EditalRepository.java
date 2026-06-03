/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.repository;

import com.bidding.system.bidding.model.EditalBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aluno
 */
@Repository
public class EditalRepository {
    
     public int novoEdital(EditalBean edital) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;

            stmt = conn.prepareStatement("insert into editais (titulo, descricao, data_fechamento, status) values (?, ?, ?, ?)");
            stmt.setString(1, edital.getTitulo());
            stmt.setString(2, edital.getDescricao());
            stmt.setDate(3, edital.getDatafechamento());
            stmt.setString(4, edital.getStatus());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<EditalBean> listaEdital() {
        List<EditalBean> listaEdital = new ArrayList<>();
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;

            stmt = conn.prepareStatement("select * from editais");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                EditalBean edital = new EditalBean();
                edital.setId(rs.getLong("id"));
                edital.setTitulo(rs.getString("titulo"));
                edital.setDescricao(rs.getString("descricao"));
                edital.setDatafechamento(rs.getDate("data_fechamento"));
                edital.setStatus(rs.getString("status"));
                
                listaEdital.add(edital);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaEdital;
    }
    
    public EditalBean getbyid(Long id) {
        EditalBean edital = new EditalBean();
        try{
            Connection conn = Conexao.conectar();
            
            PreparedStatement stmt = conn.prepareStatement("SELECT data_fechamento, status from editais where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
            edital.setDatafechamento(rs.getDate("data_fechamento"));
            edital.setStatus(rs.getString("status"));
            
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return edital;
    }
    
    public List<EditalBean> listaUrgentes() {
    List<EditalBean> lista = new ArrayList<>();
    try {
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM editais WHERE data_fechamento BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 3 DAY)"
        );
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            EditalBean edital = new EditalBean();
            edital.setId(rs.getLong("id"));
            edital.setTitulo(rs.getString("titulo"));
            edital.setDescricao(rs.getString("descricao"));
            edital.setDatafechamento(rs.getDate("data_fechamento"));
            edital.setStatus(rs.getString("status"));
            lista.add(edital);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}
    
}
