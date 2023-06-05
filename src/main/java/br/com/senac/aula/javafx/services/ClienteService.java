package br.com.senac.aula.javafx.services;

import br.com.senac.aula.javafx.db.ConexaoDataBase;
import br.com.senac.aula.javafx.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {
    private static ConexaoDataBase conexao = new ConexaoDataBase();

    public static List<Cliente> carregarClientes(){
        List<Cliente> out = new ArrayList<>();

        try {
            Connection conn = conexao.getConexao();

            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery("select * from clientes;");

            while (rs.next()){
                Cliente cli = new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("documento"));

                out.add(cli);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return out;
    }

    public static void inserirCliente(Cliente cliente){
        try {
            Connection conn = conexao.getConexao();

            String sqlInsert = "insert into clientes (nome, documento) values (?, ?)";

            PreparedStatement pre = conn.prepareStatement(sqlInsert);
            pre.setString(1, cliente.getNome());
            pre.setString(2, cliente.getDocumento());

            pre.execute();

            pre.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deletarCliente(int idCliente){
        try {
            Connection conn = conexao.getConexao();

            String deleteSql = "delete from clientes where id = ?";

            PreparedStatement ps = conn.prepareStatement(deleteSql);
            ps.setInt(1, idCliente);

            return ps.execute();
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public static boolean atualizarCliente(int idCliente, Cliente cli){
        try {
            Connection conn = conexao.getConexao();

            String updateSql = "update clientes set nome = ?, documento = ? where id = ?";

            PreparedStatement ps = conn.prepareStatement(updateSql);
            ps.setString(1, cli.getNome());
            ps.setString(2, cli.getDocumento());
            ps.setInt(3, idCliente);

            return ps.execute();
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public static boolean buscarClienteByDocumento(String documento){
        try{
            Connection conn = conexao.getConexao();

            String selectSql = "select id from clientes where documento = '" + documento + "'";

            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery(selectSql);

            return rs.next();
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

}
