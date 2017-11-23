package principal;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;


public class ClienteTest {

	private Cliente cliente;
	
	@Before
	public void setUp() throws Exception {
		cliente = new Cliente("George", new Conta(0.0, 12345, 123456));
	}

	@After
	public void tearDown() throws Exception {
		cliente = null;
	}

	@Test
	public void deveRetornarSaldoAtualDaContaDoCliente() {
		
		//executa o metodo de ver saldo
		double saldoAtual = cliente.verSaldo();
		
		//define o valor esperado
		double saldoEsperado = 0.0;
		
		//testa se o valor recebido é o mesmo do esperado
		assertEquals(saldoEsperado, saldoAtual);
	}

}
