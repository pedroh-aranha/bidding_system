/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.repository;

import com.bidding.system.bidding.model.LancesBean;
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
public class LanceRepository {

    public int criarLance(LancesBean lance) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement("Insert into lances (valor, data_lance, id_edital, id_usuario) values (?, ?, ?, ?)");

            stmt.setDouble(1, lance.getValor());
            stmt.setDate(2, lance.getData_lance());
            stmt.setLong(3, lance.getIdEdital());
            stmt.setLong(4, lance.getIdusuario());

            return stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public List<LancesBean> listarPorEdital(long idEdital) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM lances WHERE id_edital = ?"
            );
            stmt.setLong(1, idEdital);
            ResultSet rs = stmt.executeQuery();

            List<LancesBean> lances = new ArrayList<>();
            while (rs.next()) {
                LancesBean l = new LancesBean();
                l.setId(rs.getLong("id"));
                l.setValor(rs.getDouble("valor"));
                l.setData_lance(rs.getDate("data_lance"));
                l.setIdEdital(rs.getLong("id_edital"));
                l.setIdusuario(rs.getLong("id_usuario"));
                lances.add(l);
            }
            return lances;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
