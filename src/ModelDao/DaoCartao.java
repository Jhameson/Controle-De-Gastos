/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelDao;

import ModelBeans.BeansCartao;
import ModelBeans.BeansUsuario;
import ModelConnection.Connection_BD;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jhame
 */
public class DaoCartao {

    Connection_BD conex = new Connection_BD();
    BeansCartao cart = new BeansCartao();

    public void SalvarCartaoDebito(BeansCartao mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("insert into cartao(id_user, numero, tipo, bandeira, apelido ) values (?,?,?,?,?)");

            pst.setInt(1, mod.getId());
            pst.setString(2, mod.getNumero());
            pst.setString(3, mod.getTipo());
            pst.setString(4, mod.getBandeira());
            pst.setString(5, mod.getApelido());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Cartao Cadastrado com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erros ao cadastrar: " + ex.getMessage());
        }
        conex.desconecta();
    }

    public void SalvarCartaoCredito(BeansCartao mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("insert into cartao(id_user, numero, tipo, bandeira, limite, valor_atual, dia_fechamento, apelido ) values (?,?,?,?,?,?,?,?)");

            pst.setInt(1, mod.getId());
            pst.setString(2, mod.getNumero());
            pst.setString(3, mod.getTipo());
            pst.setString(4, mod.getBandeira());
            pst.setDouble(5, mod.getLimite());
            pst.setDouble(6, mod.getValor());
            pst.setInt(7, mod.getDia_fechamento());
             pst.setString(8, mod.getApelido());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Cartao Cadastrado com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erros ao cadastrar: " + ex.getMessage());
        }
        conex.desconecta();
    }

    public void excluirTodosCartoes(BeansUsuario mod) {

        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("delete from cartao where id_user = ?");
            pst.setInt(1, mod.getId());
            pst.execute();

            //JOptionPane.showMessageDialog(null, "Usuário excluido com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "(dao_user)Erros ao excluir: " + ex.getMessage());
        }
        conex.desconecta();

    }

    public void editarCredito(BeansCartao mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("update cartao set  "
                    + "numero= ?,  bandeira= ?, limite= ?, valor_atual= ?, dia_fechamento = ?, apelido =? where id_cartao = ?");

            pst.setString(1, mod.getNumero());

            pst.setString(2, mod.getBandeira());
            pst.setDouble(3, mod.getLimite());
            pst.setDouble(4, mod.getValor());
            pst.setInt(5, mod.getDia_fechamento());
            pst.setString(6, mod.getApelido());
            pst.setInt(7, mod.getId_cartao());
            

            pst.execute();

            JOptionPane.showMessageDialog(null, "credito att sucess");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erros ao atualizar: " + ex.getMessage());
        }
        conex.desconecta();
    }

    public void editarDebito(BeansCartao mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("update cartao set  numero= ?,  bandeira= ?, apelido = ?  where id_cartao = ?");

            pst.setString(1, mod.getNumero());

            pst.setString(2, mod.getBandeira());
            pst.setString(3, mod.getApelido());
         
            pst.setInt(4, mod.getId_cartao());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Debito att sucess");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erros ao atualizar: " + ex.getMessage());
        }
        conex.desconecta();
    }

    public BeansCartao buscarCartao(BeansCartao mod) {
        conex.conexao();

        conex.executaSQL("select *from cartao where apelido like '"+mod.getPesquisa()+"'");
        try {
            conex.rs.first();
            mod.setId_cartao(conex.rs.getInt("id_cartao"));
            mod.setNumero(conex.rs.getString("numero"));
            mod.setBandeira(conex.rs.getString("bandeira"));
            mod.setTipo(conex.rs.getString("tipo"));
            mod.setValor(conex.rs.getDouble("valor_atual"));
            mod.setDia_fechamento(conex.rs.getInt("dia_fechamento"));
            mod.setLimite(conex.rs.getDouble("limite"));
            mod.setApelido(conex.rs.getString("apelido"));
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Cartão não encontrado! " +ex);
        }

        conex.desconecta();
        return mod;
    }
    
    public void excluir(BeansCartao mod) {
       
        
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("delete from cartao where id_cartao = ?");

            pst.setInt(1, mod.getId_cartao());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Cartao excluido com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erros ao excluir: " + ex.getMessage());
        }
        conex.desconecta();
        

    }

}
