/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelDao;

import ModelBeans.BeansReceita;
import ModelBeans.BeansTransacao;
import ModelBeans.BeansUsuario;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import ModelConnection.*;

/**
 *
 * @author jhame
 */
public class DaoTransacao {

    Connection_BD conex = new Connection_BD();
    BeansTransacao beans_Transacao = new BeansTransacao();

    public void salvarTransacao(BeansTransacao mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("insert into transacao "
                    + "(id_user, valor, tipo, dia, mes, ano, categoria) values (?,?,?,?,?,?,?)");

            pst.setInt(1, mod.getId_user());
            pst.setDouble(2, mod.getValor());
            pst.setString(3, mod.getTipo());
            pst.setInt(4, mod.getDia());
            pst.setInt(5, mod.getMes());
            pst.setInt(6, mod.getAno());
            pst.setString(7, mod.getCategoria());
            pst.execute();

            //JOptionPane.showMessageDialog(null, "Transacao Cadastrado com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erros ao cadastrar transacao: " + ex.getMessage());
        }
        conex.desconecta();
    }

    public int retornaUltima() {
        conex.conexao();

        conex.executaSQL("select * from transacao where id_transacao = (select max(id_transacao) from transacao)");
        try {
            conex.rs.first();
            beans_Transacao.setId(conex.rs.getInt("id_transacao"));
            return beans_Transacao.getId();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao encontrar ID_Transa: " + ex);
        }

        conex.desconecta();
        return beans_Transacao.getId();
    }

    //Salva e atualiza
    public void SalvarAtualizando(BeansTransacao mod, int id) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("update transacao set valor = ?, dia = ?, mes = ?, ano = ?, categoria =? where id_transacao = '" + id + "'");

            pst.setDouble(1, mod.getValor());
            pst.setInt(2, mod.getDia());
            pst.setInt(3, mod.getMes());
            pst.setInt(4, mod.getAno());
            pst.setString(5, mod.getCategoria());

            pst.execute();

            //JOptionPane.showMessageDialog(null, "transacao Atualizada com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar transação: " + ex.getMessage());
        }
        conex.desconecta();
    }

    public void apagaInvalidos() {

        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("delete from transacao where ano = -1 and dia = -1 and categoria = '-1'");

            pst.execute();

            // JOptionPane.showMessageDialog(null, "Transacoes invalidas removidas");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao remover transações invalidas: " + ex.getMessage());
        }
        conex.desconecta();

    }

    public BeansTransacao buscarTransacao(BeansTransacao mod) {
        conex.conexao();

        conex.executaSQL("select *from transacao where  "
                + "mes  = '" + mod.getPesquisa() + "'");
        try {
            conex.rs.first();
            mod.setId(conex.rs.getInt("id_transacao"));
            mod.setTipo(conex.rs.getString("tipo"));
            mod.setValor(conex.rs.getDouble("valor"));
            mod.setDia(conex.rs.getInt("dia"));
            mod.setMes(conex.rs.getInt("mes"));
            mod.setAno(conex.rs.getInt("ano"));
            mod.setCategoria(conex.rs.getString("categoria"));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Categoria não encontrada!");
        }

        conex.desconecta();
        return mod;
    }

    public void excluirTrsancao(int id) {

        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("delete from transacao where id_transacao = '" + id + "'");

            pst.execute();

            JOptionPane.showMessageDialog(null, "Cartao excluido com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erros ao excluir: " + ex.getMessage());
        }
        conex.desconecta();

    }

    //usada quando o usuario for excluir  o perfil
    public void excluiTodasTransacoes(BeansUsuario mod) {

        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("delete from transacao where id_user = ?");
            pst.setInt(1, mod.getId());
            pst.execute();

            //JOptionPane.showMessageDialog(null, "Usuário excluido com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erros ao excluir transacoes multi: " + ex.getMessage());
        }
        conex.desconecta();

    }

    public void editarTransacao(BeansTransacao mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("update transacao set  valor= ?, "
                    + " dia = ?, mes = ?, ano = ?, categoria = ?  where id_transacao = ?");

            pst.setDouble(1, mod.getValor());

            pst.setInt(2, mod.getDia());
            pst.setInt(3, mod.getMes());
            pst.setInt(4, mod.getAno());
            pst.setString(5, mod.getCategoria());
            pst.setInt(6, mod.getId());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Transacao atualizada!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erros ao atualizar: " + ex.getMessage());
        }
        conex.desconecta();
    }

    //funcionalidades da tela de estatistica
    public double somarReceitasMes(int id, int mes, int ano) {
        conex.conexao();
        conex.executaSQL("select sum(valor) as soma_mensal_receitas from "
                + "transacao where id_user = '" + id + "'  and tipo = 'Receita' and mes = '" + mes + "' "
                + "and ano = '" + ano + "'");

        try {
            conex.rs.first();
            return conex.rs.getDouble("soma_mensal_receitas");
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro: "+ex);
        }
        conex.desconecta();
        return 0;
    }

    //metodo para a soma de despesas mensais;
    public double somarDespesasMes(int id, int mes, int ano) {
        conex.conexao();
        conex.executaSQL("select sum(valor) as soma_mensal_despesas from "
                + "transacao where id_user = '" + id + "'  and tipo = 'Despesa' and mes = '" + mes + "' "
                + "and ano = '" + ano + "'");

        try {
            conex.rs.first();
            return conex.rs.getDouble("soma_mensal_despesas");
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro: "+ex);
        }
        conex.desconecta();
        return 0;
    }

    //soma das receitas totais do ano
    public double somarReceitasAnuais(int id, int ano) {
        conex.conexao();
        conex.executaSQL("select sum(valor) as soma_anual_receitas from transacao "
                + "where id_user = '" + id + "'  and tipo = 'Receita' and ano = '" + ano + "'");

        try {
            conex.rs.first();
            return conex.rs.getDouble("soma_anual_receitas");
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro: "+ex);
        }
        conex.desconecta();
        return 0;
    }

    //soma das despesas totais do ano
    public double somarDespesaAnuais(int id, int ano) {
        conex.conexao();
        conex.executaSQL("select sum(valor) as soma_anual_despesa from transacao "
                + "where id_user = '" + id + "'  and tipo = 'Despesa' and ano = '" + ano + "'");

        try {
            conex.rs.first();
            return conex.rs.getDouble("soma_anual_despesa");
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro: "+ex);
        }
        conex.desconecta();
        return 0;
    }

    //media das receitas totais do ano
    public double mediaReceitasAnuais(int id, int ano) {
        conex.conexao();
        conex.executaSQL("select sum(valor/12) as media_anual_receitas from transacao "
                + "where id_user = '" + id + "'  and tipo = 'Receita' and ano = '" + ano + "'");

        try {
            conex.rs.first();
            return conex.rs.getDouble("media_anual_receitas");
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro: "+ex);
        }
        conex.desconecta();
        return 0;
    }

    public double mediaDespesasAnuais(int id, int ano) {
        conex.conexao();
        conex.executaSQL("select sum(valor/12) as media_anual_despesa from transacao "
                + "where id_user = '" + id + "'  and tipo = 'Despesa' and ano = '" + ano + "'");

        try {
            conex.rs.first();
            return conex.rs.getDouble("media_anual_despesa");
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro: "+ex);
        }
        conex.desconecta();
        return 0;
    }

    //maior receita do ano e em que mês ela está 
    public BeansTransacao maiorReceitaAno(BeansTransacao mod, int id, int ano) {
        conex.conexao();
        conex.executaSQL("select max(valor) as maior from transacao "
                + "where id_user = '"+ id +"' and tipo = 'Receita' "
                        + " and ano = '" + ano + "'");

        try {
            conex.rs.first();
            mod.setValor(conex.rs.getDouble("valor"));
           

        } catch (SQLException ex) {
            // JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        conex.desconecta();
        return mod;
    }
     
    

    public BeansTransacao maiorDespesaAno(BeansTransacao mod, int id, int ano) {
        conex.conexao();
        conex.executaSQL("select valor,mes,categoria from transacao "
                + "where valor =( select max(valor) from transacao  where tipo = 'Despesa') and id_user = '" + id + "' "
                + "and ano = '" + ano + "'");

        try {
            conex.rs.first();
            mod.setValor(conex.rs.getDouble("valor"));
            mod.setMes(conex.rs.getInt("mes"));
            mod.setCategoria(conex.rs.getString("categoria"));
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        conex.desconecta();
        return mod;
    }

    public BeansTransacao maiorReceitaMes(BeansTransacao mod, int id, int ano, int mes) {
        conex.conexao();
        conex.executaSQL("select max(valor) as maior from transacao "
                + "where id_user = '"+ id +"' and tipo = 'Receita' "
                        + "and mes = '" + mes + "' and ano = '" + ano + "'");

        try {
            conex.rs.first();
            mod.setValor(conex.rs.getDouble("maior"));
            return mod;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        conex.desconecta();
        return mod;
    }

    public BeansTransacao maiorDespesaMes(BeansTransacao mod, int id, int ano, int mes) {
        conex.conexao();
        conex.executaSQL("select max(valor) as maior_receita_mes "
                + "from transacao where id_user = '" + id + "'  "
                + "and tipo = 'Despesa' and mes = '" + mes + "' "
                + "and ano = '" + ano + "'");

        try {
            conex.rs.first();
            mod.setValor(conex.rs.getDouble("maior_receita_mes"));
           
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        conex.desconecta();
        return mod;
    }
}
