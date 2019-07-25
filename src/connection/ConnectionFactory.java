package connection;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author Railander
 */
//classe de conexao
public class  ConnectionFactory {
    
    private static final String DRIVER = "com.mysql.jdbc.Driver";//informa o driver de conexao a ser usado
    private static final String URL = "jdbc:mysql://localhost:3306/dbcadastroclientes";//string de conexao: driver,rede,nome banco
    private static final String USER = "root";//usuario
    private static final String PASS = "";//senha
    
//metodo para obeter conexao
    public static  java.sql.Connection getConnection() {
        try {//tente
            Class.forName(DRIVER);//passa o driver para a classe
            return DriverManager.getConnection(URL, USER, PASS);//retorna conexao, dado a rede,usuario e senha
        } catch (ClassNotFoundException | SQLException ex) {//caso ocorra exceção
           // throw new RuntimeException(ex);
            JOptionPane.showMessageDialog(null, "Erro ao tentar conectar com servidor: ."+ex);//mostre o erro
        }
        return null;
    }
    
    //metodo para fechar conexao
    public static void closeConnection(Connection con) {//recebe um conection como parametro
        try {//tente
            if (con != null) {//se parametro nao for nulo
                con.close();//feche conexao
            }
        } catch (SQLException ex) {//caso exceção: 
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //sobrecarca do metodo fechar conexao , para 2 parametros
    public static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);//evita retratar comn try o mestodo anterior 
        try {//tente
            if (stmt != null) {//se parametro nao for nulo
                stmt.close();//feche conexao
            }
        } catch (SQLException ex) {//caso ocorra exceção
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //sobrecarga do metodo fechar conexao: para 3 parametros
    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);//evita retratar os 2 metodos  anteriores fechar conexao
        try {//tente
            if (rs != null) {//caso atributo nao for nulo
                rs.close();//feche a conexao
            }
        } catch (SQLException ex) {//se houver exceção:
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
