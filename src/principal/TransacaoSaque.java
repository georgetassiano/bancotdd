package principal;

public class TransacaoSaque extends Transacao{

	public TransacaoSaque(double valor, int numeroContaAlvo, int numeroAgenciaAlvo, ContaDAO contaDAO) {
		super(valor, numeroContaAlvo, numeroAgenciaAlvo, contaDAO);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processarTransacao() throws IllegalArgumentException {
		
		getContaAlvo().retirarValor(getValor());
		getContaDAO().setSaldo(getContaAlvo().getSaldo(), getNumeroContaAlvo(), getNumeroAgenciaAlvo());
		
	}
	
}
