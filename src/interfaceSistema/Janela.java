package interfaceSistema;

import controler.ClienteController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import model.bean.Cliente;
import model.dao.ClienteDAO;
import model.dao.ClienteTabelModel;

/**
 * @author Railander
 */
public final class Janela extends JFrame {

    private String botaoAnterior = "-";//auxiliar p indicar qual ultimo botao clicado antes de CONFIRMAR -"td ação deve ser confirmada"
    private JScrollPane scrol;
    private JTable jtab;
    private final JTextField tfId = new JTextField(2);

    public JTextField tfNome, tfTr, tfTc, tfTcel, tfEmail;
    private final JButton btInserir = new JButton("Inserir");
    private final JLabel LB = new JLabel();
    private final JButton btRemover = new JButton("Remover");
    private final JButton btAlterar = new JButton("Alterar");
    private final JButton btConfirmar = new JButton("Confirmar");
    private final JButton btCancelar = new JButton("Cancelar");
    private final JButton btSair = new JButton("Sair");
    private final Toolkit kit = Toolkit.getDefaultToolkit();
    private final Dimension tamanhoDaTela = kit.getScreenSize();
    private final int alturaDaTela = tamanhoDaTela.height;
    private final int larguraDaTela = tamanhoDaTela.width;
    private final JPanel p_jpBt = new JPanel(new BorderLayout());
    private final JPanel jpBt = new JPanel(new GridLayout(1, 5, 7, 0));
    private final JPanel jp1 = new JPanel(new GridLayout(5, 1));
    private final JPanel jpIdNome = new JPanel(new BorderLayout(10, 5));
    private List<Cliente> clientelist;
    private List<Cliente> clientesTf = new ArrayList<>();
    private Long id;

    //configurando o frame
    public Janela() {
        super("Cadastro de Clientes");
        tfId.setEnabled(false);
        labelsETextfields(); //chamo o metodo que "configura" labels,texffields e adciona aos paineis
        this.setLayout(new GridLayout(2, 1)); //defino layout do frame para receber dois subpaineis (jp1 e painel da table)
        this.setSize(larguraDaTela / 2, alturaDaTela / 2);
        this.setLocation(larguraDaTela / 4, alturaDaTela / 4);
        this.pack();
        this.setVisible(true);//deixa visivel
        this.setResizable(true); //Deixa possivel a janela redimensionavel pelo pelo mouse
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sai qnd fecha
    }

    //Labels,textfields, e jtable
    void labelsETextfields() {
        //define layout para os paineis
        JPanel jpId = new JPanel(new GridLayout(2, 1));
        JPanel jpNome = new JPanel(new GridLayout(2, 1));

        JLabel jlId = new JLabel("ID:");//cria label setando o nome
        JLabel jlNome = new JLabel("Nome:");
        //adcionando labels e textfields ao painel
        jpId.add(jlId);
        jpId.add(tfId);
        jpIdNome.add(jpId, BorderLayout.WEST);

        tfNome = new JTextField();
        jpNome.add(jlNome);
        jpNome.add(tfNome);
        jpIdNome.add(jpNome, BorderLayout.CENTER);

        //painel "pai" que recebe outros "sub-paineis" de telefone
        JPanel jpTels = new JPanel(new GridLayout(1, 3, 10, 0));//define layout (lin,col,bordaX,bordaY))

        JPanel jpTr = new JPanel(new GridLayout(2, 1, 10, 0));//painel "filho" para  tfTr e jlTr
        JLabel jlTr = new JLabel("Tel. Residencial:");
        tfTr = new JTextField();
        jpTr.add(jlTr);//adciona label ao painel filho
        jpTr.add(tfTr);//adciona textfield ao painel filho
        jpTels.add(jpTr);//adciona o painel filho ao painel pai

        //repete os feitos  anterior (no telefone residencial)  
        JPanel jpTc = new JPanel(new GridLayout(2, 1));
        JLabel jlTc = new JLabel("Tel. Comercial:");
        tfTc = new JTextField();
        jpTc.add(jlTc);
        jpTc.add(tfTc);
        jpTels.add(jpTc);

        //repete os feitos  anterior (no telefone comercial)  
        JPanel jpTcel = new JPanel(new GridLayout(2, 1));
        JLabel jlTcel = new JLabel("Tel. Celular:");
        tfTcel = new JTextField();
        jpTcel.add(jlTcel);
        jpTcel.add(tfTcel);
        jpTels.add(jpTcel);

        //painel para email
        JPanel jpEmail = new JPanel(new GridLayout(2, 1));
        JLabel jlEmail = new JLabel("E-mail:");//cria labelEmail
        tfEmail = new JTextField();//textfield email
        jpEmail.add(jlEmail);//adciona label ao painel
        jpEmail.add(tfEmail);//adciona textfiels ao painel

        //painel de botoes ao principal: ("pai" dos subpaineis de botoes ) 
        p_jpBt.add(jpBt, BorderLayout.WEST);
        /* JPanel jpbS = new JPanel(new GridLayout(1, 2, 10, 10));//painel do botao sair
        p_jpBt.add(jpbS);*/

        //Painel que recebe os paineis de nome,telefones e o de email
        jp1.setBorder(new EmptyBorder(10, 10, 10, 10)); //define bordas
        jp1.add(jpIdNome);//adciono ao painel principal o subpainel idnome (que contem ainda textfields e labels de id e de nome)
        jp1.add(jpTels);//adciono ao painel principal o subpainel jptel (que contem ainda outros subpaineis d cada tipo de tel)
        jp1.add(jpEmail);//adciono por ultimo a esse painel, o painel de email, que contem textfield e label
        botoes(); //chamo o metodo que "trata" as ações de cada botao, e ainda os adcionam ao painel

        //define um painel para table 
        JPanel jpTable = new JPanel(new GridLayout(1, 1, 10, 10));//lin, col, e bordas painel
        jpTable.setBorder(BorderFactory.createTitledBorder("Clientes cadastrados:"));//separador
        jtab = new JTable();
        jtab.addMouseListener(new MouseAdapter() {//adciono a tabel no modo "ouvinte" para reconhecer qnd clicar
            @Override
            public void mouseClicked(MouseEvent me) {//reescrevo o metodo
                bloqueiaDesbloqueiaBtTF(true);//chamo o metodo para dessbloquear botoes
                //bloqueio os seguinbtes botoes
                btConfirmar.setEnabled(false);
                btCancelar.setEnabled(false);
                btSair.setEnabled(true);
                transcreva();//chamo o metodo que 
            }
        ;
        });
    
        //crio um scrollpane(barra de rolagem) e adciono a table
        scrol = new JScrollPane(jtab);
        scrol.setPreferredSize(jpTable.getPreferredSize());//configuro tamanho do scrol com tamanho da tabel

        jpTable.add(scrol);//adciono ao painel de tabel o scrol
        //chamo o metodo responsavel por chamar o metodo de "select" (busca no banco) - mostrando os registros na tabela
        atualizaJtable();
        /*adciono ao meu frame os 2 paineis criados: painel "norte"(que contem todas as textfields, labels e botoes
            painel "sul"(que contem o scrol com tabel)
         */
        this.add(jp1);
        this.add(jpTable);
    }

    //metodo em que implemento as açoes de botoes, os adciono ao painel
    void botoes() {
        bloqueiaDesbloqueiaBtTF(false);//bloqueio todos os botões e campos

        btAlterar.addActionListener(new ActionListener() { //implementação do botao alterar, reescrita do metodo:
            @Override
            public void actionPerformed(ActionEvent ae) {//qnd clicar em alterar:
                botaoAnterior = "btAlterar";//"informa" ao "botaoAnterior"(saber ultimo bt clicado)
                btConfirmar.setEnabled(true);//desbloqueio o botao confirmar
            }
        });

        btCancelar.addActionListener(new ActionListener() {//implementação da ação do botao cancelar
            @Override
            public void actionPerformed(ActionEvent ae) {//qnd cancelar for clicado:
                limpaCampos();//chama o metodo que limpa todos os campos e btd
                botaoAnterior = "-";//tira da variavel botaoAnterior a identificação do ultimo botao clicado
            }
        });
        btConfirmar.addActionListener(new ActionListener() { //implementação do botao confirmar:
            @Override
            public void actionPerformed(ActionEvent ae) { //qnd clicar em confirmar:
                switch (botaoAnterior) {
                    case "btAlterar":
                        //se botaoAnterior foi btAlterar:
                        updateCliente();//chama o metodo que atualiza cadastro
                        bloqueiaDesbloqueiaBtTF(false);//bloqueia todos os campos e bts
                        limpaCampos();//limpa os campos
                        break;
                    case "btInserir":
                        //se botaoAnterior foi o btinserir
                        insertCliente();//chama o metodo responsavel por cadastrar cliente
                        bloqueiaDesbloqueiaBtTF(false);//bloqueio todos os campos e bts
                        limpaCampos();//limpa os campos
                        break;
                    case "btRemover":
                        //se botaoAnterior foi o brRemover:
                        deleteCliente();//chama o metodo responsavel por remover cadastro
                        bloqueiaDesbloqueiaBtTF(false);//bloqueio todos os campos e botoes
                        limpaCampos();//limpa os campos
                        break;
                    case "-":
                        //se nenhum botao foi clicado, informa ao usuario
                        JOptionPane.showMessageDialog(null, "Não há o que confirmar, pois nenhum botão anterior foi clicado!");
                        break;
                    default:
                        break;
                }
            }
        });

        btInserir.addActionListener(new ActionListener() {//implementação da ação botao inserir:
            @Override
            public void actionPerformed(ActionEvent ae) {
                bloqueiaDesbloqueiaBtTF(true);//desbloqueia todos os campos e botoes...
                btAlterar.setEnabled(false);//..exceto btAlterar e..
                btSair.setEnabled(false);//...btSair
                botaoAnterior = "btInserir";//informa que o botaoAnterior foi o btInserir
            }
        });

        btRemover.addActionListener(new ActionListener() {//immplementação do botao Remover
            @Override
            public void actionPerformed(ActionEvent ae) {
                botaoAnterior = "btRemover";//informa ao botaoAnterior que btRemover foi o ultimo
                btConfirmar.setEnabled(true);//desbloqueia o botao confirmar
            }
        });

        btSair.addActionListener(new ActionListener() {//implementação do botao sair:
            @Override
            public void actionPerformed(ActionEvent ae) {
                botaoAnterior = "btSair";//informa ao botaoAnterior q btSair foi clicado
                System.exit(0);//sai do programa
            }
        });
        //Adciono os botoes ao painel de botoes, na ordem que foi especificada
        jpBt.add(btInserir);
        jpBt.add(btRemover);
        jpBt.add(btAlterar);
        jpBt.add(btConfirmar);
        jpBt.add(btCancelar);
        jpBt.add(LB);
        jpBt.add(btSair);
        //adciono o subpainel de botoes a um super ao painel "norte" (que comtem todas labels e testfields)
        jp1.add(p_jpBt);
    }

    //metodo responsavel por "COMUNICAR SE" com o remover cadastro
    void deleteCliente() {
        int rowIndex = jtab.getSelectedRow();//pega o indice da linha clicada na tabela
        if (rowIndex == -1) {//se nenhuma linha foi clicada, informa ao usuario:
            JOptionPane.showMessageDialog(null, "Selecione na linha da tabela, o cliente que deseja excluir:");
            return;
        }
        //lista do tipo clienteModel, passando a ela o cadastro correspondente ao linha clicada na tabela
        Cliente cli = new ClienteTabelModel(clientelist).get(rowIndex);
        int result = new ClienteController().removeCliente(cli.getId());//chama ClienteContrller e passa o id do cadastro a ser deletado
        if (result == 1) {//se solicitação foi feita...
            JOptionPane.showMessageDialog(null, "Cliente removido!");//..informe ao usuario
            atualizaJtable();//atualiza tabel
        } else {//caso contrario informe o erro..
            JOptionPane.showMessageDialog(null, "Erro ao  remover!");
        }

    }

    //metodo responsavel pot "COMUNICAR SE" com o atualizar cadastro
    void updateCliente() {//
        ClienteDAO dao = new ClienteDAO();//crio objeto ClienteDao
        int rowIndex = jtab.getSelectedRow();//guarda o indice da linha clicada na tabel
        Cliente cli = new ClienteTabelModel(clientelist).get(rowIndex);//lista tipo clientemodel e passa o cliente corresp. a linha clicada
        clientelist = dao.PreencheTf(cli.getId());//chama o metodo responsavel por "transcrever" dados do cliente para textfields
        String a = tfId.getText();//variavel para receber o texto da textfield
        //passo para o objeto cliente os dados dos campos textfields:
        cli.setId(Long.parseLong(a));//passo a variavel ao objeto cliente, convertendo-a em Long
        cli.setNome(tfNome.getText());
        cli.setTelResidencial(tfTr.getText());
        cli.setTelComercial(tfTc.getText());
        cli.setTelCelular(tfTcel.getText());
        cli.setEmail(tfEmail.getText());
        int result = 0;//variavel para saber se solicitação foi ou nao executada
        if (id == null) {
            result = new ClienteController().updateCliente(cli);//chama metodor upDate, passando o cadastro a ser alterado
            atualizaJtable();//atualiza tabela
        }
        if (result == 1) {
            JOptionPane.showMessageDialog(null, "Alterado.");//informa caso sucesso
            limpaCampos();//limpa os campos
            atualizaJtable();//atualiza tabel
        } else {
            JOptionPane.showMessageDialog(null, "Tente novamente.");//informa caso insucesso
        }
    }

    //metodo responsavel por "COMUNICAR SE" com o inseerir novo registro
    void insertCliente() {
        Cliente cli = new Cliente();//crio objeto cliente
        //nao aceita campos vazios
        if (tfNome.getText().length() > 0 && tfEmail.getText().length() > 0 && tfTr.getText().length() > 0 && tfTc.getText().length() > 0 && tfTcel.getText().length() > 0) {
            //limite maximo de digitos
            if (tfNome.getText().length() < 41 && tfEmail.getText().length() < 26 && tfTr.getText().length() < 14 && tfTc.getText().length() < 14
                    && tfTcel.getText().length() < 14) {
                //passa para o objeto os textos das textfields
                cli.setNome(tfNome.getText());
                cli.setTelResidencial(tfTr.getText());
                cli.setTelComercial(tfTc.getText());
                cli.setTelCelular(tfTcel.getText());
                cli.setEmail(tfEmail.getText());
                int result = 0;
                if (id == null) {
                    result = new ClienteController().addCliente(cli);//passa para metodo adcionarCliente o objeto
                } else {
                    cli.setId(id);
                    result = new ClienteController().addCliente(cli);
                    id = null;
                }
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "Inserido com sucesso!");//de houve sucesso, informe
                    atualizaJtable();//atualiza tabela
                } else {//caso contrario, informe o erro
                    JOptionPane.showMessageDialog(null, "Erro na inserção!");
                }
            } else {//caso ultrapasse os limites de digitos..
                JOptionPane.showMessageDialog(null, "Não pode ultrapassar os limites dos  campos!");
                return;
            }
        } else {//caso haja algum campo em branco
            JOptionPane.showMessageDialog(null, "Não pode haver campos em branco!");
            return;
        }

    }

    //metodo responsavel por atualizar a tabela
    void atualizaJtable() {
        clientelist = new ClienteController().findClientes();//lista tipo cliente, recebe retorno da busca do metodo find de outra classe
        if (clientelist != null) {//se a lista acima não for nula, tiver registro, passe a para a tabela
            jtab.setModel(new ClienteTabelModel(clientelist));
        }
    }
    //metodo para bloquear/desbloquear campos e botoes
    void bloqueiaDesbloqueiaBtTF(boolean b) {//recebe um boleano dependendo do q se quer false=bloqueia true=desbloqueia
        //passa o parametro a todos os botoes e textfields
        btRemover.setEnabled(b);
        btAlterar.setEnabled(b);
        btConfirmar.setEnabled(b);
        btCancelar.setEnabled(b);
        tfNome.setEnabled(b);
        tfTc.setEnabled(b);
        tfTcel.setEnabled(b);
        tfTr.setEnabled(b);
        tfEmail.setEnabled(b);
    }
    
    //metodo do botao cancelar: limpa todos os textfields, setando os texto para varzio
    void limpaCampos() {
        tfId.setText("");
        tfEmail.setText("");
        tfNome.setText("");
        tfTc.setText("");
        tfTcel.setText("");
        tfTr.setText("");
    }

    //metodo para "devolver" as texttfields os dados dos cadastro
    public void transcreva() {
        int rowIndex = jtab.getSelectedRow();//pega o indice da linha clicada na tabela
        if (rowIndex == -1) {//se nehum for clicadda informe
            JOptionPane.showMessageDialog(null, "Selecione na linha da tabela, o cliente que seseja excluir:");
            return;
        }
        ClienteDAO dao = new ClienteDAO();//cria objeto clienteDao
        Cliente cli = new ClienteTabelModel(clientelist).get(rowIndex);//cria cliente e uma lista com cliente corresp ao indice linha
        clientesTf = dao.PreencheTf(cli.getId());//passa ara lista o retorno do metodo preencher
        //seta cada texto do campo com os respectivos retorno da lista
        tfId.setText(clientesTf.get(0).getId().toString());//passa pro campo id o id contido na lista que recebeu retorno ...
        tfNome.setText(clientesTf.get(0).getNome());
        tfTr.setText(clientesTf.get(0).getTelResidencial());
        tfTc.setText(clientesTf.get(0).getTelComercial());
        tfTcel.setText(clientesTf.get(0).getTelCelular());
        tfEmail.setText(clientesTf.get(0).getEmail());
    }
}
