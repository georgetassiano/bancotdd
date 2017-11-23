package principal;

public class Cliente {
   
	private String nome;
    private Conta conta;
   
    public Cliente(String nome, Conta conta) {
		this.nome = nome;
		this.conta = conta;
	} 
    
    public Conta getConta() {
		return conta;
	}
    
    public double verSaldo() {
    	return conta.getSaldo();
    }
    
}
