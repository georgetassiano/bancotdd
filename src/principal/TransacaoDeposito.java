package principal;

public class TransacaoDeposito extends Transacao {

	public TransacaoDeposito(double valor, int numeroContaAlvo, int numeroAgenciaAlvo, ContaDAO contaDAO) {
		super(valor, numeroContaAlvo, numeroAgenciaAlvo, contaDAO);
	}

	@Override
	public void processarTransacao() throws IllegalArgumentException {
		
		double novoSaldo= getContaAlvo().getSaldo()+getValor();
		
		getContaDAO().setSaldo(novoSaldo, getNumeroContaAlvo(), getNumeroAgenciaAlvo());
		
	}
	
}
