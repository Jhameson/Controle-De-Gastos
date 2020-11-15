/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelDao;

import ModelBeans.BeansReceita;
import ModelBeans.BeansUsuario;

import java.sql.*;

import ModelConnection.Connection_BD;

import javax.swing.JOptionPane;

/**
 *
 * @author jhame
 */
public class DaoReceita {

    Connection_BD conex = new Connection_BD();
    BeansReceita mod = new BeansReceita();

    //metodo para salvar receita / atualizar transacao
    public void Salvar(BeansReceita mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("insert into receitas(id_transacaor, id_user_receita) values (?,?)");
            
            pst.setInt(1, mod.getId_transacao());
            pst.setInt(2, mod.getId_user_receita());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Receita Cadastrado com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erros ao cadastrar: " + ex.getMessage());
        }
        conex.desconecta();
    }

    public BeansReceita buscaReceita(BeansReceita br) {
        conex.conexao();
        conex.executaSQL("select *from receitas where categoria like'%" + br.getPesquisa() + "%'");
        try {
            conex.rs.first();

            // br.setId(conex.rs.getInt("id_user"));
            br.setValor(conex.rs.getDouble("valor"));
            br.setCategoria(conex.rs.getString("categoria"));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na busca: " + ex.getMessage());
        }

        conex.desconecta();
        return br;
    }

    //metodo para somar o valor em receitas
    public double somarReceitasMes(int id, int mes, int dia) {
        conex.conexao();
        conex.executaSQL("select sum(valor) as soma from transacao where id_user = '" + id + "' "
                + "and mes = '" + mes + "' and tipo = 'Receita'");

        try {
            conex.rs.first();
            //JOptionPane.showMessageDialog(null, "id "+id);
            // JOptionPane.showMessageDialog(null, "mes "+mes);
            //JOptionPane.showMessageDialog(null, "dia "+dia);
            //JOptionPane.showMessageDialog(null, "deu merda "+conex.rs.getInt("soma"));
            return conex.rs.getDouble("soma");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "deu merda");
        }
        conex.desconecta();
        return 0;
    }
    
    public double somarReceitasTodas(int id, int mes, int dia) {
         //JOptionPane.showMessageDialog(null, "deu merda aqui");
        conex.conexao();
        conex.executaSQL("select sum(valor) as soma from transacao where id_user = '" + id + "' and tipo = 'Receita'");

        try {
            conex.rs.first();
            //JOptionPane.showMessageDialog(null, "id "+id);
            // JOptionPane.showMessageDialog(null, "mes "+mes);
            //JOptionPane.showMessageDialog(null, "dia "+dia);
            //JOptionPane.showMessageDialog(null, "deu merda "+conex.rs.getInt("soma"));
           // JOptionPane.showMessageDialog(null, "deu merda "+conex.rs.getInt("soma"));
            return conex.rs.getDouble("soma");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "deu merda");
        }
        return 0;
    }

    //exclui todas as receitas de um determinado usuario quando ele for ecluir o perfil
    public void excluirTodasReceitas(BeansUsuario mod) {

        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("delete from receitas where id_user_receita = ?");
            pst.setInt(1, mod.getId());
            pst.execute();

            //JOptionPane.showMessageDialog(null, "Usuário excluido com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "aqir: " + ex.getMessage());
        }
        conex.desconecta();

    }

    public void excluirReceita(int id) {

        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("delete from receitas where id_transacaor = '" + id + "'");

            pst.execute();

            JOptionPane.showMessageDialog(null, "Receita excluido com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erros ao excluir: " + ex.getMessage());
        }
        conex.desconecta();

    }
    

}
