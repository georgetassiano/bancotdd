package principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.expectThrows;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransacaoTransferenciaTest {
	
	@Mock
	private ContaDAO contaDAO;
	private TransacaoTransferencia transferencia;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		transferencia = new TransacaoTransferencia(100.0, 12345, 123456, 23456, 234567, contaDAO);
	}

	@After
	public void tearDown() throws Exception {
		transferencia = null;
	}

	@Test
	public void deveRetornarExcercaoSeAContaDestinoNaoExistir() {
		
		//define que a interface ir� retornar uma excer��o quando fizer um getConta para 0, 0
		when(contaDAO.getConta(0, 0))
		.thenThrow(new IllegalArgumentException("N�o h� uma conta com o n�mero da conta e/ou ag�ncia informados"));
		
		//realiza o teste de verifica��o se ao executar o processarTransacao ir� gerar a excer��o especificada
		Throwable exception = expectThrows(IllegalArgumentException.class, () -> {
		      new TransacaoTransferencia(100.0, 12345, 123456, 0, 0, contaDAO).prepararTransacao();
		    });
		
		//verifica se o metodo getConta foi chamado da interface contaDAO
		verify(contaDAO).getConta(0, 0);
		
		//verifica se a mensagem da interface corresponde com a esperada
		assertEquals("N�o h� uma conta com o n�mero da conta e/ou ag�ncia informados", exception.getMessage());
		
		//verifica se n�o houve mais chamadas para a interface
		 verifyNoMoreInteractions(contaDAO);
		 
	}
	
	@Test
	public void deveRetornarExcercaoSeOSaldoDoRemetenteForMenorQueOValor() {
		
		//define que ao fazer um getConta para 12345, 123456 ir� retornar uma conta com saldo vazio
		when(contaDAO.getConta(12345, 123456))
		.thenReturn(new Conta(0.0, 12345, 123456));
		
		//realiza o teste de verifica��o se ao executar o processarTransacao ir� gerar a excer��o especificada
		Throwable exception = expectThrows(IllegalArgumentException.class, () -> {
		      new TransacaoTransferencia(100.0, 12345, 123456, 23456, 234567, contaDAO).prepararTransacao().processarTransacao();
		    });
		
		//verifica se o metodo getConta foi chamado da interface contaDAO
		verify(contaDAO).getConta(12345, 123456);
		
		//verifica se a mensagem da interface corresponde com a esperada
		assertEquals("O valor a ser sacado � menor que o saldo atual", exception.getMessage());
		 
	}
	
	@Test
	public void deveFazerAtransferenciaDoValorDoRemetenteParaODestinatario() {
		
		//cria um mock de chamadas ordenadas
		InOrder inOrder = inOrder(contaDAO);
		
		//define que ao fazer um getConta para 12345, 123456 ir� retornar uma conta com saldo vazio
		when(contaDAO.getConta(12345, 123456))
		.thenReturn(new Conta(100.0, 12345, 123456));
		
		//define que ao fazer um getConta para 12345, 123456 ir� retornar uma conta com saldo vazio
		when(contaDAO.getConta(23456, 234567))
		.thenReturn(new Conta(0.0, 23456, 234567));
		
		//define que um m�todo que ir� atualizar o valor da conta para 100.0
		contaDAO.setSaldo(0.0, 12345, 123456);
		
		//define que um m�todo que ir� atualizar o valor da conta para 100.0
		contaDAO.setSaldo(100.0, 23456, 234567);
		
		//executa o metodo da prepara��o da transacao
	    transferencia.prepararTransacao();
		
		//executa o metodo da transacao
		transferencia.processarTransacao();
		
		//verifica se os metodos foram chamadas e executadas nessa ordem
		inOrder.verify(contaDAO).getConta(23456, 234567);
		inOrder.verify(contaDAO).getConta(12345, 123456);
		inOrder.verify(contaDAO).setSaldo(0.0, 12345, 123456);
		inOrder.verify(contaDAO).setSaldo(100.0, 23456, 234567);
		 
	}

}
