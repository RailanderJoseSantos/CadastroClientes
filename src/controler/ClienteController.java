
package controler;
import facadade.ClienteFacade;
import java.util.List;
import model.bean.Cliente;

//classe controladora (seus metodos chamam os metds da ClienteFacade que por sua vez chamam os metds DAO (inserir,remover,altera,buscar))
public class ClienteController {
    private final ClienteFacade facade;//cria atributo tipo ClienteFacede

    public ClienteController() {//inicia o cosntrutor com um novo clienteFacade
        this.facade = new ClienteFacade();
    }
    
    //metodo adciona, acessa o facade e chama o save, passando o objt cliente como paramentro
    public int addCliente(Cliente cli){
        return facade.save(cli);
    }
    //metodo uptadeCliente, acessa o facade e chama o update, passando o objt cliente como paramentro
    public int updateCliente(Cliente cli){
        return  facade.update(cli);
    }
    //metodo removeCliente, acessa o facade e chama o remove, passando o id do cliente como paramentro
    public int removeCliente(Long id){
        return facade.remove(id);
    }
    //metodo findClientes, acessa o facade e chama o findAll
    public  List<Cliente> findClientes(){
        return facade.findAll();
    }
}
