package principal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import static org.testng.Assert.expectThrows;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
class TransacaoDepositoTest {
	
	@Mock
	private ContaDAO contaDAO;
	private TransacaoDeposito deposito;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		deposito = new TransacaoDeposito(100.0, 12345, 123456, contaDAO);
	}

	@AfterEach
	void tearDown() throws Exception {
		deposito = null;
	}

	@Test
	void deveRetornarExcercaoSeAContaNaoExistir() {
		
		//define que a interface ir� retornar uma excer��o quando fizer um getConta para 0, 0
		when(contaDAO.getConta(0, 0))
		.thenThrow(new IllegalArgumentException("N�o h� uma conta com o n�mero da conta e/ou ag�ncia informados"));
		
		//realiza o teste de verifica��o se ao executar o processarTransacao ir� gerar a excer��o especificada
		Throwable exception = expectThrows(IllegalArgumentException.class, () -> {
		      new TransacaoDeposito(100.0, 0, 0, contaDAO).prepararTransacao();
		    });
		
		//verifica se o metodo getConta foi chamado da interface contaDAO
		verify(contaDAO).getConta(0, 0);
		
		//verifica se a mensagem da interface corresponde com a esperada
		assertEquals("N�o h� uma conta com o n�mero da conta e/ou ag�ncia informados", exception.getMessage());
		
		//verifica se n�o houve mais chamadas para a interface
		 verifyNoMoreInteractions(contaDAO);
		 
	}
	
	@Test
	void deveAdicionarOValorNoSaldoDaConta() {
		
		//cria um mock de chamadas ordenadas
		InOrder inOrder = inOrder(contaDAO);
		
		//define que ao fazer um getConta para 12345, 123456 ir� retornar uma conta com saldo vazio
		when(contaDAO.getConta(12345, 123456))
		.thenReturn(new Conta(0.0, 12345, 123456));
		
		//define que um m�todo que ir� atualizar o valor da conta para 100.0
		contaDAO.setSaldo(100.0, 12345, 123456);
		
		//executa o metodo da prepara��o da transacao
	    deposito.prepararTransacao();
		
		//executa o metodo da transacao
		deposito.processarTransacao();
		
		//verifica se os metodos foram chamadas e executadas nessa ordem
		inOrder.verify(contaDAO).getConta(12345, 123456);
		inOrder.verify(contaDAO).setSaldo(100.0, 12345, 123456);
		 
	}

}