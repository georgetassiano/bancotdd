package principal;

public class Conta {
	
	private double saldo;
	private int numeroConta;
	private int numeroAgencia;
	
	public Conta(double saldo, int numeroConta, int numeroAgencia) {
		this.saldo = saldo;
		this.numeroConta = numeroConta;
		this.numeroAgencia = numeroAgencia;
	}
	
	public double getSaldo() {
		return saldo;
	}
	
	public void adicionarValor(double valor) {
		saldo+=valor;
	}
	
	public void retirarValor(double valor) {
		
		if(valor > saldo) {
			
			throw new IllegalArgumentException("O valor a ser sacado é menor que o saldo atual");
			
		} else {
			
			saldo-=valor;
			
		}
	}
	
}
