/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelDao;

import java.sql.*;

import ModelBeans.BeansUsuario;
import ModelConnection.Connection_BD;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jhame
 */
public class DaoUser {

    Connection_BD conex = new Connection_BD();
    BeansUsuario mod = new BeansUsuario();
    DaoReceita dao_receita = new DaoReceita();
    DaoDespesa dao_despesa = new DaoDespesa();
    DaoCartao dao_cartao = new DaoCartao();
    DaoTransacao dao_transacao = new DaoTransacao();
    DaoCategoria dao_categoria = new DaoCategoria();
    DaoDinheiro dao_dinheiro = new DaoDinheiro();

    public void Salvar(BeansUsuario mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("insert into usuario"
                    + " (user_nome, user_email, user_usuario, user_senha) values (?,?,?,?)");

            pst.setString(1, mod.getNome());
            pst.setString(2, mod.getEmail());
            pst.setString(3, mod.getUsuario());
            pst.setString(4, mod.getSenha());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Usuário Cadastrado com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erros ao cadastrar: " + ex.getMessage());
        }
        conex.desconecta();
    }
    
    public int retornaUltimo() {
        conex.conexao();

        conex.executaSQL("select user_id from usuario where user_id = (select max(user_id ) from usuario)");
        try {
            conex.rs.first();
            mod.setId(conex.rs.getInt("user_id "));
            return mod.getId();

        } catch (SQLException ex) {
           // JOptionPane.showMessageDialog(null, "Erro ao encontrar ID_Transa: " + ex);
        }

        conex.desconecta();
        return mod.getId();
    }

    public void editar(BeansUsuario mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("update usuario set user_nome = ?, "
                    + "user_email = ?, user_senha = ?, user_usuario = ? where user_id = ?");

            pst.setString(1, mod.getNome());
            pst.setString(2, mod.getEmail());
            pst.setString(3, mod.getSenha());
             pst.setString(4, mod.getUsuario());
            pst.setInt(5, mod.getId());
           

            pst.execute();

            JOptionPane.showMessageDialog(null, "Usuário Atualizado com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "(dao_user)Erros ao atualizar: " + ex.getMessage());
        }
        conex.desconecta();
    }

    /*
    Olá Fredson, você consegue fazendo um DROP TABLE. Caso apresente algum erro, 
    será necessário tirar a chave estrangeira da tabela que deseja excluirReceita.
    Para deletar a FK:
    ALTER TABLE tabela DROP FOREIGN KEY fk;
    Agora devemos deletar a Index dessa FK:
    ALTER TABLE tabela DROP INDEX fk;
    Espero ter ajudado.
     */
    
    public void excluir(BeansUsuario mod) {
        //exclui todos os dados do usuario que estejam em outras tabelas
        dao_categoria.excluirTodasCategorias(mod);
        dao_dinheiro.excluirTodos(mod);
        dao_receita.excluirTodasReceitas(mod);
        dao_despesa.excluirTodasDespesas(mod);
        
        dao_cartao.excluirTodosCartoes(mod);
        dao_transacao.excluiTodasTransacoes(mod);
        
        
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("delete from usuario where user_id = ?");

            pst.setInt(1, mod.getId());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Usuário excluido com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "(dao_user)Erros ao excluir: " + ex.getMessage());
        }
        conex.desconecta();

    }

    public BeansUsuario buscarUser(BeansUsuario mod) {
        conex.conexao();

        conex.executaSQL("select *from usuario where user_id = '" + mod.getUser_pesquisa() + "'");
        try {
            conex.rs.first();
            mod.setId(conex.rs.getInt("user_id"));
            mod.setNome(conex.rs.getString("user_nome"));
            mod.setEmail(conex.rs.getString("user_email"));
            mod.setUsuario(conex.rs.getString("user_usuario"));
            mod.setSenha(conex.rs.getString("user_senha"));
           
            return mod;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao encontrar User: " + ex);
        }

        conex.desconecta();
        return mod;
    }

    //metodo para verificar se o nome do usuario ja existe
    public boolean verificarExiste(String nome) {

        conex.conexao();
        conex.executaSQL("select user_usuario from usuario where user_usuario = '" + nome + "'");
        try {
            conex.rs.first();
            mod.setUsuario(conex.rs.getString("user_usuario"));
            conex.desconecta();
            return true;
            //JOptionPane.showMessageDialog(null, "OK existe");

        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, "OK");
        }
        conex.desconecta();
        return false;
    }

    //metodo para retornar a id
    public int retornaId(String nome) {

        conex.conexao();
        conex.executaSQL("select user_id from usuario where user_usuario = '" + nome + "'");
        try {
            conex.rs.first();
            mod.setId(conex.rs.getInt("user_id"));
            return mod.getId();
            //JOptionPane.showMessageDialog(null, "OK existe");

        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, "OK");
        }
        return mod.getId();
    }

}
