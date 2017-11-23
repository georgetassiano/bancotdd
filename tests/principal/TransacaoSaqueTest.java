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

public class TransacaoSaqueTest {
	
	@Mock
	private ContaDAO contaDAO;
	private TransacaoSaque saque;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		saque = new TransacaoSaque(100.0, 12345, 123456, contaDAO);
	}

	@After
	public void tearDown() throws Exception {
		saque = null;
	}

	@Test
	public void deveRetornarExcercaoSeOSaldoForMenorQueOValor() {
		
		//define que ao fazer um getConta para 12345, 123456 irá retornar uma conta com saldo vazio
		when(contaDAO.getConta(12345, 123456))
		.thenReturn(new Conta(0.0, 12345, 123456));
		
		//realiza o teste de verificação se ao executar o processarTransacao irá gerar a excerção especificada
		Throwable exception = expectThrows(IllegalArgumentException.class, () -> {
		      new TransacaoSaque(100.0, 12345, 123456, contaDAO).prepararTransacao().processarTransacao();
		    });
		
		//verifica se o metodo getConta foi chamado da interface contaDAO
		verify(contaDAO).getConta(12345, 123456);
		
		//verifica se a mensagem da interface corresponde com a esperada
		assertEquals("O valor a ser sacado é menor que o saldo atual", exception.getMessage());
		
		//verifica se não houve mais chamadas para a interface
		 verifyNoMoreInteractions(contaDAO);
		 
	}
	
	@Test
	public void deveRetirarOValorNoSaldoDaConta() {
		
		//cria um mock de chamadas ordenadas
		InOrder inOrder = inOrder(contaDAO);
		
		//define que ao fazer um getConta para 12345, 123456 irá retornar uma conta com saldo igual a 100.0
		when(contaDAO.getConta(12345, 123456))
		.thenReturn(new Conta(100.0, 12345, 123456));
		
		//define que um método que irá atualizar o valor da conta para 0.0
		contaDAO.setSaldo(0.0, 12345, 123456);
		
		//executa o metodo da preparação da transacao
	    saque.prepararTransacao();
		
		//executa o metodo da transacao
		saque.processarTransacao();
		
		//verifica se os metodos foram chamadas e executadas nessa ordem
		inOrder.verify(contaDAO).getConta(12345, 123456);
		inOrder.verify(contaDAO).setSaldo(0.0, 12345, 123456);
		 
	}

}
