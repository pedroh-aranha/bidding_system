/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.repository;

import com.bidding.system.bidding.model.LancesBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aluno
 */
@Repository
public class LanceRepository {
    
    public int criarLance(LancesBean lance) {
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement("Insert into lances (valor, data_lance, id_edital, id_usuario) values (?, ?, ?, ?)");
            
            stmt.setDouble(1, lance.getValor());
            stmt.setDate(2, lance.getData_lance());
            stmt.setLong(3, lance.getIdEdital());
            stmt.setLong(4, lance.getIdusuario());
            
            return stmt.executeUpdate();
            
        } catch(SQLException e) {
            e.printStackTrace();
                    
        }
        return 0;
    }
}
