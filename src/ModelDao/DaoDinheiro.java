/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelDao;

import ModelBeans.BeansDinheiro;
import ModelBeans.BeansUsuario;
import ModelConnection.Connection_BD;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jhame
 */
public class DaoDinheiro {

    //BeansUsuario beans_user = new BeansUsuario();
    BeansDinheiro beans_dinheiro = new BeansDinheiro();
    Connection_BD conex = new Connection_BD();

    public boolean verificacaoDeExistencia(int id) {
        conex.conexao();
        conex.executaSQL("select id_user from dinheiro where id_user = '" + id + "'");
        try {
            conex.rs.first();
            beans_dinheiro.setId_user(conex.rs.getInt("id_user"));
            conex.desconecta();
            return true;

        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, "OK");
        }
        conex.desconecta();
        return false;
    }

    public void quantiaInicial(int id) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("insert into dinheiro (id_user, quantia) values ('" + id + "','" + 0.0 + "')");
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na quantia inicial: " + ex);
        }

        conex.desconecta();
    }

    public double quantiaTotal(int id) {
        conex.conexao();
        conex.executaSQL("select quantia from dinheiro where id_user = '" + id + "'");
        try {
            conex.rs.first();
            beans_dinheiro.setQuantia(conex.rs.getDouble("quantia"));
            
            return beans_dinheiro.getQuantia();

        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, "OK");
        }
        conex.desconecta();
        return 0;
    }

    public void excluirTodos(BeansUsuario mod) {

        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("delete from dinheiro where id_user = ?");
            pst.setInt(1, mod.getId());
            pst.execute();

            //JOptionPane.showMessageDialog(null, "Usuário excluido com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erros ao excluir dinheiro: " + ex.getMessage());
        }
        conex.desconecta();

    }

    public void atualizarCarteira(int id, double valor) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("update dinheiro set quantia = '" + valor + "' where id_user = '" + id + "'");

            pst.execute();

            //JOptionPane.showMessageDialog(null, "transacao Atualizada com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar transação: " + ex.getMessage());
        }
        conex.desconecta();
    }

}
