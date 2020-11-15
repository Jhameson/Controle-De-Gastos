/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package ModelConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


//testando essa merda carallho
/**
 *
 * @author jhame
 */
public class Connection_BD {

    int teste;
    //acesar de outras classes
    public Statement stm; //realiza pesquisa no BD
    public ResultSet rs; //armazeza resultado da pesquisa

    private String driver = "org.postgresql.Driver"; //identifica o serviço do BD ps
    private String caminho = "jdbc:postgresql://localhost:5432/db_financas";
    private String usuario = "postgres"; //user bd
    private String senha = "123456";
    public Connection con;//CONEXÃO BD

    public void conexao() {
        System.setProperty("jdbc.Drivers", driver); //setar a propriedade do drive de conexao
        try {
            con = DriverManager.getConnection(caminho, usuario, senha);
            //JOptionPane.showMessageDialog(null, "Conectado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "(Connection_BD)Error na conexão: " + ex.getMessage());
        }

    }
    public void executaSQL(String sql){
        try {
            //difere a pesquisa de maiusculo e minusculo // percorre do inicio ao fim e do fim ao inicio
            stm = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY); //percorre do inicio ao fim e  vice versa
            rs = stm.executeQuery(sql); 
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "(Connection_BD)Error ExecutaSQL: " + ex.getMessage());
        }
        
    }
    
    public void teste(){
        System.out.println("Mensagem");
    }
    
    
    
    
    public void desconecta() {
        try {
            con.close();
            //JOptionPane.showMessageDialog(null, "Desconectado com sucesso");
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Falha ao desconectar: "+ex.getMessage());
        }
    }
}
