package model.dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import model.bean.Cliente;

/**
 * @author Railander
 */
    //classe DAO data acess object - intermediadora  CRUD entre programa e banco de dados
public class ClienteDAO {//crud DAO: data acess object
    
    //metodo que cadastra cliente no banco, recebendo um objt como paramentro (objeto criado qnd preenche as textfields)
    public int inserir(Cliente cli) {
        Connection con = null;
        PreparedStatement stmt = null;//cria atributo tipo para execução de comando SQL
        int result = 0; //para saber se houve inserção..
        try {//tente
            con = ConnectionFactory.getConnection();//abre conexao pelos metodos da classe Connection
            //query: insira em tabela clientes  nos campos... os valores...
            stmt = con.prepareStatement("INSERT INTO clientes(nome,telresidencial,telcomercial,telcelular,email)VALUES(?,?,?,?,?)");
            //valores a ser substituidos pelos "?" do SQL acima, prezando pela sequencia das variaveis
            stmt.setString(1, cli.getNome());
            stmt.setString(2, cli.getTelResidencial());
            stmt.setString(3, cli.getTelComercial());
            stmt.setString(4, cli.getTelCelular());
            stmt.setString(5, cli.getEmail());
            result = stmt.executeUpdate();//passa o comando SQL e devolve resutado da execução para variaveç
        } catch (SQLException ex) {//caso haja problemas ao tentar cadastrar
            JOptionPane.showMessageDialog(null, ex);//mostre
        } finally {//de qualque forma (erro ou nao): 
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt);//fecha a conexão com o banco
        }
        return result;//se inseriu restorna 1 
    }
    
    //metodo que realiza busca no banco e retorna os dados a uma lista -Utilizada para mostrar dados na tabela
    public List<Cliente> find() {//LE DO BANCO e joga na table
        Connection con = null;//atributo tipo Connection
        PreparedStatement stmt = null;//atributo que passa o comando SQL ao banco
        ResultSet rs = null;//atributo que recebera a busca no banco
        List<Cliente> clientes = new ArrayList<>();//lista tipo cliente 
        try {//tente
            con = ConnectionFactory.getConnection();//obeter conexao
            stmt = con.prepareStatement("SELECT id,nome,email FROM clientes");//passar o comando SQL
            rs = stmt.executeQuery();//executa a query e devolve ao atributo rs o resultado
            while (rs.next()) {//enquanto ouver algo no atributo q recebeu a busca no banco
                Cliente cli = new Cliente();//crie um cliente
                cli.setId(rs.getLong("id"));//set o id desse cliente com o id do cliente retornado na busca
                cli.setNome(rs.getString("nome"));//set o nome desse cliente com o nome do cliente retornado na busca
                cli.setEmail(rs.getString("email"));//set o email desse cliente com o email do cliente retornado na busca
                clientes.add(cli);//adcione na lista, o objeto criado e setado acima
            }
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt, rs);//feche a conexao
        } catch (SQLException ex) {//se erro ao tentar
            JOptionPane.showMessageDialog(null, ex);//mostre
        } finally {//executando ou nao, feche a conexao com o banco
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt, rs);
        }
        return clientes;//retorna a lista clientes
    }
    
    //metodo que atualiza cadstro existente
    public int upDate(Cliente cli) {//recebe um objeto cliente como parametro
        Connection con = null;
        PreparedStatement stmt = null;//cria atributo tipo para execução de comando SQL
        int result = 0;
        try {//tente :
            con = ConnectionFactory.getConnection();//abre conexão
            //passa o comando sql (update) - UPDATE cliente SET ... 
            stmt = con.prepareStatement("UPDATE  clientes SET nome = ?,telresidencial = ?, telcomercial = ?,telcelular = ?,email = ? WHERE id = ?");
            //passa para o banco, o valor dos campos "?" acima, prezando a ordem sequencial
            stmt.setString(1, cli.getNome());
            stmt.setString(2, cli.getTelResidencial());
            stmt.setString(3, cli.getTelComercial());
            stmt.setString(4, cli.getTelCelular());
            stmt.setString(5, cli.getEmail());
            stmt.setLong(6, cli.getId());
            result = stmt.executeUpdate();//execute o comando SQL e devolva o resultado (true/false) para result
            return result;//retorne o recutado (auxilia para saber se houve ou nao atualização)
        } catch (SQLException e) {//caso nao seja realizada a tentativa (haja exceeção)
            JOptionPane.showMessageDialog(null, e);//mostre
        } finally {//de qualquer forma (com exceção ou nao)
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt, null);//feche a conexao
            return result;
        }
    }
    
    //metodo que DELETA cadastro do banco
    public int excluir(Long id) {//recebe como parametro o id do cadastro que deseja remover
        Connection  con = null;
        PreparedStatement pstm = null;//cria atributo tipo para execução de comando SQL
        int result = 0;//saber se houve ou nao exclusao
        try {//tente
            con = ConnectionFactory.getConnection();//abrir conexao
            //passa o comando sql para deletar tudo do cliente onde id seja.. 
            pstm = con.prepareStatement("DELETE FROM clientes WHERE id=?");
            pstm.setLong(1, id);//numero do campo, e variavel do campo "?" acima
            result = pstm.executeUpdate();
        } catch (SQLException e) {//caso ocorra exceção, mostre
            JOptionPane.showMessageDialog(null, e);//mostre
        } finally {//de qualquer forma:
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, pstm, null);//feche a conexao
        }
        return result;//retorne o resultado
    }
    
    //metodo auxiliar para preencher as textfiels, ja que o metodo FIND retorna somente os campos id,nome,email (existentes na table)
    public List<Cliente> PreencheTf(Long id) {//recebe como parametro o id do cliente
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();//cria lista tipo cliente, a qual é retornada para preencher as textfields
        try {//tente
            con = ConnectionFactory.getConnection();//anrir conexao
            //passe o comando sql, e devolva para srmt
            stmt = con.prepareStatement("SELECT id,nome,telresidencial,telcomercial,telcelular,email FROM clientes WHERE id=" + id);
            rs = stmt.executeQuery();//resultado recebe o tetorno da query 
            while (rs.next()) {//enquanto ouver algo em rs..
                Cliente cli = new Cliente();//crie um objeto
                //seta todos os atributos deste objeto com os atributos do objeto da busca (copia de objetos)
                cli.setId(rs.getLong("id"));
                cli.setNome(rs.getString("nome"));
                cli.setTelResidencial(rs.getString("telresidencial"));
                cli.setTelComercial(rs.getString("telcomercial"));
                cli.setTelCelular(rs.getString("telcelular"));
                cli.setEmail(rs.getString("email"));
                clientes.add(cli);//adcione o objeto (copiado acima) na lista
            }
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt, rs);//feche a conecao
        } catch (SQLException ex) {//caso exceção, informe:
            JOptionPane.showMessageDialog(null, ex);
        } finally {//de qualquer forma:
            ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) con, stmt, rs);//feche a conexao
        }
        return clientes;
    }
}
