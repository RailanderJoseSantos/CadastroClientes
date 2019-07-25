package model.bean;
//classe cliente usada para criar objeto e "passa-lo" para o banco
public class Cliente {
    /*atributos de cliente*/
    private Long id;
    private  String nome;
    private String telComercial;
    private String telResidencial;
    private String telCelular;
    private String email;
    
    /*Metodos para setar e acessar*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelComercial() {
        return telComercial;
    }

    public void setTelComercial(String telComercial) {
        this.telComercial = telComercial;
    }

    public String getTelResidencial() {
        return telResidencial;
    }

    public void setTelResidencial(String telResidencial) {
        this.telResidencial = telResidencial;
    }

    public String getTelCelular() {
        return telCelular;
    }

    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //reescrita do metodo toString para mostrar os dados do cliente
    @Override
    public String toString() {
        return "Cliente{" + "id=" + id +
                ", nome=" +
                nome + ", telcomercial=" +
                telComercial + ", telresidencial=" +
                telResidencial + ", telcelular=" +
                telCelular + ", email=" +
                email + '}';
    }
    
}
