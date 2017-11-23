package principal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import static org.testng.Assert.expectThrows;

public class TransacaoDepositoTest {
	
	@Mock
	private ContaDAO contaDAO;
	private TransacaoDeposito deposito;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		deposito = new TransacaoDeposito(100.0, 12345, 123456, contaDAO);
	}

	@After
	public void tearDown() throws Exception {
		deposito = null;
	}

	@Test
	public void deveRetornarExcercaoSeAContaNaoExistir() {
		
		//define que a interface irá retornar uma excerção quando fizer um getConta para 0, 0
		when(contaDAO.getConta(0, 0))
		.thenThrow(new IllegalArgumentException("Não há uma conta com o número da conta e/ou agência informados"));
		
		//realiza o teste de verificação se ao executar o processarTransacao irá gerar a excerção especificada
		Throwable exception = expectThrows(IllegalArgumentException.class, () -> {
		      new TransacaoDeposito(100.0, 0, 0, contaDAO).prepararTransacao();
		    });
		
		//verifica se o metodo getConta foi chamado da interface contaDAO
		verify(contaDAO).getConta(0, 0);
		
		//verifica se a mensagem da interface corresponde com a esperada
		assertEquals("Não há uma conta com o número da conta e/ou agência informados", exception.getMessage());
		
		//verifica se não houve mais chamadas para a interface
		 verifyNoMoreInteractions(contaDAO);
		 
	}
	
	@Test
	public void deveAdicionarOValorNoSaldoDaConta() {
		
		//cria um mock de chamadas ordenadas
		InOrder inOrder = inOrder(contaDAO);
		
		//define que ao fazer um getConta para 12345, 123456 irá retornar uma conta com saldo vazio
		when(contaDAO.getConta(12345, 123456))
		.thenReturn(new Conta(0.0, 12345, 123456));
		
		//define que um método que irá atualizar o valor da conta para 100.0
		contaDAO.setSaldo(100.0, 12345, 123456);
		
		//executa o metodo da preparação da transacao
	    deposito.prepararTransacao();
		
		//executa o metodo da transacao
		deposito.processarTransacao();
		
		//verifica se os metodos foram chamadas e executadas nessa ordem
		inOrder.verify(contaDAO).getConta(12345, 123456);
		inOrder.verify(contaDAO).setSaldo(100.0, 12345, 123456);
		 
	}

}
