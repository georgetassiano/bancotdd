package principal;

public interface ContaDAO {
	Conta getConta(int numeroConta, int numeroAgencia);
	void setSaldo(double valor, int numeroConta, int numeroAgencia);
}
