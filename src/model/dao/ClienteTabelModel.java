package model.dao;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.bean.Cliente;

/**
 * @author Railander
 */
//classe "table model personalizada"  implementação da tabelModel
public class ClienteTabelModel extends AbstractTableModel {//herda de AbstactTabelModel

    private final List<Cliente> valores;//lista do tipo clientes (dados)
    private final String[] colunas = {"ID", "NOME", "EMAIL"};//string para nomes das colunas da tabela
    //ATRIBUTOS para enumerar indices de coluna
    private static final int COL_ID = 0;
    private static final int COL_NOME = 1;
    private static final int COL_EMAIL = 2;

    //construtor - inicializado com lista valores do tipo cliente q recebeu por parametro
    public ClienteTabelModel(List<Cliente> valores) {
        this.valores = valores;
    }

    //reescrita do metodo abstrato "conta linhas"
    @Override
    public int getRowCount() {
        return valores.size();//retorna qnts linhas existem na tabela
    }

    //reescrita do metodo abstrato: "conta colunas"
    @Override
    public int getColumnCount() {
        return 3;//retorna o numero de colunas da tabela
    }

    //reescrita do metodo q retorna dados de obejeto de acordo com indice de linha e de coluna da tabela
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {//recebe indice da linha e de coluna clicada na tabela
        Cliente cli = valores.get(rowIndex); //informa a linha acessada
        switch (columnIndex) {//verifique caso de indice de coluna
            case COL_ID:  // caso 0:
                return cli.getId();//retorne o id do objeto
            case COL_NOME: //caso seja 1
                return cli.getNome();//retorne o nome do cliente
            case COL_EMAIL://caso seja 3
                return cli.getEmail();//retorne o email do cliente
            default://se nehuma das alternarivas..
                break;//pare
        }
        return null;
    }
    //reescrita do metodo que "Nomeia" as colunas da tabela
    @Override
    public String getColumnName(int column) {//recebe como parametro o indice da coluna
        String coluna = "";//nome da coluna 
        switch (column) {
            case COL_ID://caso id for 0
                coluna = "ID";//nomeie com ID
                break;
            case COL_NOME://caso id for 1
                coluna = "NOME";//nomeie com NOME
                break;
            case COL_EMAIL://caso id for 2
                coluna = "EMAIL";//nomeie com EMAIL
                break;
            default://caso nenhuma das alternativas..
                throw new IllegalArgumentException("Coluna inválida!");
        }
        return coluna;
    }
    //reescrita do metodo que retorna dados de uma classe
    @Override
    public Class<?> getColumnClass(int i) {
        switch (i) {
            case COL_ID:
                return Long.class;
            case COL_NOME:
                return String.class;
            case COL_EMAIL:
                return String.class;
            default:
                break;
        }
        return null;
    }
    //reescrita do metodo: retorna valores dado um indice de linha
    public Cliente get(int row) {
        return valores.get(row);
    }

}
