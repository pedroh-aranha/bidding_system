package com.bidding.system.bidding.repository;

import com.bidding.system.bidding.model.VencedorBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Repositório responsável por registrar e consultar vencedores de editais.
 * 
 * Regra de desempate: em caso de empate no valor do lance (menor valor),
 * vence o fornecedor que registrou o lance PRIMEIRO (data_lance mais antiga).
 * Isso é justo porque quem chegou antes tem prioridade.
 */
@Repository
public class VencedorRepository {

    /**
     * Registra o vencedor de todos os editais recém-encerrados que ainda
     * não possuem vencedor cadastrado.
     *
     * Critério de seleção:
     *  1. Menor valor de lance (licitação é para quem oferece o menor preço)
     *  2. Em caso de empate de valor → ganha quem enviou o lance mais cedo (MIN data_lance)
     *  3. Se ainda houver empate (mesma data_lance) → ganha o lance com menor id (chegou primeiro no banco)
     */
    public void registrarVencedoresPendentes() {
        try {
            Connection conn = Conexao.conectar();

            /*
             * Seleciona o lance vencedor para cada edital ENCERRADO que ainda
             * não tem registro em vencedores_edital.
             *
             * Lógica de desempate:
             *   ORDER BY valor ASC, data_lance ASC, l.id ASC
             *   → menor preço → mais antigo em data → menor id (em último caso)
             *   LIMIT 1 por edital via subconsulta correlacionada
             */
            String sql = "INSERT INTO vencedores_edital (id_edital, id_usuario, id_lance) "
                    + "SELECT e.id, l.id_usuario, l.id "
                    + "FROM editais e "
                    + "JOIN lances l ON l.id_edital = e.id "
                    + "WHERE e.status = 'ENCERRADO' "
                    + "  AND e.id NOT IN (SELECT id_edital FROM vencedores_edital) "
                    + "  AND l.id = ( "
                    + "      SELECT id FROM lances "
                    + "      WHERE id_edital = e.id "
                    + "      ORDER BY valor ASC, data_lance ASC, id ASC "
                    + "      LIMIT 1 "
                    + "  )";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna todos os vencedores registrados, com dados do edital e do usuário.
     */
    public List<VencedorBean> listarTodos() {
        List<VencedorBean> lista = new ArrayList<>();
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT v.id, v.id_edital, v.id_usuario, v.id_lance, v.data_registro, "
                    + "       l.valor AS valor_lance, "
                    + "       e.titulo AS edital_titulo, u.nome AS usuario_nome "
                    + "FROM vencedores_edital v "
                    + "JOIN editais e ON e.id = v.id_edital "
                    + "JOIN usuarios u ON u.id = v.id_usuario "
                    + "JOIN lances l ON l.id = v.id_lance "
                    + "ORDER BY v.data_registro DESC"
            );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VencedorBean v = new VencedorBean();
                v.setId(rs.getLong("id"));
                v.setIdEdital(rs.getLong("id_edital"));
                v.setIdUsuario(rs.getLong("id_usuario"));
                v.setIdLance(rs.getLong("id_lance"));
                v.setValorLance(rs.getDouble("valor_lance"));
                v.setDataRegistro(rs.getDate("data_registro"));
                v.setEditalTitulo(rs.getString("edital_titulo"));
                v.setUsuarioNome(rs.getString("usuario_nome"));
                lista.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Retorna o vencedor de um edital específico.
     */
    public VencedorBean buscarPorEdital(Long idEdital) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT v.id, v.id_edital, v.id_usuario, v.id_lance, v.data_registro, "
                    + "       l.valor AS valor_lance, "
                    + "       e.titulo AS edital_titulo, u.nome AS usuario_nome "
                    + "FROM vencedores_edital v "
                    + "JOIN editais e ON e.id = v.id_edital "
                    + "JOIN usuarios u ON u.id = v.id_usuario "
                    + "JOIN lances l ON l.id = v.id_lance "
                    + "WHERE v.id_edital = ?"
            );
            stmt.setLong(1, idEdital);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                VencedorBean v = new VencedorBean();
                v.setId(rs.getLong("id"));
                v.setIdEdital(rs.getLong("id_edital"));
                v.setIdUsuario(rs.getLong("id_usuario"));
                v.setIdLance(rs.getLong("id_lance"));
                v.setValorLance(rs.getDouble("valor_lance"));
                v.setDataRegistro(rs.getDate("data_registro"));
                v.setEditalTitulo(rs.getString("edital_titulo"));
                v.setUsuarioNome(rs.getString("usuario_nome"));
                return v;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
