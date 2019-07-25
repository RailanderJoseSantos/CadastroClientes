
package facadade;

import java.util.List;
import model.bean.Cliente;
import model.dao.ClienteDAO;

/**
 * @author Railander
 */
//classe Facade
public class ClienteFacade {
    private ClienteDAO dao;

    public ClienteFacade() {//Inicia o construtor com objt clienteDao
        this.dao = new ClienteDAO();
    }
    //metodo save, recebe objt cliente como parametro e passa para o metodo INSERIR da classa DAO
    public int save(Cliente cli){
        return dao.inserir(cli);
    }
    //metodo update, recebe objt cliente como parametro e passa para o metodo UPDATE da classa DAO
    public int update(Cliente cli){
        return dao.upDate(cli);
    }
    //metodo REMOVE, recebe id do  cliente como parametro e passa para o metodo EXCLUIR da classa DAO
    public int remove(Long id){
        return dao.excluir(id);
    }
    //metodo findAll, chama o metodo find da classe DAO
    public List<Cliente> findAll(){
        return dao.find();
    }
}
