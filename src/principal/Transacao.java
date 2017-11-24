package principal;

public abstract class Transacao {
	
	private double valor;
	private int numeroContaAlvo;
	private int numeroAgenciaAlvo;
	private ContaDAO contaDAO;
	private Conta contaAlvo;
	
	public Transacao(double valor, int numeroContaAlvo, int numeroAgenciaAlvo, ContaDAO contaDAO) {
		 this.valor = valor;
		 this.numeroContaAlvo = numeroContaAlvo;
		 this.numeroAgenciaAlvo = numeroAgenciaAlvo;
		 this.contaDAO = contaDAO;
		 this.contaAlvo = null;
	}
	
	public abstract void processarTransacao();
	
	public Transacao verificarContaDestino() throws IllegalArgumentException {
		
			this.contaAlvo = this.contaDAO.getConta(numeroContaAlvo, numeroAgenciaAlvo);
			
			return this;
			
	}

	public double getValor() {
		return valor;
	}

	public int getNumeroContaAlvo() {
		return numeroContaAlvo;
	}

	public int getNumeroAgenciaAlvo() {
		return numeroAgenciaAlvo;
	}

	public ContaDAO getContaDAO() {
		return contaDAO;
	}
	
	public Conta getContaAlvo() {
		return contaAlvo;
	}

}
