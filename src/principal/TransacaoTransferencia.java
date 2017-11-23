package principal;

public class TransacaoTransferencia extends Transacao {

	private int numeroContaOrigem;
	private int numeroAgenciaOrigem;
	
	public TransacaoTransferencia(double valor, int numeroContaOrigem, int numeroAgenciaOrigem, int numeroContaAlvo, int numeroAgenciaAlvo, ContaDAO contaDAO) {
		super(valor, numeroContaAlvo, numeroAgenciaAlvo, contaDAO);
		this.numeroContaOrigem = numeroContaOrigem;
		this.numeroAgenciaOrigem = numeroAgenciaOrigem;
	}

	@Override
	public void processarTransacao() {
		Conta contaOrigem = getContaDAO().getConta(numeroContaOrigem, numeroAgenciaOrigem);
		contaOrigem.retirarValor(getValor());
		getContaAlvo().adicionarValor(getValor());
		getContaDAO().setSaldo(contaOrigem.getSaldo(), numeroContaOrigem, numeroAgenciaOrigem);
		getContaDAO().setSaldo(getContaAlvo().getSaldo(), getNumeroContaAlvo(), getNumeroAgenciaAlvo());
	}

}
